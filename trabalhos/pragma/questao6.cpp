/*
Programação de Alto Desempenho
#pragma omp parallel
Aula 03 - Atividades

6. Crie um programa que peça um valor ao usuário e, a partir do número informado, gere o mesmo número de threads, 
cada thread deve gerar 10 números aleatórios (entre 0 e 1000) e mostrar a saída no console informando qual o ID da thread e o número gerado:
*/

#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <time.h>

int main() {
    int num_threads, i;

    printf("Digite o número de threads: ");
    scanf("%d", &num_threads);

    // Define o número de threads
    omp_set_num_threads(num_threads);

    #pragma omp parallel
    {
        int thread_id = omp_get_thread_num();
        unsigned int seed = (unsigned int)time(NULL) + thread_id;  // Semente única por thread

        for (i = 0; i < 10; i++) {
            int numero = rand_r(&seed) % 1001;  // Número entre 0 e 1000
            printf("Thread %d - Número gerado: %d\n", thread_id, numero);
        }
    }

    return 0;
}
