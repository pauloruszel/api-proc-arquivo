package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.ModeloVeiculo;
import com.br.apiprocarquivo.domain.repository.ModeloVeiculoRepository;
import com.br.apiprocarquivo.domain.service.ModeloVeiculoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModeloVeiculoServiceImpl implements ModeloVeiculoService {

    private final ModeloVeiculoRepository modeloVeiculoRepository;

    @Override
    public ModeloVeiculo salvarSeNaoExiste(VeiculoCotacaoModel veiculoCotacaoModel) {
        return modeloVeiculoRepository.findByNomeAndAndAtivo(veiculoCotacaoModel.getModelo(), true)
                .orElseGet(() -> {
                    final var modeloVeiculo = ModeloVeiculo.builder()
                            .nome(veiculoCotacaoModel.getModelo())
                            .ativo(true)
                            .versao(veiculoCotacaoModel.getVersao())
                            .motor(veiculoCotacaoModel.getMotor())
                            .transmissao(veiculoCotacaoModel.getTransmissao())
                            .anoModelo(veiculoCotacaoModel.getAnoModelo())
                            .anoFabricacao(veiculoCotacaoModel.getAnoProducao())
                            .build();

                    return modeloVeiculoRepository.save(modeloVeiculo);
                });
    }

}