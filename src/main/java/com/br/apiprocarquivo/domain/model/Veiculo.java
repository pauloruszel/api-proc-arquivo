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
public class Veiculo implements Serializable {

    @Serial
    private static final long serialVersionUID = 8739020287619387468L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_veiculo")
    private Long idVeiculo;

    private String marca;

    @ManyToOne
    @JoinColumn(name = "id_segmento_veiculo")
    private SegmentoVeiculo segmentoVeiculo;

    @ManyToOne
    @JoinColumn(name = "id_modelo_veiculo")
    private ModeloVeiculo modeloVeiculo;

}
