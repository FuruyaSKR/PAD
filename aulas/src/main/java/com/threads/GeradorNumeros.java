package com.aulas.threads;

import java.util.Random;

public class GeradorNumeros {
    private boolean turnoPar = true;
    private boolean terminadoPar = false;
    private boolean terminadoImpar = false;

    public synchronized void gerarPar(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            while (!turnoPar) {
                if (terminadoImpar)
                    return;
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            int numero = new Random().nextInt(50) * 2;
            System.out.println("Par: " + numero);
            turnoPar = false;
            notify();
        }
        terminadoPar = true;
        notify();
    }

    public synchronized void gerarImpar(int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            while (turnoPar) {
                if (terminadoPar)
                    return;
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            int numero = new Random().nextInt(50) * 2 + 1;
            System.out.println("Ímpar: " + numero);
            turnoPar = true;
            notify();
        }
        terminadoImpar = true;
        notify();
    }
}
