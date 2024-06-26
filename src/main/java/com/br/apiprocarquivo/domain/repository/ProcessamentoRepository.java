package com.br.apiprocarquivo.domain.repository;

import com.br.apiprocarquivo.domain.enums.ProcessamentoStatus;
import com.br.apiprocarquivo.domain.model.Processamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessamentoRepository extends JpaRepository<Processamento, Long> {

    List<Processamento> findAllByStatus(ProcessamentoStatus status);

    @Query("SELECT p FROM Processamento p WHERE p.status IN (:status)")
    List<Processamento> findAllByStatusIn(@Param("status") List<ProcessamentoStatus> status);

}
