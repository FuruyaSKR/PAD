package Corrida;

public class Box implements Runnable {
    private final Carro carro;

    public Box(Carro carro) {
        this.carro = carro;
    }

    @Override
    public void run() {
        int velocidade = carro.getVelocidadeAtual();
        System.out.println(carro.getNome() + " Reduzindo a velocidade para o box: " + velocidade + " km/h.");

        while (velocidade > 60) {
            velocidade -= 10 + carro.getRandom().nextInt(4);
            if (velocidade < 60)

                carro.setVelocidadeAtual(velocidade);
            pausa(500);
        }

        System.out.println(carro.getNome() + " entrou no box com velocidade de " + velocidade + " km/h.");

        if (velocidade > 60) {
            penalidade();
        }

        pausa(2000);
        System.out.println(carro.getNome() + " saiu do box.");
    }

    private void penalidade() {
        carro.setPenalizado(true);
        System.out.println(carro.getNome() + " foi penalizado por entrar acima de 60 km/h!");
    }

    private void pausa(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
