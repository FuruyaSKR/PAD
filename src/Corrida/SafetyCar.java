package Corrida;

public class SafetyCar {
    private volatile boolean ativo = false;
    private final int limiteVelocidade = 120;

    public synchronized void entrarEmAcao(int duracaoEmMs) {
        if (ativo)
            return;

        ativo = true;
        System.out.println("Safety Car entrou na pista! Velocidade máxima: " + limiteVelocidade + " km/h");

        new Thread(() -> {
            try {
                Thread.sleep(duracaoEmMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            synchronized (SafetyCar.this) {
                ativo = false;
                System.out.println("Safety Car saiu da pista! Corrida normalizada.");
            }
        }).start();
    }

    public synchronized boolean isAtivo() {
        return ativo;
    }

    public int getLimiteVelocidade() {
        return limiteVelocidade;
    }
}
