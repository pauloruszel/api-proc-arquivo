package com.br.apiprocarquivo;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GenerateSampleSpreadsheet {

    @Test
    void generate() throws IOException {
        Path docsDir = Path.of("docs");
        if (!Files.exists(docsDir)) {
            Files.createDirectories(docsDir);
        }

        try (var workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Planilha");

            // Header (ignored by the application but useful for humans)
            String[] headers = new String[]{
                    "codigoCliente","cotacao","marca","segmento","modelo","versao","motor","transmissao",
                    "anoModelo","anoProducao","fsc","ocn","pack","prazo","km","kmMensal",
                    "mensalidade","kmSuperior","pneus","cooparticipacaoAcidente","cidadeCirculacao"
            };
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Row 1 (index 1)
            Row r1 = sheet.createRow(1);
            r1.createCell(0).setCellValue("PJ");
            r1.createCell(1).setCellValue("COT-2025-0001");
            r1.createCell(2).setCellValue("Toyota");
            r1.createCell(3).setCellValue("SUV");
            r1.createCell(4).setCellValue("Corolla Cross");
            r1.createCell(5).setCellValue("XRE");
            r1.createCell(6).setCellValue("2.0 Flex");
            r1.createCell(7).setCellValue("Automática");
            r1.createCell(8).setCellValue(2024);
            r1.createCell(9).setCellValue(2023);
            r1.createCell(10).setCellValue("FSC123");
            r1.createCell(11).setCellValue("OCN456");
            r1.createCell(12).setCellValue("Premium");
            r1.createCell(13).setCellValue(36);
            r1.createCell(14).setCellValue(30000);
            r1.createCell(15).setCellValue(1000);
            r1.createCell(16).setCellValue(1899.90);
            r1.createCell(17).setCellValue(0.35);
            r1.createCell(18).setCellValue(4);
            r1.createCell(19).setCellValue(1500.00);
            r1.createCell(20).setCellValue("São Paulo");

            // Row 2 (index 2)
            Row r2 = sheet.createRow(2);
            r2.createCell(0).setCellValue("PF");
            r2.createCell(1).setCellValue("COT-2025-0002");
            r2.createCell(2).setCellValue("Volkswagen");
            r2.createCell(3).setCellValue("Hatch");
            r2.createCell(4).setCellValue("Polo");
            r2.createCell(5).setCellValue("Highline");
            r2.createCell(6).setCellValue("1.0 TSI");
            r2.createCell(7).setCellValue("Automática");
            r2.createCell(8).setCellValue(2024);
            r2.createCell(9).setCellValue(2024);
            r2.createCell(10).setCellValue("FSC789");
            r2.createCell(11).setCellValue("OCN111");
            r2.createCell(12).setCellValue("Conforto");
            r2.createCell(13).setCellValue(24);
            r2.createCell(14).setCellValue(24000);
            r2.createCell(15).setCellValue(1000);
            r2.createCell(16).setCellValue(1299.00);
            r2.createCell(17).setCellValue(0.40);
            r2.createCell(18).setCellValue(4);
            r2.createCell(19).setCellValue(900.00);
            r2.createCell(20).setCellValue("Curitiba");

            // Row 3 (index 3)
            Row r3 = sheet.createRow(3);
            r3.createCell(0).setCellValue("PJ");
            r3.createCell(1).setCellValue("COT-2025-0003");
            r3.createCell(2).setCellValue("Chevrolet");
            r3.createCell(3).setCellValue("Sedan");
            r3.createCell(4).setCellValue("Onix Plus");
            r3.createCell(5).setCellValue("Premier");
            r3.createCell(6).setCellValue("1.0 Turbo");
            r3.createCell(7).setCellValue("Manual");
            r3.createCell(8).setCellValue(2023);
            r3.createCell(9).setCellValue(2023);
            r3.createCell(10).setCellValue("FSC555");
            r3.createCell(11).setCellValue("OCN222");
            r3.createCell(12).setCellValue("Básico");
            r3.createCell(13).setCellValue(12);
            r3.createCell(14).setCellValue(12000);
            r3.createCell(15).setCellValue(1000);
            r3.createCell(16).setCellValue(899.50);
            r3.createCell(17).setCellValue(0.25);
            r3.createCell(18).setCellValue(4);
            r3.createCell(19).setCellValue(500.00);
            r3.createCell(20).setCellValue("Belo Horizonte");

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(docsDir.resolve("planilha-exemplo.xlsx").toFile())) {
                workbook.write(fos);
            }
        }
    }
}

