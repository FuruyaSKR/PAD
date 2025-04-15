package prioridade;

public class Main {
    public static void main(String[] args) {
        /*
         * Alta Prioriade Example
         * 
         * AltaPrioridade a = new AltaPrioridade();
         * BaixaPrioridade b = new BaixaPrioridade();
         * System.out.println("Iniciando Threads...");
         * b.start();
         * a.start();
         * Thread.currentThread();
         * Thread.yield();
         * System.out.println("Main Finalizado");
         */

        int[] v = { 1, 2, 3 };
        CalcNotSync c1 = new CalcNotSync("T1", v);
        CalcNotSync c2 = new CalcNotSync("T2", v);
    }
}
