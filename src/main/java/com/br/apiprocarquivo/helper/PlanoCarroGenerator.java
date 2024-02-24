package com.br.apiprocarquivo.helper;

import java.util.List;
import java.util.Random;

public class PlanoCarroGenerator {

    private static final List<String> PREFIXOS = List.of("Super", "Ultra", "Mega", "Power");
    private static final List<String> MODELOS = List.of("Sedan", "Hatch", "SUV", "Esportivo");
    private static final List<String> SUFIXOS = List.of("Plus", "Pro", "Max", "Elite");
    private static final Random random = new Random();

    public static String gerarNomePlano() {
        final var prefixo = PREFIXOS.get(random.nextInt(PREFIXOS.size()));
        final var modelo = MODELOS.get(random.nextInt(MODELOS.size()));
        final var sufixo = SUFIXOS.get(random.nextInt(SUFIXOS.size()));

        final var numero = random.nextInt(1000);

        return prefixo + " " + modelo + " " + sufixo + " " + numero;
    }

}