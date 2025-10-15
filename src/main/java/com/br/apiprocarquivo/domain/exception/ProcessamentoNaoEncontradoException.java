package com.br.apiprocarquivo.domain.exception;

public class ProcessamentoNaoEncontradoException extends RuntimeException {

    private final Long id;

    public ProcessamentoNaoEncontradoException(final Long id) {
        super("Processamento não encontrado: " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
