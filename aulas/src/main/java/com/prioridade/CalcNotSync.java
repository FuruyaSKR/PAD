package com.prioridade;

public class CalcNotSync implements Runnable {

    private int[] vet;
    private String nome;
    private static CalcNotSync_Calc calc = new CalcNotSync_Calc();

    public CalcNotSync(
            String nome,
            int[] vet) {
        this.nome = nome;
        this.vet = vet;
        new Thread(this, nome).start();
    }

    public void run() {
        System.out.println("Inicio de " + nome);
        int soma = calc.SomaVet(vet);
        System.out.println("Fim de " + nome + "- soma = " + soma);
    }
}
