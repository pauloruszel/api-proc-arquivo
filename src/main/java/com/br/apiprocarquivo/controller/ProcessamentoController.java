package com.br.apiprocarquivo.controller;

import com.br.apiprocarquivo.repository.ProcessamentoRepository;
import com.br.apiprocarquivo.service.ArquivoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.br.apiprocarquivo.domain.enums.ProcessamentoStatus.AGUARDANDO;
import static com.br.apiprocarquivo.domain.enums.ProcessamentoStatus.PARCIAL;
import static com.br.apiprocarquivo.helper.ArquivoHelper.isArquivoExcelValido;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ProcessamentoController {

    @Autowired
    private ArquivoService arquivoService;

    @Autowired
    private ProcessamentoRepository processamentoRepository;

    @PostMapping("/arquivos/upload")
    public ResponseEntity<String> uploadArquivo(@RequestParam("arquivo") final MultipartFile arquivo) {
        try {
            if (arquivo.isEmpty()) {
                log.warn("arquivo vazio ou nao enviado");
                return status(BAD_REQUEST).body("Arquivo não enviado.");
            }

            if (!isArquivoExcelValido(arquivo)) {
                log.warn("Formato de arquivo não suportado.");
                return status(BAD_REQUEST).body("Formato de arquivo não suportado. Apenas .xlsx é permitido.");
            }

            log.info("iniciando a consulta dos arquivos com status de processamento. {} - {} ", AGUARDANDO, PARCIAL);
            final var processamentosAguardandoEParciais = processamentoRepository.findAllByStatusIn(List.of(AGUARDANDO, PARCIAL));
            log.info("fim da consulta dos arquivos com status de processamento. {}", processamentosAguardandoEParciais);

            if (processamentosAguardandoEParciais.isEmpty()) {
                log.warn("Não existem registros a serem processados");
            }

            log.info("iniciando o processamento do arquivo {}", arquivo.getOriginalFilename());
            arquivoService.processarArquivo(arquivo);
            log.info("fim do processamento do arquivo {}", arquivo.getOriginalFilename());

            return ResponseEntity.ok("Arquivo recebido e em processamento");
        } catch (Exception e) {
            log.error("erro ao processar arquivo {} ", e.getMessage());
            return status(INTERNAL_SERVER_ERROR).body("Erro ao processar arquivo: " + e.getMessage());
        }
    }

}