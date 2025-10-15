package com.br.apiprocarquivo.api.handlers;

import com.br.apiprocarquivo.api.messages.ApiMessageCode;
import com.br.apiprocarquivo.api.messages.ApiMessageResponse;
import com.br.apiprocarquivo.api.messages.MessageTranslator;
import com.br.apiprocarquivo.domain.exception.ProcessamentoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageTranslator messageTranslator;

    @ExceptionHandler(ProcessamentoNaoEncontradoException.class)
    public ResponseEntity<ApiMessageResponse> handleProcessamentoNaoEncontrado(final ProcessamentoNaoEncontradoException ex) {
        log.warn("processamento nao encontrado: {}", ex.getId());
        return ResponseEntity.status(NOT_FOUND)
                .body(messageTranslator.toResponse(ApiMessageCode.PROCESSAMENTO_NAO_ENCONTRADO, ex.getId()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiMessageResponse> handleException(final Exception e) {
        log.error("erro interno: {}", e.toString(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(messageTranslator.toResponse(ApiMessageCode.PROCESSAMENTO_ERRO_INTERNO));
    }
}

