package com.br.apiprocarquivo.domain.service;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.ModeloVeiculo;
import com.br.apiprocarquivo.domain.model.SegmentoVeiculo;
import com.br.apiprocarquivo.domain.model.Veiculo;

public interface VeiculoService {

    Veiculo salvarSeNaoExiste(VeiculoCotacaoModel veiculoCotacaoModel, SegmentoVeiculo segmentoVeiculo, ModeloVeiculo modeloVeiculo);

}