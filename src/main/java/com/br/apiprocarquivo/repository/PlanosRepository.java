package com.br.apiprocarquivo.repository;

import com.br.apiprocarquivo.entity.Planos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanosRepository extends JpaRepository<Planos, Long> {
}
