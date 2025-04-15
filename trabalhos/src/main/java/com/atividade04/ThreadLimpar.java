package com.atividade04;

class ThreadLimpar extends Thread {
    private final Caldeira caldeira;

    public ThreadLimpar(Caldeira caldeira) {
        this.caldeira = caldeira;
        setPriority(MIN_PRIORITY);
    }

    public void run() {
        while (caldeira.emFuncionamento()) {
            caldeira.limpar();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}