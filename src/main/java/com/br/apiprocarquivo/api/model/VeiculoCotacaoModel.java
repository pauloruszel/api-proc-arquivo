package com.br.apiprocarquivo.api.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeiculoCotacaoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -5800762519104879241L;

    private String codigoCliente;
    private String cotacao;
    private String marca;
    private String segmento;
    private String modelo;
    private String versao;
    private String motor;
    private String transmissao;
    private Long anoModelo;
    private Long anoProducao;
    private String fsc;
    private String ocn;
    private String pack;
    private Integer prazo;
    private Long km;
    private Long kmMensal;
    private BigDecimal mensalidade;
    private BigDecimal kmSuperior;
    private Integer pneus;
    private BigDecimal cooparticipacaoAcidente;
    private String cidadeCirculacao;
}
