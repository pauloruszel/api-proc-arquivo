package com.br.apiprocarquivo.api.dto;

import com.br.apiprocarquivo.api.messages.ApiMessageResponse;

public record ProcessamentoUploadResponse(Long processamentoId, ApiMessageResponse message) {
}
