package atividade04;

class ThreadAlimentar extends Thread {
    private final Caldeira caldeira;

    public ThreadAlimentar(Caldeira caldeira) {
        this.caldeira = caldeira;
        setPriority(MIN_PRIORITY);
    }

    public void run() {
        while (caldeira.emFuncionamento()) {
            caldeira.alimentar();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                interrupt();
            }
        }
    }
}