package com.arquivosNomes;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class BuscaNomesMain {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o termo de busca: ");
        String termoParaBuscar = scanner.nextLine();

        ClassLoader classLoader = BuscaNomesMain.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("arquivosNomes");

        if (resourceUrl == null) {
            System.out.println("Pasta de arquivos não encontrada.");
            scanner.close();
            return;
        }

        File pasta = new File(resourceUrl.getFile());
        File[] arquivos = pasta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo de nomes encontrado.");
            scanner.close();
            return;
        }

        // === TESTE SEQUENCIAL ===
        long inicioSequencial = System.nanoTime();
        Semaphore semaforoSequencial = new Semaphore(1);

        for (File arquivo : arquivos) {
            Thread thread = new Thread(new BuscaArquivo(arquivo, termoParaBuscar, semaforoSequencial));
            thread.run();
        }

        long fimSequencial = System.nanoTime();

        // === TESTE MULTITHREAD ===
        long inicioParalelo = System.nanoTime();
        Semaphore semaforoParalelo = new Semaphore(2);

        List<Thread> threads = new ArrayList<>();

        for (File arquivo : arquivos) {
            Thread thread = new Thread(new BuscaArquivo(arquivo, termoParaBuscar, semaforoParalelo));
            thread.start();
            threads.add(thread);
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Erro ao aguardar thread: " + e.getMessage());
            }
        }
        long fimParalelo = System.nanoTime();

        System.out.printf("Tempo de execução (sequencial): %.2f ms%n",
                (fimSequencial - inicioSequencial) / 1_000_000.0);
        System.out.printf("Tempo de execução (paralelo com 2 threads): %.2f ms%n",
                (fimParalelo - inicioParalelo) / 1_000_000.0);

        scanner.close();
    }
}
