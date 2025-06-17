#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

#define LINHAS 5
#define COLUNAS 5

int main() {
    int matriz[LINHAS][COLUNAS];

    #pragma omp parallel
    {
        #pragma omp single
        {
            srand(time(NULL));
            printf("Gerador de números aleatórios inicializado por uma única thread.\n");
        }

        #pragma omp for collapse(2)
        for (int i = 0; i < LINHAS; i++) {
            for (int j = 0; j < COLUNAS; j++) {
                matriz[i][j] = rand() % 100;
            }
        }
    }

    printf("Matriz gerada:\n");
    for (int i = 0; i < LINHAS; i++) {
        for (int j = 0; j < COLUNAS; j++) {
            printf("%d ", matriz[i][j]);
        }
        printf("\n");
    }

    return 0;
}
