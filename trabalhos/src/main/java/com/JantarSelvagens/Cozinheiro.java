package com.JantarSelvagens;

import java.util.Random;

public class Cozinheiro extends Thread {
    private final Caldeirao caldeirao;
    private final Random random = new Random();

    public Cozinheiro(Caldeirao caldeirao) {
        this.caldeirao = caldeirao;
    }

    @Override
    public void run() {
        try {
            while (true) {
                caldeirao.encher();
                dormir();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void dormir() throws InterruptedException {
        System.out.println("O cozinheiro está dormindo...");
        Thread.sleep(500 + random.nextInt(1000));
    }
}
