package com.br.apiprocarquivo.domain.service;

import com.br.apiprocarquivo.domain.model.Processamento;
import com.br.apiprocarquivo.domain.model.ProcessamentoErro;

import java.util.List;

public interface ProcessamentoQueryService {

    Processamento buscarPorId(Long id);

    List<ProcessamentoErro> buscarErros(Long processamentoId);
}
