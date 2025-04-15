package com.ReplicacaoDNA;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProcessadorDNA implements Runnable {

    private final File arquivo;

    public ProcessadorDNA(File arquivo) {
        this.arquivo = arquivo;
    }

    @Override
    public void run() {
        List<String> linhasProcessadas = new ArrayList<>();
        int totalFitas = 0;
        int fitasValidas = 0;
        int fitasInvalidas = 0;
        List<String> fitasInvalidasLista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                totalFitas++;

                if (isFitaValida(linha)) {
                    fitasValidas++;
                    String fitaComplementar = gerarComplementar(linha);
                    linhasProcessadas.add(fitaComplementar);
                } else {
                    fitasInvalidas++;
                    linhasProcessadas.add("****FITA INVALIDA - " + linha);
                    fitasInvalidasLista.add("Fita #" + totalFitas + ": " + linha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + arquivo.getName());
            return;
        }

        File saida = new File(arquivo.getParentFile(), "saida_" + arquivo.getName());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saida))) {
            for (String linha : linhasProcessadas) {
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever arquivo de saída: " + saida.getName());
        }

        synchronized (System.out) {
            System.out.println("Arquivo: " + arquivo.getName());
            System.out.println("Total de fitas: " + totalFitas);
            System.out.println("Fitas válidas: " + fitasValidas);
            System.out.println("Fitas inválidas: " + fitasInvalidas);
            System.out.println("Lista de fitas inválidas:");
            for (String fita : fitasInvalidasLista) {
                System.out.println(fita);
            }
            System.out.println("---------------------------------------------------");
        }
    }

    private boolean isFitaValida(String fita) {
        return fita.matches("[ATCG]+");
    }

    private String gerarComplementar(String fita) {
        StringBuilder sb = new StringBuilder();
        for (char base : fita.toCharArray()) {
            switch (base) {
                case 'A':
                    sb.append('T');
                    break;
                case 'T':
                    sb.append('A');
                    break;
                case 'C':
                    sb.append('G');
                    break;
                case 'G':
                    sb.append('C');
                    break;
                default:
                    sb.append('?');
            }
        }
        return sb.toString();
    }
}
