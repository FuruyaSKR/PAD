package Corrida;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Corrida {
    private static final int TOTAL_VOLTA = 5;
    private static final int NUM_CARROS = 5;

    public static void main(String[] args) {
        SafetyCar safetyCar = new SafetyCar();
        Box box = new Box();
        List<Carro> carros = new ArrayList<>();

        // Criando os carros
        for (int i = 1; i <= NUM_CARROS; i++) {
            carros.add(new Carro("Carro #" + i, box, safetyCar));
        }

        System.out.println("🏁 Corrida iniciada com " + NUM_CARROS + " carros!");

        // Loop de voltas
        for (int volta = 1; volta <= TOTAL_VOLTA; volta++) {
            System.out.println("\n🔄 Iniciando volta " + volta);

            // Safety Car entra na volta 3
            if (volta == 3) {
                safetyCar.entrarEmAcao(7000); // Ativo por 7 segundos
            }

            // Cada carro realiza sua ação da volta (sincronizado por volta)
            for (Carro c : carros) {
                if (c.isAtivo()) {
                    c.correr();
                }
            }

            // Tempo entre voltas (só para espaçar prints)
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Finaliza os carros restantes
        for (Carro c : carros) {
            if (!c.isAbandonou()) {
                c.finalizar();
            }
        }

        System.out.println("\n🏁 Corrida encerrada! Resultados:\n");

        // Mostrar carros que completaram
        carros.stream()
                .filter(Carro::isFinalizou)
                .sorted(Comparator.comparingDouble(Carro::getVelocidadeAtual).reversed())
                .forEach(c -> {
                    System.out.println("✅ " + c.getNome() + " terminou a corrida."
                            + (c.isPenalizado() ? " (Com penalidade)" : ""));
                });

        // Mostrar abandonos
        carros.stream()
                .filter(Carro::isAbandonou)
                .forEach(c -> System.out.println("❌ " + c.getNome() + " abandonou a corrida."));

        System.out.println("\n🏆 Fim da simulação!");
    }
}
