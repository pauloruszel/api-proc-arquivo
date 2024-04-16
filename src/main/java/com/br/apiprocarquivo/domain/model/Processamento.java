package com.br.apiprocarquivo.domain.model;

import com.br.apiprocarquivo.domain.enums.ProcessamentoStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Processamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    private LocalDateTime data;

    @Column(name = "total_registros")
    private Integer totalRegistros;

    @Column(name = "quantidade_processada")
    private Integer quantidadeProcessada;

    @Enumerated(EnumType.STRING)
    private ProcessamentoStatus status;
}
