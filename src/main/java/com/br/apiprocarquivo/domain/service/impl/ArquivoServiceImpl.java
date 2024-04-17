package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.enums.ProcessamentoStatus;
import com.br.apiprocarquivo.domain.model.*;
import com.br.apiprocarquivo.domain.repository.ProcessamentoRepository;
import com.br.apiprocarquivo.domain.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
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
            var workbook = WorkbookFactory.create(fileInputStream);
            var sheet = workbook.getSheetAt(0);
            processarSheet(sheet, processamento);
        }

    }

    private Processamento prepararProcessamento() {
        final var nomeArquivoUnico = criarNomeArquivoAleatorio();
        final var processamento = Processamento.builder()
                .nomeArquivo(nomeArquivoUnico)
                .data(LocalDateTime.now())
                .status(ProcessamentoStatus.PARCIAL)
                .build();
        return processamentoRepository.save(processamento);
    }

    private void processarSheet(final Sheet sheet, final Processamento processamento) {
        int totalRegistros = 0;
        int linhasProcessadasComSucesso = 0;

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            if (++totalRegistros >= limiteDeRegistros) {
                log.info("Limite de registros para processamento alcançado: {} registros.", limiteDeRegistros);
                break;
            }

            final var arquivoCotacao = createVeiculoCotacaoModelFromRow(row);
            if (arquivoCotacao == null) continue;

            if (processamentoService.processarLinha(row, processamento, i)) {
                linhasProcessadasComSucesso++;
            }

            salvarDadosSeNecessario(arquivoCotacao);
        }

        processamentoService.atualizarStatusProcessamento(linhasProcessadasComSucesso, sheet, processamento);
        log.info("Total de registros processados: {}/{}", linhasProcessadasComSucesso, totalRegistros);
    }

    private void salvarDadosSeNecessario(final VeiculoCotacaoModel veiculoCotacaoModel) {
        final var segmento = segmentoVeiculoService.salvarSeNaoExiste(veiculoCotacaoModel);
        final var modelo = modeloVeiculoService.salvarSeNaoExiste(veiculoCotacaoModel);
        final var tipoPessoa = tipoPessoaService.salvarSeNaoExiste(veiculoCotacaoModel);
        final var veiculo = veiculoService.salvarSeNaoExiste(veiculoCotacaoModel, segmento, modelo);
        final var cotacao = cotacaoService.salvarSeNaoExiste(veiculoCotacaoModel);

        veiculoCotacaoService.salvarSeNaoExiste(veiculoCotacaoModel, veiculo, cotacao, tipoPessoa);
    }

}

