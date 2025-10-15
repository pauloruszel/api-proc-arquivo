package com.br.apiprocarquivo.domain.service;

import com.br.apiprocarquivo.domain.model.Processamento;
import org.apache.poi.ss.usermodel.Row;

public interface ProcessamentoService {

    boolean processarLinha(Row row, Processamento processamento, int posicaoLinha);

    void atualizarStatusProcessamento(int linhasProcessadasComSucesso, int totalRegistrosProcessados, Processamento processamento);
}
