package atividade04;

class ThreadDesligar extends Thread {
    private final Caldeira caldeira;

    public ThreadDesligar(Caldeira caldeira) {
        this.caldeira = caldeira;
        setPriority(MAX_PRIORITY);
    }

    public void run() {
        while (caldeira.emFuncionamento()) {
            if (caldeira.getTemperatura() > 1600) {
                caldeira.desligar();
                caldeira.pararSistema();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}