package com.br.apiprocarquivo.domain.service;

import com.br.apiprocarquivo.domain.model.Processamento;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public interface ProcessamentoService {

    boolean processarLinha(Row row, Processamento processamento);

    void atualizarStatusProcessamento(int linhasProcessadasComSucesso, Sheet sheet, Processamento processamento);
}
