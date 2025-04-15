package com.aulas.banheiro;

public class Banheiro {
    private boolean ocupado = false;

    public synchronized void usarBanheiro(String nome, boolean num2) {

        while (ocupado) {
            try {
                System.out.println(nome + " está esperando...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println(nome + " entrou no banheiro");
        ocupado = true;

        try {
            if (num2) {
                System.out.println(nome + " está fazendo o número 2");
                System.out.println("Puxando descarga");
                System.out.println("Lavando as mãos");
                Thread.sleep(5000);
            } else {
                System.out.println(nome + " está fazendo o número 1");
                System.out.println("Puxando descarga");
                System.out.println("Lavando as mãos");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(nome + " saiu do banheiro.");
            ocupado = false;
            notifyAll();
        }
    }

}
