package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.enums.ProcessamentoStatus;
import com.br.apiprocarquivo.domain.model.Processamento;
import com.br.apiprocarquivo.domain.repository.ProcessamentoRepository;
import com.br.apiprocarquivo.domain.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import static com.br.apiprocarquivo.infrastructure.helper.ArquivoHelper.createVeiculoCotacaoModelFromRow;
import static com.br.apiprocarquivo.infrastructure.helper.ArquivoHelper.criarNomeArquivoAleatorio;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArquivoServiceImpl implements ArquivoService {

    private final ProcessamentoService processamentoService;
    private final ProcessamentoRepository processamentoRepository;
    private final VeiculoCotacaoService veiculoCotacaoService;
    private final SegmentoVeiculoService segmentoVeiculoService;
    private final ModeloVeiculoService modeloVeiculoService;
    private final TipoPessoaService tipoPessoaService;
    private final VeiculoService veiculoService;
    private final CotacaoService cotacaoService;

    @Value("${processamento.limite.registros}")
    private int limiteDeRegistros;

    @Override
    public void processarArquivo(final MultipartFile arquivo) throws IOException {
        final var processamento = prepararProcessamento();
        try (InputStream fileInputStream = arquivo.getInputStream()) {
            final var workbook = WorkbookFactory.create(fileInputStream);
            final var sheet = workbook.getSheetAt(0);
            processarSheet(sheet, processamento);
        }
    }

    private Processamento prepararProcessamento() {
        log.info("preparando um novo processamento.");
        final var nomeArquivoUnico = criarNomeArquivoAleatorio();
        final var processamento = Processamento.builder()
                .nomeArquivo(nomeArquivoUnico)
                .data(LocalDateTime.now())
                .status(ProcessamentoStatus.PARCIAL)
                .build();
        return processamentoRepository.save(processamento);
    }

    private void processarSheet(final Sheet sheet, final Processamento processamento) {
        log.info("inicio do processamento dos registros excel.");

        int totalRegistros = 0;
        int linhasProcessadasComSucesso = 0;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            final var row = sheet.getRow(i);

            if (++totalRegistros >= limiteDeRegistros) {
                log.info("limite de registros para processamento alcancado: {} registros.", limiteDeRegistros);
                return;
            }

            if (processamentoService.processarLinha(row, processamento, i)) {
                linhasProcessadasComSucesso++;
            }

            final var arquivoCotacao = createVeiculoCotacaoModelFromRow(row);
            if (arquivoCotacao != null) {
                salvarDadosSeNecessario(arquivoCotacao);
            }
        }

        processamentoService.atualizarStatusProcessamento(linhasProcessadasComSucesso, sheet, processamento);
        log.info("fim do processamento dos registros excel com total de registros processados: {}/{}", linhasProcessadasComSucesso, totalRegistros);
    }

    private void salvarDadosSeNecessario(final VeiculoCotacaoModel model) {
        log.info("inicio da verificacao para salvar os dados para o segmento {}", model.getSegmento());
        final var segmento = segmentoVeiculoService.salvarSeNaoExiste(model);
        log.info("fim da verificacao para salvar os dados para o segmento {}", segmento.getNome());

        log.info("inicio da verificacao para salvar os dados para o modelo {}", model.getModelo());
        final var modelo = modeloVeiculoService.salvarSeNaoExiste(model);
        log.info("fim da verificacao para salvar os dados para o modelo {}", modelo.getNome());

        log.info("inicio da verificacao para salvar os dados para o tipo pessoa {}", model.getCodigoCliente());
        final var tipoPessoa = tipoPessoaService.salvarSeNaoExiste(model);
        log.info("fim da verificacao para salvar os dados para o tipo pessoa {}", tipoPessoa.getNome());

        log.info("inicio da verificacao para salvar os dados para o veiculo {}", model.getMarca());
        final var veiculo = veiculoService.salvarSeNaoExiste(model, segmento, modelo);
        log.info("fim da verificacao para salvar os dados para o veiculo {}", veiculo.getMarca());

        log.info("inicio da verificacao para salvar os dados para a cotacao {}", model.getCotacao());
        final var cotacao = cotacaoService.salvarSeNaoExiste(model);
        log.info("fim da verificacao para salvar os dados para a cotacao {}", cotacao.getCodigoCotacao());

        log.info("inicio da verificacao para salvar os dados para o veiculo cotacao associando veículo {}, cotação {}, e tipo de pessoa {}", veiculo.getIdVeiculo(), cotacao.getIdCotacao(), tipoPessoa.getIdTipoPessoa());
        final var veiculoCotacao = veiculoCotacaoService.salvarSeNaoExiste(model, veiculo, cotacao, tipoPessoa);
        log.info("fim da verificacao para salvar os dados para o veiculo cotacao para a cotacao {}", veiculoCotacao.getCotacao());
    }

}

