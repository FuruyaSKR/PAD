package Corrida;

public class SafetyCar {
    private volatile boolean ativo = false;
    private final int limiteVelocidade = 120;

    public synchronized void ativarPorTempo(int tempoMs) {
        if (ativo)
            return;

        ativo = true;
        System.out.println("Safety Car entrou na pista! Velocidade limitada a " + limiteVelocidade + " km/h");

        new Thread(() -> {
            try {
                Thread.sleep(tempoMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            desativar();
        }).start();
    }

    public synchronized void desativar() {
        if (ativo) {
            ativo = false;
            System.out.println("Safety Car saiu da pista! Corrida normalizada.");
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getLimiteVelocidade() {
        return limiteVelocidade;
    }
}
