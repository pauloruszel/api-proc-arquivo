package com.br.apiprocarquivo.infrastructure.helper;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

import static com.br.apiprocarquivo.infrastructure.helper.CellValueConverter.*;
import static org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK;

@Slf4j
public class ArquivoHelper {

    public static boolean isArquivoExcelValido(final MultipartFile arquivo) {
        final var contentType = arquivo.getContentType();
        final var filename = arquivo.getOriginalFilename();
        return (contentType != null && contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) ||
                (filename != null && filename.endsWith(".xlsx"));
    }

    public static boolean isRowComplete(final Row row) {
        if (row == null) {
            return false;
        }

        Cell[] cells = new Cell[21];
        for (int i = 0; i <= 20; i++) {
            cells[i] = row.getCell(i, CREATE_NULL_AS_BLANK);
        }

        for (int i = 0; i <= 20; i++) {
            Cell cell = cells[i];
            boolean isValid;
            switch (i) {
                case 8, 9, 14, 15 -> isValid = getCellValueAsLong(cell) != -1L;
                case 13, 18 -> isValid = getCellValueAsInteger(cell) != -1;
                case 16, 17, 19 -> isValid = getCellValueAsBigDecimal(cell).compareTo(BigDecimal.ZERO) != 0;
                default -> isValid = !getCellValueAsString(cell).isEmpty();
            }
            if (!isValid) {
                return false;
            }
        }
        return true;
    }


    public static VeiculoCotacaoModel createVeiculoCotacaoModelFromRow(final Row row) {
        if (!isRowComplete(row)) {
            return null;
        }

        return VeiculoCotacaoModel.builder()
                .codigoCliente(getCellValueAsString(row.getCell(0, CREATE_NULL_AS_BLANK)))
                .cotacao(getCellValueAsString(row.getCell(1, CREATE_NULL_AS_BLANK)))
                .marca(getCellValueAsString(row.getCell(2, CREATE_NULL_AS_BLANK)))
                .segmento(getCellValueAsString(row.getCell(3, CREATE_NULL_AS_BLANK)))
                .modelo(getCellValueAsString(row.getCell(4, CREATE_NULL_AS_BLANK)))
                .versao(getCellValueAsString(row.getCell(5, CREATE_NULL_AS_BLANK)))
                .motor(getCellValueAsString(row.getCell(6, CREATE_NULL_AS_BLANK)))
                .transmissao(getCellValueAsString(row.getCell(7, CREATE_NULL_AS_BLANK)))
                .anoModelo(getCellValueAsLong(row.getCell(8, CREATE_NULL_AS_BLANK)))
                .anoProducao(getCellValueAsLong(row.getCell(9, CREATE_NULL_AS_BLANK)))
                .fsc(getCellValueAsString(row.getCell(10, CREATE_NULL_AS_BLANK)))
                .ocn(getCellValueAsString(row.getCell(11, CREATE_NULL_AS_BLANK)))
                .pack(getCellValueAsString(row.getCell(12, CREATE_NULL_AS_BLANK)))
                .prazo(getCellValueAsInteger(row.getCell(13, CREATE_NULL_AS_BLANK)))
                .km(getCellValueAsLong(row.getCell(14, CREATE_NULL_AS_BLANK)))
                .kmMensal(getCellValueAsLong(row.getCell(15, CREATE_NULL_AS_BLANK)))
                .mensalidade(getCellValueAsBigDecimal(row.getCell(16, CREATE_NULL_AS_BLANK)))
                .kmSuperior(getCellValueAsBigDecimal(row.getCell(17, CREATE_NULL_AS_BLANK)))
                .pneus(getCellValueAsInteger(row.getCell(18, CREATE_NULL_AS_BLANK)))
                .cooparticipacaoAcidente(getCellValueAsBigDecimal(row.getCell(19, CREATE_NULL_AS_BLANK)))
                .cidadeCirculacao(getCellValueAsString(row.getCell(20, CREATE_NULL_AS_BLANK)))
                .build();
    }

    public static String criarNomeArquivoAleatorio() {
        return UUID.randomUUID().toString();
    }


}
