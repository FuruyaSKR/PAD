package main.java.com.aulas.prioridade;

public class AltaPrioridade extends Thread {
    public void run() {
        // setPriority(10);
        setPriority(Thread.MAX_PRIORITY); // Same Value
        for (int y = 0; y < 10; y++) {
            System.out.println("Alta Prioridade...");
        }
        try {
            sleep(100);
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
