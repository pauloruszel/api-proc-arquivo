package com.br.apiprocarquivo.domain.repository;

import com.br.apiprocarquivo.domain.model.ModeloVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeloVeiculoRepository extends JpaRepository<ModeloVeiculo, Long> {

    Optional<ModeloVeiculo> findByNomeAndAndAtivo(String marca, boolean ativo);
}