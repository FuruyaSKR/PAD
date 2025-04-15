package com.aulas.threads;

import java.util.Random;

public class Medico implements Runnable {
    private final String nome;
    private final ProntoSocorro prontoSocorro;
    private final Random random = new Random();

    public Medico(String nome, ProntoSocorro prontoSocorro) {
        this.nome = nome;
        this.prontoSocorro = prontoSocorro;
    }

    @Override
    public void run() {
        while (true) {
            Paciente paciente = prontoSocorro.atenderPaciente();
            if (paciente == null)
                break;

            int tempoAtendimento = random.nextInt(3000) + 2000;
            System.out.println("🩺 " + nome + " está atendendo o Paciente " + paciente.getId());

            try {
                Thread.sleep(tempoAtendimento);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            System.out.println("✅ " + nome + " finalizou o atendimento do Paciente " + paciente.getId() + " em "
                    + (tempoAtendimento / 1000) + " segundos.");
        }
        System.out.println("🏥 " + nome + " finalizou todos os atendimentos e está indo embora.");
    }
}
