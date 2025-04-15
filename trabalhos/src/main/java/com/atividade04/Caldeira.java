package com.atividade04;

import java.util.Random;

class Caldeira {
    private int temperatura = 0;
    private boolean ligada = false;
    private boolean emFuncionamento = true;

    public synchronized boolean estaLigada() {
        return ligada;
    }

    public synchronized int getTemperatura() {
        return temperatura;
    }

    public synchronized boolean emFuncionamento() {
        return emFuncionamento;
    }

    public synchronized void pararSistema() {
        emFuncionamento = false;
    }

    public synchronized void ligar() {
        if (!ligada) {
            ligada = true;
            System.out.println("Caldeira LIGADA.");
        }
    }

    public synchronized void alimentar() {
        if (ligada && temperatura < 1600) {
            int aumento = new Random().nextInt(200) + 100;
            temperatura += aumento;
            System.out.println("Caldeira alimentada. Temperatura atual: " + temperatura + " ºC");

            if (temperatura > 1600) {
                System.out.println("Temperatura CRÍTICA detectada: " + temperatura + " ºC");
            }
        }
    }

    public synchronized void desligar() {
        if (ligada) {
            ligada = false;
            System.out.println("Caldeira DESLIGADA por segurança.");
        }
    }

    public synchronized void limpar() {
        if (!ligada) {
            System.out.println("Limpando caldeira...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            temperatura = 0;
            System.out.println("Caldeira limpa. Temperatura zerada.");
        } else {
            System.out.println("A limpeza foi adiada: caldeira ainda ligada.");
        }
    }
}
