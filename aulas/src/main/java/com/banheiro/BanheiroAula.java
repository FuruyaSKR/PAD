package com.banheiro;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class BanheiroAula {
    private Lock lock = new ReentrantLock();

    public void fazNumero1() {
        String nome = Thread.currentThread().getName();
        lock.lock();
        System.out.println(nome + " entrando no banheiro");
        System.out.println(nome + " entrando no banheiro");

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(nome + " terminou atividade rapida");
        System.out.println(nome + " dando descarga");
        System.out.println(nome + " lavando a mao");
        System.out.println(nome + " saindo do banheiro");
        lock.unlock();
    }

    public void fazNumero2() {
        String nome = Thread.currentThread().getName();
        lock.lock();
        System.out.println(nome + " entrando no banheiro");
        System.out.println(nome + " entrando no banheiro");

        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(nome + " terminou atividade rapida");
        System.out.println(nome + " dando descarga");
        System.out.println(nome + " lavando a mao");
        System.out.println(nome + " saindo do banheiro");
        lock.unlock();
    }
}
