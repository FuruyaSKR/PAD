package com.JantarSelvagens;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final int N_PORCOES_INICIAL = 5;
        final int NUM_SELVAGENS = 10;
        final int NUM_COZINHEIROS = 1;
        final int TEMPO_EXECUCAO_MS = 10000;

        List<Thread> todasAsThreads = new ArrayList<>();
        Caldeirao caldeirao = new Caldeirao(N_PORCOES_INICIAL);

        for (int i = 0; i < NUM_SELVAGENS; i++) {
            Thread selvagem = new Selvagem(caldeirao);
            todasAsThreads.add(selvagem);
            selvagem.start();
        }

        for (int i = 0; i < NUM_COZINHEIROS; i++) {
            Thread cozinheiro = new Cozinheiro(caldeirao);
            todasAsThreads.add(cozinheiro);
            cozinheiro.start();
        }

        try {
            Thread.sleep(TEMPO_EXECUCAO_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (Thread thread : todasAsThreads) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Execução finalizada com sucesso.");
    }
}
