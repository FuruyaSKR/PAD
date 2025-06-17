/*
Programação de Alto Desempenho
#pragma omp parallel
Aula 03 - Atividades

8. Altere o programa anterior permitindo mostrar a somatória de todos os números gerados:
*/

#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <time.h>

int main() {
    int num_threads, inicio, fim, i;
    int somaTotal = 0;

    printf("Digite o número de threads: ");
    scanf("%d", &num_threads);

    printf("Digite o valor inicial do intervalo: ");
    scanf("%d", &inicio);

    printf("Digite o valor final do intervalo: ");
    scanf("%d", &fim);

    // Validação básica
    if (fim <= inicio) {
        printf("Intervalo inválido! O valor final deve ser maior que o inicial.\n");
        return 1;
    }

    omp_set_num_threads(num_threads);

    #pragma omp parallel reduction(+:somaTotal)
    {
        int thread_id = omp_get_thread_num();
        unsigned int seed = (unsigned int)time(NULL) + thread_id;

        for (i = 0; i < 10; i++) {
            int numero = inicio + (rand_r(&seed) % (fim - inicio + 1));
            printf("Thread %d - Número gerado: %d\n", thread_id, numero);
            somaTotal += numero;
        }
    }

    printf("Somatória total de todos os números gerados: %d\n", somaTotal);

    return 0;
}
