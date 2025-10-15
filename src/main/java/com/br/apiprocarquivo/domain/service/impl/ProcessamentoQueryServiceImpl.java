package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.domain.exception.ProcessamentoNaoEncontradoException;
import com.br.apiprocarquivo.domain.model.Processamento;
import com.br.apiprocarquivo.domain.model.ProcessamentoErro;
import com.br.apiprocarquivo.domain.repository.ProcessamentoErroRepository;
import com.br.apiprocarquivo.domain.repository.ProcessamentoRepository;
import com.br.apiprocarquivo.domain.service.ProcessamentoQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessamentoQueryServiceImpl implements ProcessamentoQueryService {

    private final ProcessamentoRepository processamentoRepository;
    private final ProcessamentoErroRepository processamentoErroRepository;

    @Override
    public Processamento buscarPorId(final Long id) {
        return processamentoRepository.findById(id)
                .orElseThrow(() -> new ProcessamentoNaoEncontradoException(id));
    }

    @Override
    public List<ProcessamentoErro> buscarErros(final Long processamentoId) {
        buscarPorId(processamentoId);
        return processamentoErroRepository.findAllByProcessamentoIdOrderByDataAsc(processamentoId);
    }
}
