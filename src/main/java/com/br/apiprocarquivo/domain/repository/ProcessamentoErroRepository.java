package com.br.apiprocarquivo.domain.repository;

import com.br.apiprocarquivo.domain.model.ProcessamentoErro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessamentoErroRepository extends JpaRepository<ProcessamentoErro, Long> {

    List<ProcessamentoErro> findAllByProcessamentoIdOrderByDataAsc(Long processamentoId);
}
