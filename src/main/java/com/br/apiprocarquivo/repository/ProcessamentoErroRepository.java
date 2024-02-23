package com.br.apiprocarquivo.repository;

import com.br.apiprocarquivo.entity.ProcessamentoErro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessamentoErroRepository extends JpaRepository<ProcessamentoErro, Long> {
}
