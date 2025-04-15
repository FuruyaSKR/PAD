package atividade04;

class ThreadLigar extends Thread {
    private final Caldeira caldeira;

    public ThreadLigar(Caldeira caldeira) {
        this.caldeira = caldeira;
        setPriority(MIN_PRIORITY);
    }

    public void run() {
        while (caldeira.emFuncionamento()) {
            caldeira.ligar();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}