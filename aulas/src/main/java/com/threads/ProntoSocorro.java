package com.aulas.threads;

import java.util.LinkedList;
import java.util.Queue;

public class ProntoSocorro {
    private final Queue<Paciente> fila = new LinkedList<>();
    private boolean atendimentosEncerrados = false;

    public synchronized void adicionarPaciente(Paciente paciente) {
        fila.add(paciente);
        System.out.println("Paciente " + paciente.getId() + " chegou ao pronto socorro.");
        notify();
    }

    public synchronized Paciente atenderPaciente() {
        while (fila.isEmpty()) {
            if (atendimentosEncerrados) {
                notifyAll();
                return null;
            }
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return fila.poll();
    }

    public synchronized void encerrarAtendimentos() {
        atendimentosEncerrados = true;
        notifyAll();
    }
}
