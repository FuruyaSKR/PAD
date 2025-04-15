package com.arquivosNomes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Semaphore;
import org.apache.commons.text.similarity.LevenshteinDistance;

public class BuscaArquivo implements Runnable {

    private final File arquivo;
    private final String termoBusca;
    private final Semaphore semaforo;

    public BuscaArquivo(File arquivo, String termoBusca, Semaphore semaforo) {
        this.arquivo = arquivo;
        this.termoBusca = termoBusca.toLowerCase();
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        try {
            semaforo.acquire();
            BufferedReader reader = new BufferedReader(new FileReader(arquivo, StandardCharsets.UTF_8));
            LevenshteinDistance ld = new LevenshteinDistance(2);
            String linha;
            int numeroLinha = 1;

            while ((linha = reader.readLine()) != null) {
                String[] palavras = linha.toLowerCase().split("\\s+");

                for (String palavra : palavras) {
                    int tamanhoDiferenca = Math.abs(palavra.length() - termoBusca.length());
                    if (tamanhoDiferenca > 2)
                        continue;

                    if (!palavra.startsWith(termoBusca.substring(0, 1)))
                        continue;

                    if (!letrasComunsMinimas(palavra, termoBusca, 2))
                        continue;

                    Integer distancia = ld.apply(palavra, termoBusca);
                    if (distancia != null && distancia <= 2) {
                        System.out.printf("Arquivo: %s | Linha: %d | Nome: %s%n",
                                arquivo.getName(), numeroLinha, linha);
                        break;
                    }
                }
                numeroLinha++;
            }

            reader.close();
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo " + arquivo.getName() + ": " + e.getMessage());
        } finally {
            semaforo.release();
        }
    }

    private boolean letrasComunsMinimas(String palavra, String termo, int minimo) {
        int contador = 0;
        for (char c : termo.toCharArray()) {
            if (palavra.indexOf(c) >= 0) {
                contador++;
            }
        }
        return contador >= minimo;
    }

}