package com.br.apiprocarquivo.infrastructure.helper;

import com.br.apiprocarquivo.api.model.VeiculoCotacaoModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.IntStream;

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

        return IntStream.rangeClosed(0, 20)
                .mapToObj(i -> row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK))
                .allMatch(cell -> {
                    int columnIndex = cell.getColumnIndex();
                    switch (columnIndex) {
                        case 8, 9, 14, 15 -> {
                            // Campos numéricos que devem ser long
                            return getCellValueAsLong(cell) != -1L;
                        }
                        case 13, 18 -> {
                            // Campos numéricos que devem ser inteiros
                            return getCellValueAsInteger(cell) != -1;
                        }
                        case 16, 17, 19 -> {
                            // Campos decimais
                            return getCellValueAsBigDecimal(cell).compareTo(BigDecimal.ZERO) != 0;
                        }
                        default -> {
                            // Campos de texto
                            return !getCellValueAsString(cell).isEmpty();
                        }
                    }
                });
    }

    public static VeiculoCotacaoModel createVeiculoCotacaoModelFromRow(final Row row) {
        if (!isRowComplete(row)) {
            return null;
        }

        return VeiculoCotacaoModel.builder()
                .codigoCliente(getCellValueAsString(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .cotacao(getCellValueAsString(row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .marca(getCellValueAsString(row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .segmento(getCellValueAsString(row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .modelo(getCellValueAsString(row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .versao(getCellValueAsString(row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .motor(getCellValueAsString(row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .transmissao(getCellValueAsString(row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .anoModelo(getCellValueAsLong(row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .anoProducao(getCellValueAsLong(row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .fsc(getCellValueAsString(row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .ocn(getCellValueAsString(row.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .pack(getCellValueAsString(row.getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .prazo(getCellValueAsInteger(row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .km(getCellValueAsLong(row.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .kmMensal(getCellValueAsLong(row.getCell(15, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .mensalidade(getCellValueAsBigDecimal(row.getCell(16, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .kmSuperior(getCellValueAsBigDecimal(row.getCell(17, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .pneus(getCellValueAsInteger(row.getCell(18, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .cooparticipacaoAcidente(getCellValueAsBigDecimal(row.getCell(19, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .cidadeCirculacao(getCellValueAsString(row.getCell(20, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                .build();
    }

    public static String criarNomeArquivoAleatorio() {
        return UUID.randomUUID().toString();
    }

    private static String getCellValueAsString(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> NumberToTextConverter.toText(cell.getNumericCellValue());
            default -> "";
        };
    }

    private static BigDecimal getCellValueAsBigDecimal(Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC) {
            return BigDecimal.valueOf(cell.getNumericCellValue());
        } else {
            try {
                return new BigDecimal(cell.getStringCellValue().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        }
    }

    private static Long getCellValueAsLong(Cell cell) {
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (long) cell.getNumericCellValue();
            } else {
                return Long.parseLong(cell.getStringCellValue().trim());
            }
        } catch (NumberFormatException | IllegalStateException e) {
            return -1L;
        }
    }

    private static Integer getCellValueAsInteger(Cell cell) {
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

}
