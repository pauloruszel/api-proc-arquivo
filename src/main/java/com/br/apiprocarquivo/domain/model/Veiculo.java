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
@Entity
public class Veiculo {

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
