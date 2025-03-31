package Corrida;

import java.util.Random;

public class Carro {
    private final String nome;
    private final int velocidadeBase;
    private int velocidadeAtual;
    private boolean correndo;
    private boolean abandonou;
    private boolean penalizado;

    private final SafetyCar safetyCar;

    private final Random random = new Random();

    public Carro(String nome, SafetyCar safetyCar) {
        this.nome = nome;
        this.safetyCar = safetyCar;
        this.velocidadeBase = random.nextInt(101) + 100;
        this.correndo = true;
        this.abandonou = false;
        this.penalizado = false;
    }

    public void executarVolta() {
        int chance = random.nextInt(100) + 1;
        if (!correndo || abandonou)
            return;

        correr();

        if (chance <= 5) {
            pararPorProblema();
        } else if (chance <= 20) {
            entrarNoBox();
        } else {
            correr();
        }
        chance = 0;
        esperar(1000 + random.nextInt(1000));
    }

    private void correr() {
        int variacao = random.nextInt(21) - 10;
        velocidadeAtual = velocidadeBase + variacao;

        if (safetyCar != null && safetyCar.isAtivo()) {
            velocidadeAtual = Math.min(velocidadeAtual, safetyCar.getLimiteVelocidade());
            System.out.println(nome + " sob Safety Car a " + velocidadeAtual + " km/h");
        } else {
            System.out.println(nome + " a " + velocidadeAtual + " km/h");
        }
    }

    private void entrarNoBox() {
        Box box = new Box(this);
        Thread boxThread = new Thread(box);
        boxThread.start();

        try {
            boxThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void pararPorProblema() {
        abandonou = true;
        correndo = false;
        System.out.println(nome + " abandonou a corrida por falha técnica");
    }

    private void esperar(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public String getNome() {
        return nome;
    }

    public int getVelocidadeAtual() {
        return velocidadeAtual;
    }

    public boolean isCorrendo() {
        return correndo && !abandonou;
    }

    public boolean isAbandonou() {
        return abandonou;
    }

    public int getVelocidadeBase() {
        return this.velocidadeBase;
    }

    public void setVelocidadeAtual(int velocidadeAtual) {
        this.velocidadeAtual = velocidadeAtual;
    }

    public boolean getCorrendo() {
        return this.correndo;
    }

    public void setCorrendo(boolean correndo) {
        this.correndo = correndo;
    }

    public boolean getAbandonou() {
        return this.abandonou;
    }

    public void setAbandonou(boolean abandonou) {
        this.abandonou = abandonou;
    }

    public boolean getPenalizado() {
        return this.penalizado;
    }

    public void setPenalizado(boolean penalizado) {
        this.penalizado = penalizado;
    }

    public SafetyCar getSafetyCar() {
        return this.safetyCar;
    }

    public Random getRandom() {
        return this.random;
    }

}
