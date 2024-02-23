package com.br.apiprocarquivo.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProcessamentoStatus {
    PROCESSANDO("Processando"),
    AGUARDANDO("Aguardando"),
    CONCLUIDO("Concluído"),
    PARCIAL("Parcial");

    private final String status;

    public static ProcessamentoStatus fromStatus(String status) {
        for (ProcessamentoStatus ps : values()) {
            if (ps.getStatus().equalsIgnoreCase(status)) {
                return ps;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + status);
    }

}
