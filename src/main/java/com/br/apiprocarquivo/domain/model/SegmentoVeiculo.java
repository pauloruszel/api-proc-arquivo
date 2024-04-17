package com.br.apiprocarquivo.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SegmentoVeiculo implements Serializable {

    @Serial
    private static final long serialVersionUID = -7547958089321426242L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_segmento_veiculo")
    private Long idSegmentoVeiculo;

    private String nome;

    private boolean ativo;
}
