package com.br.apiprocarquivo.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "modelo_veiculo")
public class ModeloVeiculo {

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
    private String anoModelo;

    @Column(name = "ano_fabricacao")
    private String anoFabricacao;

}
