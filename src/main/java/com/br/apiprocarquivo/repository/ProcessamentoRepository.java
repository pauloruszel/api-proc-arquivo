package com.br.apiprocarquivo.repository;

import com.br.apiprocarquivo.domain.enums.ProcessamentoStatus;
import com.br.apiprocarquivo.entity.Processamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessamentoRepository extends JpaRepository<Processamento, Long> {

    List<Processamento> findAllByStatus(String status);

    @Query("SELECT p FROM Processamento p WHERE p.status IN (:status)")
    List<Processamento> findAllByStatusIn(@Param("status") List<ProcessamentoStatus> status);

    @Query("SELECT SUM(p.quantidadeProcessada) FROM Processamento p")
    Long sumQuantidadeProcessada();

    @Query("SELECT SUM(p.totalRegistros) FROM Processamento p")
    Long sumTotalRegistros();
}
