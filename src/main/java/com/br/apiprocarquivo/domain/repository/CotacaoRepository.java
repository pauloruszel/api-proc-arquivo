package com.br.apiprocarquivo.domain.repository;

import com.br.apiprocarquivo.domain.model.Cotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CotacaoRepository extends JpaRepository<Cotacao, Long> {

    Optional<Cotacao> findByCodigoCotacaoAndAtivo(String codigoCotacao, boolean ativo);
}