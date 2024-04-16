package com.br.apiprocarquivo.domain.repository;

import com.br.apiprocarquivo.domain.model.Planos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanosRepository extends JpaRepository<Planos, Long> {
}
