package com.br.apiprocarquivo.domain.service.impl;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.ModeloVeiculo;
import com.br.apiprocarquivo.domain.model.SegmentoVeiculo;
import com.br.apiprocarquivo.domain.model.Veiculo;
import com.br.apiprocarquivo.domain.repository.VeiculoRepository;
import com.br.apiprocarquivo.domain.service.VeiculoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VeiculoServiceImpl implements VeiculoService {

    private final VeiculoRepository veiculoRepository;

    @Override
    public Veiculo salvarSeNaoExiste(final VeiculoCotacaoModel model, final SegmentoVeiculo segmentoVeiculo, final ModeloVeiculo modeloVeiculo) {
        return veiculoRepository.findByMarca(model.getMarca())
                .orElseGet(() -> {
                    final var novoVeiculo = Veiculo.builder()
                            .marca(model.getMarca())
                            .segmentoVeiculo(segmentoVeiculo)
                            .modeloVeiculo(modeloVeiculo)
                            .build();

                    return veiculoRepository.save(novoVeiculo);
                });
    }

}