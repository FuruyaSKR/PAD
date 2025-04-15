package com.Corrida;

import java.util.*;

public class Corrida {

    private static final int NUM_CARROS = 5;
    private static int numVoltas = 5;

    public static void main(String[] args) {
        SafetyCar safetyCar = new SafetyCar();
        List<Carro> carros = new ArrayList<>();
        List<String> ordemChegada = Collections.synchronizedList(new ArrayList<>());

        System.out.println("Corrida iniciada com " + NUM_CARROS + " carros!");

        for (int i = 1; i <= NUM_CARROS; i++) {
            carros.add(new Carro("Carro #" + i, safetyCar));
        }

        boolean safetyCarAtivado = false;

        for (int voltaGlobal = 1; voltaGlobal <= numVoltas; voltaGlobal++) {
            System.out.println("\nVolta " + voltaGlobal + " iniciada!");

            List<Thread> threadsVolta = new ArrayList<>();
            List<Carro> abandonosNestaVolta = Collections.synchronizedList(new ArrayList<>());

            for (Carro carro : carros) {
                Thread t = new Thread(() -> {
                    if (carro.isCorrendo()) {
                        boolean abandonouAntes = carro.isAbandonou();
                        carro.executarVolta();
                        if (!abandonouAntes && carro.isAbandonou()) {
                            abandonosNestaVolta.add(carro);
                        }
                    }
                });
                threadsVolta.add(t);
                t.start();
            }

            for (Thread t : threadsVolta) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            if (!safetyCarAtivado && !abandonosNestaVolta.isEmpty()) {
                safetyCarAtivado = true;
                safetyCar.ativarPorTempo(8000);

                if (voltaGlobal == numVoltas) {
                    System.out.println("Safety Car ativado na última volta! Adicionando uma volta extra.");
                    numVoltas++;
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        for (Carro carro : carros) {
            if (carro.isCorrendo()) {
                ordemChegada.add(carro.getNome());
            }
        }

        System.out.println("\nOrdem de chegada:");
        for (int i = 0; i < ordemChegada.size(); i++) {
            System.out.println((i + 1) + "º - " + ordemChegada.get(i));
        }
    }
}
