package com.br.apiprocarquivo.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Cotacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 5082188176667948724L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cotacao")
    private Long idCotacao;

    @Column(name = "codigo_cotacao")
    private String codigoCotacao;

    @Column(name = "fsc")
    private String fsc;

    @Column(name = "ocn")
    private String ocn;

    @Column(name = "pack")
    private String pack;

    @Column(name = "prazo")
    private Integer prazo;

    @Column(name = "km")
    private Long km;

    @Column(name = "km_mensal")
    private Long kmMensal;

    @Column(name = "mensalidade")
    private BigDecimal mensalidade;

    @Column(name = "km_superior")
    private BigDecimal kmSuperior;

    @Column(name = "pneus")
    private Integer pneus;

    @Column(name = "cooparticipacao_acidente")
    private BigDecimal cooparticipacaoAcidente;

    @Column(name = "cidade_circulacao")
    private String cidadeCirculacao;

    private boolean ativo;

}
