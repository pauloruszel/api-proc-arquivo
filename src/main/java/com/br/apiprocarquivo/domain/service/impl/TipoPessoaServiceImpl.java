package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.TipoPessoa;
import com.br.apiprocarquivo.domain.repository.TipoPessoaRepository;
import com.br.apiprocarquivo.domain.service.TipoPessoaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipoPessoaServiceImpl implements TipoPessoaService {

    private final TipoPessoaRepository tipoPessoaRepository;

    @Override
    public TipoPessoa salvarSeNaoExiste(VeiculoCotacaoModel veiculoCotacaoModel) {
        return tipoPessoaRepository.findByNomeAndAtivo(veiculoCotacaoModel.getCodigoCliente(), true)
                .orElseGet(() -> {
                    final var tipoPessoa = TipoPessoa.builder()
                            .nome(veiculoCotacaoModel.getCodigoCliente())
                            .ativo(true)
                            .build();

                    return tipoPessoaRepository.save(tipoPessoa);
                });
    }
}