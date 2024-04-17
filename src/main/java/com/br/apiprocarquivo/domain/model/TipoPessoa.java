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
public class TipoPessoa implements Serializable {

    @Serial
    private static final long serialVersionUID = -5560916220091790605L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_pessoa")
    private Long idTipoPessoa;

    private String nome;

    private boolean ativo;

}
