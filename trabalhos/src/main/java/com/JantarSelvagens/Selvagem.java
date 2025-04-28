package com.JantarSelvagens;

import java.util.Random;

public class Selvagem extends Thread {
    private final Caldeirao caldeirao;
    private final Random random = new Random();

    public Selvagem(Caldeirao caldeirao) {
        this.caldeirao = caldeirao;
    }

    @Override
    public void run() {
        try {
            while (true) {
                caldeirao.servir();
                comer();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void comer() throws InterruptedException {
        System.out.println("Um selvagem está comendo...");
        Thread.sleep(500 + random.nextInt(1000));
    }
}
