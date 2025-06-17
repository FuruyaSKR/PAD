#include <stdio.h>
#include <omp.h>

int main() {
    int vetorSoma[] = {1, 2, 3, 4, 5};
    int vetorProduto[] = {1, 2, 3, 4, 5};
    int tamanho = 5;
    int soma = 0;
    int produto = 1;

    #pragma omp parallel sections
    {
        #pragma omp section
        {
            for (int i = 0; i < tamanho; i++) {
                soma += vetorSoma[i];
            }
            printf("Soma dos elementos: %d\n", soma);
        }

        #pragma omp section
        {
            for (int i = 0; i < tamanho; i++) {
                produto *= vetorProduto[i];
            }
            printf("Produto dos elementos: %d\n", produto);
        }
    }

    return 0;
}
