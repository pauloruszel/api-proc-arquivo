package com.br.apiprocarquivo;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

class GenerateSampleSpreadsheet {

    private static final int TOTAL = 5_000;

    @Test
    void generate() throws IOException {
        Path docsDir = Path.of("docs");
        if (!Files.exists(docsDir)) {
            Files.createDirectories(docsDir);
        }

        // Arrays-base (pode ajustar/expandir à vontade)
        String[] marcas = {"Toyota", "Volkswagen", "Chevrolet", "Hyundai", "Fiat", "Honda", "Renault", "Nissan", "Jeep", "Peugeot"};
        String[] segmentos = {"SUV", "Hatch", "Sedan", "Pickup", "Crossover", "Minivan"};
        String[] modelosToyota = {"Corolla Cross", "Yaris", "Hilux", "SW4"};
        String[] modelosVW = {"Polo", "T-Cross", "Virtus", "Nivus"};
        String[] modelosGM = {"Onix", "Onix Plus", "Tracker", "S10"};
        String[] modelosHyundai = {"HB20", "HB20S", "Creta"};
        String[] modelosFiat = {"Argo", "Pulse", "Toro", "Strada"};
        String[] modelosHonda = {"Civic", "City", "HR-V"};
        String[] modelosRenault = {"Kwid", "Stepway", "Duster"};
        String[] modelosNissan = {"Kicks", "Versa", "Frontier"};
        String[] modelosJeep = {"Renegade", "Compass", "Commander"};
        String[] modelosPeugeot = {"208", "2008"};
        String[] versoes = {"Entry", "Drive", "Comfortline", "Highline", "Limited", "Premier", "Sport", "XRE"};
        String[] motores = {"1.0", "1.0 Turbo", "1.3", "1.6", "1.6 16V", "1.8", "2.0 Flex", "2.0 Turbo"};
        String[] transmissoes = {"Manual", "Automática", "CVT"};
        String[] packs = {"Básico", "Conforto", "Tech", "Premium", "Off-road"};
        String[] cidades = {"São Paulo", "Curitiba", "Belo Horizonte", "Rio de Janeiro", "Porto Alegre",
                "Fortaleza", "Salvador", "Recife", "Manaus", "Goiânia", "Brasília", "Florianópolis"};
        int[] prazos = {12, 18, 24, 30, 36, 48};
        int[] kmsAno = {12000, 18000, 24000, 30000, 36000};

        try (var workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Planilha");

            // Cabeçalho (para humanos)
            String[] headers = new String[]{
                    "codigoCliente", "cotacao", "marca", "segmento", "modelo", "versao", "motor", "transmissao",
                    "anoModelo", "anoProducao", "fsc", "ocn", "pack", "prazo", "km", "kmMensal",
                    "mensalidade", "kmSuperior", "pneus", "cooparticipacaoAcidente", "cidadeCirculacao"
            };
            Row header = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                header.createCell(i).setCellValue(headers[i]);
            }

            // Gerador (pode tornar determinístico definindo uma seed global, se quiser)
            ThreadLocalRandom r = ThreadLocalRandom.current();
            int currentYear = Year.now().getValue();

            for (int i = 1; i <= TOTAL; i++) {
                Row row = sheet.createRow(i);

                // Alterna PF/PJ, mas com pitada de aleatoriedade
                boolean pj = (i % 2 == 0) || r.nextInt(100) < 10;
                row.createCell(0).setCellValue(pj ? "PJ" : "PF");

                // IDs relativamente únicos
                row.createCell(1).setCellValue(String.format(Locale.ROOT, "COT-%d-%05d", currentYear, i));
                row.createCell(10).setCellValue(String.format(Locale.ROOT, "FSC%03d", 100 + (i % 900)));
                row.createCell(11).setCellValue(String.format(Locale.ROOT, "OCN%03d", 100 + ((i * 7) % 900))); // passo diferente p/ variar

                // Marca -> escolhe um modelo compatível
                String marca = pick(r, marcas);
                String modelo = switch (marca) {
                    case "Toyota" -> pick(r, modelosToyota);
                    case "Volkswagen" -> pick(r, modelosVW);
                    case "Chevrolet" -> pick(r, modelosGM);
                    case "Hyundai" -> pick(r, modelosHyundai);
                    case "Fiat" -> pick(r, modelosFiat);
                    case "Honda" -> pick(r, modelosHonda);
                    case "Renault" -> pick(r, modelosRenault);
                    case "Nissan" -> pick(r, modelosNissan);
                    case "Jeep" -> pick(r, modelosJeep);
                    case "Peugeot" -> pick(r, modelosPeugeot);
                    default -> "Modelo";
                };

                row.createCell(2).setCellValue(marca);
                row.createCell(3).setCellValue(pick(r, segmentos));
                row.createCell(4).setCellValue(modelo);
                row.createCell(5).setCellValue(pick(r, versoes));
                row.createCell(6).setCellValue(pick(r, motores));
                row.createCell(7).setCellValue(pick(r, transmissoes));

                // Anos (produção pode ser igual ou 1 ano antes do ano modelo)
                int anoModelo = currentYear - r.nextInt(0, 3);       // currentYear, -1, -2
                int anoProducao = anoModelo - r.nextInt(0, 2);       // mesmo ou -1
                row.createCell(8).setCellValue(anoModelo);
                row.createCell(9).setCellValue(anoProducao);

                // Pack / prazo / km
                int prazo = pick(r, prazos);
                int kmAno = pick(r, kmsAno);
                int kmMensal = Math.max(800, kmAno / 12); // arredonda mínimo
                row.createCell(12).setCellValue(pick(r, packs));
                row.createCell(13).setCellValue(prazo);
                row.createCell(14).setCellValue(kmAno);
                row.createCell(15).setCellValue(kmMensal);

                // Mensalidade base variável por segmento + motor + aleatório
                double baseSegmento = switch (row.getCell(3).getStringCellValue()) {
                    case "SUV" -> 1599.0;
                    case "Pickup" -> 1899.0;
                    case "Crossover" -> 1499.0;
                    case "Sedan" -> 1299.0;
                    case "Minivan" -> 1399.0;
                    default -> 1099.0; // Hatch ou outros
                };
                double motorAdj = row.getCell(6).getStringCellValue().contains("Turbo") ? 180.0
                        : row.getCell(6).getStringCellValue().contains("2.0") ? 250.0 : 0.0;
                double prazoAdj = (prazo >= 36 ? -120.0 : 0.0);
                double kmAdj = (kmAno > 24000 ? 160.0 : 0.0);
                double variacao = r.nextDouble(-80.0, 220.0);
                double mensalidade = round2(baseSegmento + motorAdj + prazoAdj + kmAdj + variacao);
                row.createCell(16).setCellValue(mensalidade);

                // Km superior (faixa de cobrança extra, percentual)
                double kmSuperior = round2(0.15 + (r.nextInt(0, 7) * 0.05)); // 0.15 a 0.45
                row.createCell(17).setCellValue(kmSuperior);

                // Pneus (qtde coberta por contrato) – entre 2 e 6
                row.createCell(18).setCellValue(randBetween(r, 2, 6));

                // Coparticipação em acidente – faixa por perfil PF/PJ
                double copart = pj ? round2(randDouble(r, 600, 1600)) : round2(randDouble(r, 400, 1200));
                row.createCell(19).setCellValue(copart);

                // Cidade de circulação
                row.createCell(20).setCellValue(pick(r, cidades));
            }

            // Larguras fixas (autoSize em 5k linhas pode ficar lento, mas se preferir, troque por autoSize)
            int[] widths = {3800, 4200, 4200, 3800, 5200, 4200, 4200, 4200, 3200, 3200, 3200,
                    3200, 3800, 2400, 3000, 3000, 3200, 3200, 2200, 4200, 5200};
            for (int i = 0; i < headers.length; i++) {
                sheet.setColumnWidth(i, widths[i]);
            }

            try (FileOutputStream fos = new FileOutputStream(docsDir.resolve("planilha-exemplo-5000.xlsx").toFile())) {
                workbook.write(fos);
            }
        }
    }

    // ===== Helpers =====

    private static String pick(ThreadLocalRandom r, String[] arr) {
        return arr[r.nextInt(arr.length)];
    }

    private static int pick(ThreadLocalRandom r, int[] arr) {
        return arr[r.nextInt(arr.length)];
    }

    private static int randBetween(ThreadLocalRandom r, int minIncl, int maxIncl) {
        return r.nextInt((maxIncl - minIncl) + 1) + minIncl;
    }

    private static double randDouble(ThreadLocalRandom r, double minIncl, double maxIncl) {
        return minIncl + (r.nextDouble() * (maxIncl - minIncl));
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}