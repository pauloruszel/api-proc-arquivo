package com.br.apiprocarquivo.service;

import com.br.apiprocarquivo.domain.enums.ProcessamentoErroStatus;
import com.br.apiprocarquivo.domain.enums.ProcessamentoStatus;
import com.br.apiprocarquivo.entity.Planos;
import com.br.apiprocarquivo.entity.Processamento;
import com.br.apiprocarquivo.entity.ProcessamentoErro;
import com.br.apiprocarquivo.repository.PlanosRepository;
import com.br.apiprocarquivo.repository.ProcessamentoErroRepository;
import com.br.apiprocarquivo.repository.ProcessamentoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import static com.br.apiprocarquivo.helper.ArquivoHelper.criarNomeArquivoAleatorio;
import static com.br.apiprocarquivo.helper.ArquivoHelper.isRowEmpty;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArquivoService {

    @Value("${processamento.limite.registros}")
    private int limiteDeRegistros;

    @Autowired
    private ProcessamentoRepository processamentoRepository;

    @Autowired
    private ProcessamentoErroRepository processamentoErroRepository;

    @Autowired
    private PlanosRepository planosRepository;

    public void processarArquivo(final MultipartFile arquivo) throws IOException {
        int totalRegistros = 0;

        try (InputStream fileInputStream = arquivo.getInputStream()) {
            final var workbook = WorkbookFactory.create(fileInputStream);
            final var sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (totalRegistros >= limiteDeRegistros) {
                    log.error("Não é suportado acima de 500 registros");
                    break;
                }

                final var nomeArquivoUnico = criarNomeArquivoAleatorio();

                final var processamento = Processamento.builder()
                        .nomeArquivo(nomeArquivoUnico)
                        .data(LocalDateTime.now())
                        .status(ProcessamentoStatus.AGUARDANDO)
                        .build();

                processamentoRepository.save(processamento);

                log.info("o processamento foi salvo com o nome {}, data {} e status {}",
                        processamento.getNomeArquivo(),
                        processamento.getData(),
                        processamento.getStatus());

                if (!isRowEmpty(row)) {
                    log.info("Iniciando a leitura da linha {}", row.getRowNum());
                    final var processadoComSucesso = processarLinha(row, processamento, nomeArquivoUnico);
                    if (!processadoComSucesso) {
                        continue;
                    }
                    totalRegistros++;
                }


                if (totalRegistros > 0) {
                    processamento.setStatus(ProcessamentoStatus.CONCLUIDO);
                } else {
                    processamento.setStatus(ProcessamentoStatus.PARCIAL);
                }
                processamento.setTotalRegistros(sheet.getLastRowNum());
                processamento.setQuantidadeProcessada(totalRegistros);
                processamento.setData(LocalDateTime.now());
                processamentoRepository.save(processamento);

                log.info("o processamento foi atualizado com o nome {}, data {} e status {}",
                        processamento.getNomeArquivo(),
                        processamento.getData(),
                        processamento.getStatus());

            }

            log.info("Total de registros (linhas com dados): {}", totalRegistros);
        }
    }

    private boolean processarLinha(final Row row, final Processamento processamentoAtual, final String nomeArquivo) {

        if (isRowEmpty(row)) {
            return false;
        }

        try {
            log.info("iniciando o processamento do registro na linha {}, com processamento ID: {}, associado ao arquivo '{}'.", row.getRowNum(), processamentoAtual.getId(), nomeArquivo);

            final var nome = row.getCell(0);
            final var sobrenome = row.getCell(1);
            final var idade = row.getCell(2);

            if (nome == null || sobrenome == null || idade == null) {
                log.error("dados incompletos na linha {}", row.getRowNum());
                return false;
            }
            // Salvar registros com sucesso na tabela PLANOS;
            final var planos = Planos.builder()
                    .nomePlano("Plano " + criarNomeArquivoAleatorio())
                    .data(LocalDateTime.now())
                    .build();

            planosRepository.save(planos);

            log.error("salvo com sucesso o plano com o nome {} e data {}",
                    planos.getNomePlano(),
                    planos.getData());

            log.info("fim do processamento do registro na linha {}, com processamento ID: {}, associado ao arquivo '{}'.", row.getRowNum(), processamentoAtual.getId(), nomeArquivo);
            return true;
        } catch (Exception e) {
            // Salvar registros com erro na tabela PROCESSAMENTO_ERRO;
            log.error("erro ao processar linha {}: {}", row.getRowNum(), e.getMessage());
            final var processamentoErro = ProcessamentoErro.builder()
                    .processamento(processamentoAtual)
                    .status(ProcessamentoErroStatus.ERRO_AO_PROCESSAR)
                    .data(LocalDateTime.now())
                    .payload(nomeArquivo)
                    .build();

            processamentoErroRepository.save(processamentoErro);

            log.error("erro ao processar linha para o status {}, data {} e payload {}",
                    processamentoErro.getStatus(),
                    processamentoErro.getData(),
                    processamentoErro.getPayload());

            return false;
        }
    }

}

