#include <stdio.h>
#include <omp.h>

int main() {
    int vetor[] = {3, 7, 2, 9, 5, 8, 4, 10, 6, 1};
    int tamanho = 10;
    int valorProcurado = 5;
    int posicao = -1;

    #pragma omp parallel
    {
        #pragma omp for
        for (int i = 0; i < tamanho; i++) {
            if (vetor[i] == valorProcurado) {
                #pragma omp critical
                {
                    if (posicao == -1) {
                        posicao = i;
                    }
                }
            }
        }

        #pragma omp single
        {
            if (posicao != -1) {
                printf("Valor encontrado na posição: %d\n", posicao);
            } else {
                printf("Valor não encontrado no vetor.\n");
            }
        }
    }

    return 0;
}
