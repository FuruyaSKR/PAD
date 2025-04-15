package atividade04;

public class Main {
    public static void main(String[] args) {
        Caldeira caldeira = new Caldeira();

        Thread ligar = new ThreadLigar(caldeira);
        Thread alimentar = new ThreadAlimentar(caldeira);
        Thread desligar = new ThreadDesligar(caldeira);
        Thread limpar = new ThreadLimpar(caldeira);

        ligar.start();
        alimentar.start();
        desligar.start();
        limpar.start();

        try {
            desligar.join();
            ligar.join();
            alimentar.join();
            limpar.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Sistema encerrado com segurança.");
    }
}