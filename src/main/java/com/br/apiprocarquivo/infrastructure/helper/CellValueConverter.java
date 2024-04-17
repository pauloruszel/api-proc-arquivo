package com.br.apiprocarquivo.infrastructure.helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.math.BigDecimal;

public class CellValueConverter {

    static String getCellValueAsString(final Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> NumberToTextConverter.toText(cell.getNumericCellValue());
            default -> "";
        };
    }

    static BigDecimal getCellValueAsBigDecimal(final Cell cell) {
        return switch (cell.getCellType()) {
            case NUMERIC -> BigDecimal.valueOf(cell.getNumericCellValue());
            case STRING -> parseBigDecimal(cell.getStringCellValue().trim().replace(",", "."));
            default -> BigDecimal.ZERO;
        };
    }


    static Long getCellValueAsLong(final Cell cell) {
        return switch (cell.getCellType()) {
            case NUMERIC -> (long) cell.getNumericCellValue();
            case STRING -> parseLong(cell.getStringCellValue().trim());
            default -> -1L;
        };
    }

    static Integer getCellValueAsInteger(final Cell cell) {
        return switch (cell.getCellType()) {
            case NUMERIC -> (int) cell.getNumericCellValue();
            case STRING -> parseInteger(cell.getStringCellValue().trim());
            default -> -1;
        };
    }

    private static BigDecimal parseBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    private static Long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    private static Integer parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
