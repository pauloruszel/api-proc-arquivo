package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.enums.ProcessamentoStatus;
import com.br.apiprocarquivo.domain.model.Cotacao;
import com.br.apiprocarquivo.domain.model.ModeloVeiculo;
import com.br.apiprocarquivo.domain.model.Processamento;
import com.br.apiprocarquivo.domain.model.SegmentoVeiculo;
import com.br.apiprocarquivo.domain.model.TipoPessoa;
import com.br.apiprocarquivo.domain.model.Veiculo;
import com.br.apiprocarquivo.domain.model.VeiculoCotacao;
import com.br.apiprocarquivo.domain.repository.ProcessamentoRepository;
import com.br.apiprocarquivo.domain.service.*;
import com.monitorjbl.xlsx.StreamingReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
    private final TaskExecutor processamentoTaskExecutor;

    @Value("${processamento.limite.registros}")
    private int limiteDeRegistros;

    @Override
    public Processamento iniciarProcessamento(final MultipartFile arquivo) throws IOException {
        final var processamento = prepararProcessamento();
        final var arquivoTemporario = criarArquivoTemporario(arquivo);
        try {
            processamentoTaskExecutor.execute(() -> executarProcessamentoAsync(processamento.getId(), arquivoTemporario,
                    arquivo.getOriginalFilename()));
        } catch (RuntimeException e) {
            try {
                Files.deleteIfExists(arquivoTemporario);
            } catch (IOException ex) {
                log.warn("falha ao remover arquivo temporário após rejeição do executor {}: {}", arquivoTemporario,
                        ex.getMessage(), ex);
            }
            throw e;
        }
        return processamento;
    }

    private Processamento prepararProcessamento() {
        log.info("preparando um novo processamento.");
        final var nomeArquivoUnico = criarNomeArquivoAleatorio();
        final var processamento = Processamento.builder()
                .nomeArquivo(nomeArquivoUnico)
                .data(LocalDateTime.now())
                .status(ProcessamentoStatus.AGUARDANDO)
                .limiteRegistrosUtilizado(limiteDeRegistros)
                .quantidadeProcessada(0)
                .totalRegistros(0)
                .build();
        return processamentoRepository.save(processamento);
    }

    private Path criarArquivoTemporario(final MultipartFile arquivo) throws IOException {
        final Path tempFile = Files.createTempFile("processamento-arquivo-", ".xlsx");
        try (InputStream inputStream = arquivo.getInputStream()) {
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        return tempFile;
    }

    private void executarProcessamentoAsync(final Long processamentoId, final Path arquivoTemporario,
                                            final String nomeOriginal) {
        try (InputStream inputStream = Files.newInputStream(arquivoTemporario);
             Workbook workbook = StreamingReader.builder()
                     .rowCacheSize(100)
                     .bufferSize(8192)
                     .open(inputStream)) {

            final var processamento = processamentoRepository.findById(processamentoId)
                    .orElseThrow(() -> new IllegalStateException("Processamento não encontrado: " + processamentoId));

            processamento.setStatus(ProcessamentoStatus.PROCESSANDO);
            processamentoRepository.save(processamento);

            final var sheet = workbook.getSheetAt(0);
            processarSheet(sheet, processamento);
        } catch (Exception e) {
            log.error("erro ao processar arquivo {} (processamento {}): {}", nomeOriginal, processamentoId, e.getMessage(), e);
            processamentoRepository.findById(processamentoId).ifPresent(proc -> {
                proc.setStatus(ProcessamentoStatus.PARCIAL);
                processamentoRepository.save(proc);
            });
        } finally {
            try {
                Files.deleteIfExists(arquivoTemporario);
            } catch (IOException ex) {
                log.warn("falha ao remover arquivo temporário {}: {}", arquivoTemporario, ex.getMessage(), ex);
            }
        }
    }

    private void processarSheet(final Sheet sheet, final Processamento processamento) {
        log.info("inicio do processamento dos registros excel.");

        int totalRegistros = 0;
        int linhasProcessadasComSucesso = 0;
        final var cache = new ProcessamentoCache();

        final Iterator<Row> rowIterator = sheet.rowIterator();
        if (rowIterator.hasNext()) {
            rowIterator.next();
        }

        while (rowIterator.hasNext()) {
            if (totalRegistros >= processamento.getLimiteRegistrosUtilizado()) {
                log.info("limite de registros para processamento alcancado: {} registros.",
                        processamento.getLimiteRegistrosUtilizado());
                break;
            }

            final var row = rowIterator.next();
            totalRegistros++;

            if (processamentoService.processarLinha(row, processamento, row.getRowNum())) {
                linhasProcessadasComSucesso++;
                final var arquivoCotacao = createVeiculoCotacaoModelFromRow(row);
                if (arquivoCotacao != null) {
                    salvarDadosSeNecessario(arquivoCotacao, cache);
                }
            }
        }

        processamentoService.atualizarStatusProcessamento(linhasProcessadasComSucesso, totalRegistros, processamento);
        log.info("fim do processamento dos registros excel com total de registros processados: {}/{}",
                linhasProcessadasComSucesso, totalRegistros);
    }

    private void salvarDadosSeNecessario(final VeiculoCotacaoModel model, final ProcessamentoCache cache) {
        final var segmento = cache.segmentos.computeIfAbsent(model.getSegmento(), key -> {
            log.info("inicio da verificacao para salvar os dados para o segmento {}", model.getSegmento());
            final var salvo = segmentoVeiculoService.salvarSeNaoExiste(model);
            log.info("fim da verificacao para salvar os dados para o segmento {}", salvo.getNome());
            return salvo;
        });

        final var modelo = cache.modelos.computeIfAbsent(model.getModelo(), key -> {
            log.info("inicio da verificacao para salvar os dados para o modelo {}", model.getModelo());
            final var salvo = modeloVeiculoService.salvarSeNaoExiste(model);
            log.info("fim da verificacao para salvar os dados para o modelo {}", salvo.getNome());
            return salvo;
        });

        final var tipoPessoa = cache.tiposPessoa.computeIfAbsent(model.getCodigoCliente(), key -> {
            log.info("inicio da verificacao para salvar os dados para o tipo pessoa {}", model.getCodigoCliente());
            final var salvo = tipoPessoaService.salvarSeNaoExiste(model);
            log.info("fim da verificacao para salvar os dados para o tipo pessoa {}", salvo.getNome());
            return salvo;
        });

        final var veiculo = cache.veiculos.computeIfAbsent(model.getMarca(), key -> {
            log.info("inicio da verificacao para salvar os dados para o veiculo {}", model.getMarca());
            final var salvo = veiculoService.salvarSeNaoExiste(model, segmento, modelo);
            log.info("fim da verificacao para salvar os dados para o veiculo {}", salvo.getMarca());
            return salvo;
        });

        final var cotacao = cache.cotacoes.computeIfAbsent(model.getCotacao(), key -> {
            log.info("inicio da verificacao para salvar os dados para a cotacao {}", model.getCotacao());
            final var salvo = cotacaoService.salvarSeNaoExiste(model);
            log.info("fim da verificacao para salvar os dados para a cotacao {}", salvo.getCodigoCotacao());
            return salvo;
        });

        final var chaveVeiculoCotacao = veiculo.getIdVeiculo() + ":" + cotacao.getIdCotacao() + ":" + tipoPessoa.getIdTipoPessoa();
        cache.veiculosCotacao.computeIfAbsent(chaveVeiculoCotacao, key -> {
            log.info("inicio da verificacao para salvar os dados para o veiculo cotacao associando veículo {}, cotação {}, e tipo de pessoa {}",
                    veiculo.getIdVeiculo(), cotacao.getIdCotacao(), tipoPessoa.getIdTipoPessoa());
            final var salvo = veiculoCotacaoService.salvarSeNaoExiste(model, veiculo, cotacao, tipoPessoa);
            log.info("fim da verificacao para salvar os dados para o veiculo cotacao para a cotacao {}",
                    salvo.getCotacao().getCodigoCotacao());
            return salvo;
        });
    }

    private static class ProcessamentoCache {
        private final Map<String, SegmentoVeiculo> segmentos = new HashMap<>();
        private final Map<String, ModeloVeiculo> modelos = new HashMap<>();
        private final Map<String, TipoPessoa> tiposPessoa = new HashMap<>();
        private final Map<String, Veiculo> veiculos = new HashMap<>();
        private final Map<String, Cotacao> cotacoes = new HashMap<>();
        private final Map<String, VeiculoCotacao> veiculosCotacao = new HashMap<>();
    }

}

