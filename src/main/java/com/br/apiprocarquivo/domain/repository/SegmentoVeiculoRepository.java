package com.br.apiprocarquivo.domain.repository;

import com.br.apiprocarquivo.domain.model.SegmentoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SegmentoVeiculoRepository extends JpaRepository<SegmentoVeiculo, Long> {
    Optional<SegmentoVeiculo> findByNomeAndAndAtivo(String marca, boolean ativo);
}