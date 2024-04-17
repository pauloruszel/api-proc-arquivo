package com.br.apiprocarquivo.domain.model;

import com.br.apiprocarquivo.domain.enums.ProcessamentoErroStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProcessamentoErro implements Serializable {

    @Serial
    private static final long serialVersionUID = 8718943589162118485L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_processamento", nullable = false)
    private Processamento processamento;

    @Enumerated(EnumType.STRING)
    private ProcessamentoErroStatus status;

    private LocalDateTime data;

    @Column(name = "payload")
    private String payload;
}