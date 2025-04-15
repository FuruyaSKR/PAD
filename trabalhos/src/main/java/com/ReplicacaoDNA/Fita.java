package com.ReplicacaoDNA;

public class Fita {
    private String fitaOriginal;
    private char complementar[];

    public Fita(String fitaOriginal) {
        this.fitaOriginal = fitaOriginal;
    }

    public String gerarFitaComplementar() {
        char[] complementar = new char[fitaOriginal.length()];

        for (int i = 0; i < fitaOriginal.length(); i++) {

            char base = fitaOriginal.charAt(i);

            switch (base) {
                case 'A':
                    complementar[i] = 'T';
                    break;
                case 'T':
                    complementar[i] = 'A';
                    break;
                case 'G':
                    complementar[i] = 'C';
                    break;
                case 'C':
                    complementar[i] = 'G';
                    break;
                default:
                    return null;
            }
        }
        return new String(complementar);
    }
}
