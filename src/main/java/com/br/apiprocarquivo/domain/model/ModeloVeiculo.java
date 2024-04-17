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
public class ModeloVeiculo implements Serializable {

    @Serial
    private static final long serialVersionUID = 2104180363680609658L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo_veiculo")
    private Long idModeloVeiculo;

    private String nome;
    private boolean ativo;
    private String versao;
    private String motor;
    private String transmissao;

    @Column(name = "ano_modelo")
    private Long anoModelo;

    @Column(name = "ano_fabricacao")
    private Long anoFabricacao;

}
