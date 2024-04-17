package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.Cotacao;
import com.br.apiprocarquivo.domain.model.TipoPessoa;
import com.br.apiprocarquivo.domain.model.Veiculo;
import com.br.apiprocarquivo.domain.model.VeiculoCotacao;
import com.br.apiprocarquivo.domain.repository.VeiculoCotacaoRepository;
import com.br.apiprocarquivo.domain.service.VeiculoCotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VeiculoCotacaoServiceImpl implements VeiculoCotacaoService {

    private final VeiculoCotacaoRepository veiculoCotacaoRepository;

    @Override
    public VeiculoCotacao salvarSeNaoExiste(final VeiculoCotacaoModel model, final Veiculo veiculo, final Cotacao cotacao, final TipoPessoa tipoPessoa) {
        return veiculoCotacaoRepository.findByVeiculoAndCotacaoAndAndTipoPessoaAndAtivo(veiculo, cotacao, tipoPessoa, true)
                .orElseGet(() -> {
                    final var veiculoCotacao = VeiculoCotacao.builder()
                            .veiculo(veiculo)
                            .cotacao(cotacao)
                            .tipoPessoa(tipoPessoa)
                            .ativo(true)
                            .build();

                    return veiculoCotacaoRepository.save(veiculoCotacao);
                });
    }
}