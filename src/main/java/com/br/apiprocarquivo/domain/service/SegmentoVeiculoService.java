package com.br.apiprocarquivo.domain.service;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.SegmentoVeiculo;

public interface SegmentoVeiculoService {

    SegmentoVeiculo salvarSeNaoExiste(VeiculoCotacaoModel veiculoCotacaoModel);

}