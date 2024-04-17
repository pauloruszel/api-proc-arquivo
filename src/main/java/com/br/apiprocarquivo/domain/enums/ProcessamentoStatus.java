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

}
