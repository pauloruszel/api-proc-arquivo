package com.br.apiprocarquivo.domain.service;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.Cotacao;
import com.br.apiprocarquivo.domain.model.TipoPessoa;
import com.br.apiprocarquivo.domain.model.Veiculo;
import com.br.apiprocarquivo.domain.model.VeiculoCotacao;

public interface VeiculoCotacaoService {

    VeiculoCotacao salvarSeNaoExiste(VeiculoCotacaoModel veiculoCotacaoModel, Veiculo veiculo, Cotacao cotacao, TipoPessoa tipoPessoa);

}