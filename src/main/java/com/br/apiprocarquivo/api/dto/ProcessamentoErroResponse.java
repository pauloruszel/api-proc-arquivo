package com.br.apiprocarquivo.api.dto;

import com.br.apiprocarquivo.domain.enums.ProcessamentoErroStatus;

import java.time.LocalDateTime;

public record ProcessamentoErroResponse(
        Long id,
        ProcessamentoErroStatus status,
        LocalDateTime data,
        String descricao) {
}
