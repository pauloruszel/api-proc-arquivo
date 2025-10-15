package com.br.apiprocarquivo.api.messages;

public enum ApiMessageCode {
    PROCESSAMENTO_ARQUIVO_NAO_ENVIADO("PROCESSAMENTO_ARQUIVO_NAO_ENVIADO", "processamento.upload.file.missing"),
    PROCESSAMENTO_FORMATO_NAO_SUPORTADO("PROCESSAMENTO_FORMATO_NAO_SUPORTADO", "processamento.upload.file.invalid"),
    PROCESSAMENTO_RECEBIDO("PROCESSAMENTO_RECEBIDO", "processamento.upload.accepted"),
    PROCESSAMENTO_ERRO_INTERNO("PROCESSAMENTO_ERRO_INTERNO", "error.internal"),
    PROCESSAMENTO_NAO_ENCONTRADO("PROCESSAMENTO_NAO_ENCONTRADO", "processamento.nao.encontrado");

    private final String code;
    private final String messageKey;

    ApiMessageCode(final String code, final String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public String getCode() {
        return code;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
