package com.br.apiprocarquivo.api.controller;

import com.br.apiprocarquivo.domain.service.ArquivoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.br.apiprocarquivo.infrastructure.helper.ArquivoHelper.isArquivoExcelValido;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ProcessamentoController {

    private final ArquivoService arquivoService;

    public ProcessamentoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @PostMapping(value = "/arquivos/upload", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadArquivo(@RequestParam("arquivo") MultipartFile arquivo) {

        try {
            if (arquivo.isEmpty()) {
                log.warn("arquivo vazio ou nao enviado");
                return status(BAD_REQUEST).body("arquivo não enviado.");
            }

            if (!isArquivoExcelValido(arquivo)) {
                log.warn("formato de arquivo não suportado.");
                return status(BAD_REQUEST).body("formato de arquivo não suportado. apenas .xlsx é permitido.");
            }

            log.info("iniciando o processamento do arquivo {}", arquivo.getOriginalFilename());
            arquivoService.processarArquivo(arquivo);
            log.info("fim do processamento do arquivo {}", arquivo.getOriginalFilename());

            return ResponseEntity.ok("arquivo recebido e processado");
        } catch (Exception e) {
            log.error("erro ao processar arquivo {}: {}", arquivo.getOriginalFilename(), e, e);
            return status(INTERNAL_SERVER_ERROR).body("Erro ao processar arquivo: " + e.getMessage());
        }
    }


}