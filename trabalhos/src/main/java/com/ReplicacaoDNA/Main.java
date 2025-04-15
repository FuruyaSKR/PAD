package com.ReplicacaoDNA;

import java.io.File;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o número de threads a serem utilizadas: ");
        int numThreads = scanner.nextInt();
        scanner.close();

        long inicio = System.nanoTime();

        ClassLoader classLoader = Main.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("arquivosDNA");

        if (resourceUrl == null) {
            System.out.println("Pasta de arquivos não encontrada.");
            return;
        }

        File pasta = new File(resourceUrl.getFile());
        File[] arquivos = pasta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo de DNA encontrado.");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (File arquivo : arquivos) {
            Runnable task = new ProcessadorDNA(arquivo);
            executor.submit(task);
        }

        executor.shutdown();

        try {
            if (executor.awaitTermination(10, TimeUnit.MINUTES)) {
                long fim = System.nanoTime();
                double tempoTotalSegundos = (fim - inicio) / 1_000_000_000.0;
                System.out.printf("Tempo total de execução: %.3f segundos%n", tempoTotalSegundos);
            } else {
                System.out.println("Tempo limite excedido para execução das threads.");
            }
        } catch (InterruptedException e) {
            System.out.println("Execução foi interrompida.");
        }
    }
}
