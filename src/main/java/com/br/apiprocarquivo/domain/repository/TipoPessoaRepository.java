package com.br.apiprocarquivo.domain.repository;

import com.br.apiprocarquivo.domain.model.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoPessoaRepository extends JpaRepository<TipoPessoa, Long> {

    Optional<TipoPessoa> findByNomeAndAtivo(String nome, boolean ativo);
}