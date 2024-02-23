package com.br.apiprocarquivo.entity;

import com.br.apiprocarquivo.domain.enums.ProcessamentoErroStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessamentoErro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_processamento", nullable = false)
    private Processamento processamento;

    @Enumerated(EnumType.STRING)
    private ProcessamentoErroStatus status;

    private LocalDateTime data;

    @Lob
    @Column(name = "payload")
    private String payload;
}