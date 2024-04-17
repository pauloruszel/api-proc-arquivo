package com.br.apiprocarquivo.domain.service;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.ModeloVeiculo;

public interface ModeloVeiculoService {

    ModeloVeiculo salvarSeNaoExiste(VeiculoCotacaoModel veiculoCotacaoModel);

}