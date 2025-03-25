package threads;

public class Main {
    public static void main(String[] args) {
        /*
         * 8 - Placas
         * 
         * int quantidade = 10;
         * Thread thread = new Thread(new GeradorPlacas(quantidade));
         * thread.start();
         */

        /*
         * 9 - Impar e Par
         * 
         * GeradorNumeros gerador = new GeradorNumeros();
         * Random random = new Random();
         * 
         * int quantidadePar = random.nextInt(5) + 5;
         * int quantidadeImpar = random.nextInt(5) + 10;
         * 
         * Thread threadPar = new Thread(() -> gerador.gerarPar(quantidadePar));
         * Thread threadImpar = new Thread(() -> gerador.gerarImpar(quantidadeImpar));
         * 
         * threadPar.start();
         * threadImpar.start();
         */

        ProntoSocorro prontoSocorro = new ProntoSocorro();

        Thread medico1 = new Thread(new Medico("Dr. Carlos", prontoSocorro));
        Thread medico2 = new Thread(new Medico("Dra. Ana", prontoSocorro));

        medico1.start();
        medico2.start();

        for (int i = 1; i <= 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            prontoSocorro.adicionarPaciente(new Paciente(i));
        }

        prontoSocorro.encerrarAtendimentos();

        try {
            medico1.join();
            medico2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("🏥 Pronto socorro fechou, todos os pacientes foram atendidos!");
    }
}
