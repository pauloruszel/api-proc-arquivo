package com.br.apiprocarquivo.domain.service;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import com.br.apiprocarquivo.domain.model.TipoPessoa;

public interface TipoPessoaService {

    TipoPessoa salvarSeNaoExiste(VeiculoCotacaoModel veiculoCotacaoModel);

}