package com.br.apiprocarquivo.domain.repository;

import com.br.apiprocarquivo.domain.model.ProcessamentoErro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessamentoErroRepository extends JpaRepository<ProcessamentoErro, Long> {
}
