package com.br.apiprocarquivo.domain.repository;

import com.br.apiprocarquivo.domain.model.Cotacao;
import com.br.apiprocarquivo.domain.model.TipoPessoa;
import com.br.apiprocarquivo.domain.model.Veiculo;
import com.br.apiprocarquivo.domain.model.VeiculoCotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VeiculoCotacaoRepository extends JpaRepository<VeiculoCotacao, Long> {
    Optional<VeiculoCotacao> findByVeiculoAndCotacaoAndAndTipoPessoaAndAtivo(Veiculo veiculo, Cotacao cotacao, TipoPessoa tipoPessoa, boolean ativo);
}