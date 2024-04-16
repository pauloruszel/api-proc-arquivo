package com.br.apiprocarquivo.infrastructure.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

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

        Cell nomeCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        Cell sobrenomeCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        Cell idadeCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);


        String nome = getCellValueAsString(nomeCell);
        String sobrenome = getCellValueAsString(sobrenomeCell);
        int idade = getCellValueAsInt(idadeCell);

        return !nome.isEmpty() && !sobrenome.isEmpty() && idade != -1;
    }

    public static String criarNomeArquivoAleatorio() {
        return UUID.randomUUID().toString();
    }

    private static int getCellValueAsInt(Cell cell) {
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            } else {
                return Integer.parseInt(cell.getStringCellValue().trim());
            }
        } catch (NumberFormatException | IllegalStateException e) {
            return -1;
        }
    }

    private static String getCellValueAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> NumberToTextConverter.toText(cell.getNumericCellValue());
            default -> "";
        };
    }

}
