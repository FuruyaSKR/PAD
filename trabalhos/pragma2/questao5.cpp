#include <stdio.h>
#include <omp.h>

int main() {
    int vetor[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    int tamanho = 10;
    int soma = 0;

    #pragma omp parallel for reduction(+:soma)
    for (int i = 0; i < tamanho; i++) {
        soma += vetor[i];
    }

    printf("Soma total: %d\n", soma);

    return 0;
}
