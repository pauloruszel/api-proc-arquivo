package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.SegmentoVeiculo;
import com.br.apiprocarquivo.domain.repository.SegmentoVeiculoRepository;
import com.br.apiprocarquivo.domain.service.SegmentoVeiculoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SegmentoVeiculoServiceImpl implements SegmentoVeiculoService {

    private final SegmentoVeiculoRepository segmentoVeiculoRepository;

    @Override
    public SegmentoVeiculo salvarSeNaoExiste(final VeiculoCotacaoModel veiculoCotacaoModel) {
        return segmentoVeiculoRepository.findByNomeAndAndAtivo(veiculoCotacaoModel.getSegmento(), true)
                .orElseGet(() -> {
                    final var segmentoVeiculo = SegmentoVeiculo.builder()
                            .nome(veiculoCotacaoModel.getSegmento())
                            .ativo(true)
                            .build();

                    return segmentoVeiculoRepository.save(segmentoVeiculo);
                });
    }
}