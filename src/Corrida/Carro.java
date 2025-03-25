package Corrida;

import java.util.Random;

public class Carro {
    private final String nome;
    private final Random random = new Random();

    private double velocidadeAtual; // km/h
    private boolean abandonou = false;
    private boolean penalizado = false;
    private boolean finalizou = false;

    private final Box box;
    private final SafetyCar safetyCar;

    public Carro(String nome, Box box, SafetyCar safetyCar) {
        this.nome = nome;
        this.box = box;
        this.safetyCar = safetyCar;
    }

    public void correr() {
        if (abandonou || finalizou)
            return;

        velocidadeAtual = gerarVelocidade();

        if (random.nextInt(100) < 5) {
            abandonou = true;
            System.out.println(nome + " abandonou a corrida por falha técnica.");
            return;
        }

        System.out.println(nome + " está correndo a " + String.format("%.1f", velocidadeAtual) + " km/h");

        simularTempoDeCorrida();

        // Checa se deve entrar no box
        if (box.deveEntrarNoBox(this)) {
            entrarNoBox();
        }
    }

    public void finalizar() {
        finalizou = true;
    }

    public boolean isAtivo() {
        return !abandonou && !finalizou;
    }

    private void entrarNoBox() {
        System.out.println(nome + " entrando no box...");

        if (velocidadeAtual > box.getLimiteVelocidade()) {
            penalizado = true;
            System.out.println(nome + " penalizado por excesso de velocidade no box! Velocidade: "
                    + String.format("%.1f", velocidadeAtual) + " km/h");
            dormir(2000);
        }

        dormir(1500);

        System.out.println(nome + " saiu do box.");
    }

    private void simularTempoDeCorrida() {
        dormir(random.nextInt(1000) + 500);
    }

    private double gerarVelocidade() {
        double base = 200 + random.nextDouble() * 100;
        if (safetyCar != null && safetyCar.isAtivo()) {
            return Math.min(base, safetyCar.getLimiteVelocidade());
        }
        return base;
    }

    private void dormir(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Getters

    public String getNome() {
        return nome;
    }

    public boolean isPenalizado() {
        return penalizado;
    }

    public boolean isAbandonou() {
        return abandonou;
    }

    public boolean isFinalizou() {
        return finalizou;
    }

    public double getVelocidadeAtual() {
        return velocidadeAtual;
    }
}
