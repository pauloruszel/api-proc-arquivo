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
public class VeiculoCotacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 6082089896878589608L;
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
