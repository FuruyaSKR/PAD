package com.aulas.threads;

import java.util.Random;

public class GeradorPlacas implements Runnable {
    private int quantidade;
    private static final String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random random = new Random();

    public GeradorPlacas(int quantidade) {
        this.quantidade = quantidade;
    }

    private String gerarPlaca() {
        return String.format("%c%c%c%d%c%d%d",
                LETRAS.charAt(random.nextInt(26)), // 1ª letra
                LETRAS.charAt(random.nextInt(26)), // 2ª letra
                LETRAS.charAt(random.nextInt(26)), // 3ª letra
                random.nextInt(10), // Número
                LETRAS.charAt(random.nextInt(26)), // Letra do meio
                random.nextInt(10), // Número
                random.nextInt(10)); // Número
    }

    @Override
    public void run() {
        for (int i = 0; i < quantidade; i++) {
            System.out.println("Placa Gerada: " + gerarPlaca());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrompida!");
            }
        }
    }
}
