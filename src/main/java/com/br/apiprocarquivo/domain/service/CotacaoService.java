package com.br.apiprocarquivo.domain.service;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.Cotacao;

public interface CotacaoService {

    Cotacao salvarSeNaoExiste(VeiculoCotacaoModel veiculoCotacaoModel);

}