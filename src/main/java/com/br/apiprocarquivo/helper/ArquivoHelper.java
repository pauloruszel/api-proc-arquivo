package com.br.apiprocarquivo.helper;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class ArquivoHelper {

    public static boolean isArquivoExcelValido(final MultipartFile arquivo) {
        final var contentType = arquivo.getContentType();
        final var filename = arquivo.getOriginalFilename();
        return (contentType != null && contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) ||
                (filename != null && filename.endsWith(".xlsx"));
    }

    public static boolean isRowEmpty(final Row row) {
        if (row == null) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            var cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != BLANK && isNotBlank(cell.toString())) {
                return false;
            }
        }
        return true;
    }

    public static String criarNomeArquivoAleatorio() {
        return UUID.randomUUID().toString();
    }

}
