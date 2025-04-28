package com.JantarSelvagens;

public class Caldeirao {
    private int porcoesRestantes;
    private final int capacidadeMaxima;

    public Caldeirao(int capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
        this.porcoesRestantes = capacidadeMaxima;
    }

    public synchronized void servir() throws InterruptedException {
        while (porcoesRestantes == 0) {
            wait();
        }
        porcoesRestantes--;
        System.out.println("Selvagem se serviu. Porções restantes: " + porcoesRestantes);
        if (porcoesRestantes == 0) {
            notifyAll();
        }
    }

    public synchronized void encher() throws InterruptedException {
        while (porcoesRestantes > 0) {
            wait();
        }
        porcoesRestantes = capacidadeMaxima;
        System.out.println("Cozinheiro encheu o caldeirão! Porções disponíveis: " + capacidadeMaxima);
        notifyAll();
    }
}
