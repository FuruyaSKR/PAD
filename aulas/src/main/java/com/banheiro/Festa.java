package com.banheiro;

public class Festa {
    public static void main(String[] args) {
        Banheiro banheiro = new Banheiro();

        Runnable pessoa1 = () -> {
            banheiro.usarBanheiro("João", true);
        };

        Runnable pessoa2 = () -> {
            banheiro.usarBanheiro("Maria", false);
        };

        Runnable pessoa3 = () -> {
            banheiro.usarBanheiro("Carlos", false);
        };

        new Thread(pessoa1).start();
        new Thread(pessoa2).start();
        new Thread(pessoa3).start();
    }
}
