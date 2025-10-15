package com.br.apiprocarquivo.api.controller;

import com.br.apiprocarquivo.api.dto.ProcessamentoDetalheResponse;
import com.br.apiprocarquivo.api.dto.ProcessamentoErroResponse;
import com.br.apiprocarquivo.api.dto.ProcessamentoUploadResponse;
import com.br.apiprocarquivo.api.messages.ApiMessageCode;
import com.br.apiprocarquivo.api.messages.MessageTranslator;
import com.br.apiprocarquivo.domain.service.ArquivoService;
import com.br.apiprocarquivo.domain.service.ProcessamentoQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.br.apiprocarquivo.infrastructure.helper.ArquivoHelper.isArquivoExcelValido;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProcessamentoController {

    private final ArquivoService arquivoService;
    private final ProcessamentoQueryService processamentoQueryService;
    private final MessageTranslator messageTranslator;

    @PostMapping(value = "/arquivos/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadArquivo(@RequestParam("arquivo") final MultipartFile arquivo) throws IOException {

        if (arquivo.isEmpty()) {
            log.warn("arquivo vazio ou nao enviado");
            return status(BAD_REQUEST)
                    .body(messageTranslator.toResponse(ApiMessageCode.PROCESSAMENTO_ARQUIVO_NAO_ENVIADO));
        }

        if (!isArquivoExcelValido(arquivo)) {
            log.warn("formato de arquivo nao suportado.");
            return status(BAD_REQUEST)
                    .body(messageTranslator.toResponse(ApiMessageCode.PROCESSAMENTO_FORMATO_NAO_SUPORTADO));
        }

        log.info("iniciando o processamento assíncrono do arquivo {}", arquivo.getOriginalFilename());
        final var processamento = arquivoService.iniciarProcessamento(arquivo);
        final var response = new ProcessamentoUploadResponse(
                processamento.getId(),
                messageTranslator.toResponse(ApiMessageCode.PROCESSAMENTO_RECEBIDO,
                        arquivo.getOriginalFilename(), processamento.getId())
        );
        return status(ACCEPTED).body(response);
    }

    @GetMapping("/processamentos/{id}")
    public ResponseEntity<ProcessamentoDetalheResponse> consultarProcessamento(@PathVariable("id") final Long id) {
        final var processamento = processamentoQueryService.buscarPorId(id);
        final var response = new ProcessamentoDetalheResponse(
                processamento.getId(),
                processamento.getNomeArquivo(),
                processamento.getData(),
                processamento.getTotalRegistros(),
                processamento.getQuantidadeProcessada(),
                processamento.getLimiteRegistrosUtilizado(),
                processamento.getStatus()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/processamentos/{id}/erros")
    public ResponseEntity<List<ProcessamentoErroResponse>> consultarErros(@PathVariable("id") final Long id) {
        final var erros = processamentoQueryService.buscarErros(id).stream()
                .map(erro -> new ProcessamentoErroResponse(
                        erro.getId(),
                        erro.getStatus(),
                        erro.getData(),
                        erro.getPayload()
                ))
                .toList();
        return ResponseEntity.ok(erros);
    }


}