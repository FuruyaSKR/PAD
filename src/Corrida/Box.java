package Corrida;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Box implements Runnable {

    private final int LIMITE_VELOCIDADE = 60;
    private final BlockingQueue<Carro> filaCarros = new LinkedBlockingQueue<>();

    private final List<String> carrosEntraram = Collections.synchronizedList(new ArrayList<>());
    private final List<String> carrosPenalizados = Collections.synchronizedList(new ArrayList<>());

    private volatile boolean ativo = true;

    public void solicitarEntrada(Carro carro) {
        try {
            filaCarros.put(carro);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getLimiteVelocidade() {
        return LIMITE_VELOCIDADE;
    }

    public void encerrar() {
        ativo = false;
    }

    @Override
    public void run() {
        while (ativo || !filaCarros.isEmpty()) {
            try {
                Carro carro = filaCarros.take();
                processarCarro(carro);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Box terminou o atendimento.");
    }

    private void processarCarro(Carro carro) {
        System.out.println(carro.getNome() + " entrou no box.");

        carrosEntraram.add(carro.getNome());

        if (carro.getVelocidadeAtual() > LIMITE_VELOCIDADE) {
            carrosPenalizados.add(carro.getNome());
            System.out.println(carro.getNome() + " penalizado por excesso de velocidade: "
                    + String.format("%.1f", carro.getVelocidadeAtual()) + " km/h");
            dormir(2000);
        }

        dormir(1800);

        System.out.println(carro.getNome() + " saiu do box.");
    }

    private void dormir(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public List<String> getCarrosEntraram() {
        return carrosEntraram;
    }

    public List<String> getCarrosPenalizados() {
        return carrosPenalizados;
    }
}
