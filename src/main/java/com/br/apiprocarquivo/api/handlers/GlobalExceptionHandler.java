package com.br.apiprocarquivo.api.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("erro interno: {}", e.toString(), e);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("Erro interno do servidor. Por favor, entre em contato com o suporte.");
    }

}

