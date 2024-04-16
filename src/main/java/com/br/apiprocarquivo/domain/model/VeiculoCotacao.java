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
@Entity(name = "veiculo_cotacao")
public class VeiculoCotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_veiculo_cotacao")
    private Long idVeiculoCotacao;

    @ManyToOne
    @JoinColumn(name = "id_veiculo")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "id_cotacao")
    private Cotacao cotacao;

    @OneToOne
    @JoinColumn(name = "id_tipo_pessoa")
    private TipoPessoa tipoPessoa;

    private boolean ativo;

}
