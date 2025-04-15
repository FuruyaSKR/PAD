package com.ReplicacaoDNA;

import java.io.*;
import java.net.URL;
import java.util.Random;

public class InjetarFitasInvalidas {

    private static final char[] INVALIDOS = { 'X', 'Y', 'Z', '!', '@', '#', '%', '&' };

    public static void main(String[] args) {
        ClassLoader classLoader = InjetarFitasInvalidas.class.getClassLoader();
        URL resourceUrl = classLoader.getResource("arquivosDNA");

        if (resourceUrl == null) {
            System.out.println("Pasta de arquivos não encontrada.");
            return;
        }

        File pasta = new File(resourceUrl.getFile());
        File[] arquivos = pasta.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo encontrado.");
            return;
        }

        Random random = new Random();

        for (File arquivo : arquivos) {
            try {
                File temp = new File(arquivo.getParent(), "mod_" + arquivo.getName());
                BufferedReader reader = new BufferedReader(new FileReader(arquivo));
                BufferedWriter writer = new BufferedWriter(new FileWriter(temp));

                String linha;
                while ((linha = reader.readLine()) != null) {
                    if (random.nextInt(100) == 0) {
                        int posicao = random.nextInt(linha.length() + 1);
                        char invalido = INVALIDOS[random.nextInt(INVALIDOS.length)];
                        linha = linha.substring(0, posicao) + invalido + linha.substring(posicao);
                    }
                    writer.write(linha);
                    writer.newLine();
                }

                reader.close();
                writer.close();

                System.out.println("Arquivo modificado salvo como: " + temp.getName());

            } catch (IOException e) {
                System.out.println("Erro ao processar arquivo: " + arquivo.getName());
            }
        }
    }
}
