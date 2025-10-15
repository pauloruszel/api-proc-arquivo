package com.br.apiprocarquivo.api.dto;

import com.br.apiprocarquivo.domain.enums.ProcessamentoStatus;

import java.time.LocalDateTime;

public record ProcessamentoDetalheResponse(
        Long id,
        String nomeArquivo,
        LocalDateTime data,
        Integer totalRegistros,
        Integer quantidadeProcessada,
        Integer limiteRegistrosUtilizado,
        ProcessamentoStatus status) {
}
