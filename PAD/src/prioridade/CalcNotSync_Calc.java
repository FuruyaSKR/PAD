public class CalcNotSync_Calc {
    private int soma;

    // public int SomaVet(int[] vet) { Metodo sem o synchronized
    public synchronized int SomaVet(int[] vet) {
        soma = 0;
        for (int x = 0; x < vet.length; x++) {
            soma += vet[x];
            System.out.println(Thread.currentThread().getName() + "- Somando" + vet[x] + "- Total = " + soma);
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return soma;
    }

}
