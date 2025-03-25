public class BaixaPrioridade extends Thread {
    public void run() {
        setPriority(1);
        for (int x = 0; x < 10; x++) {
            System.out.println("Baixa Prioridade...");
        }
        try {
            sleep(100);
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
