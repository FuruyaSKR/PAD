/*
Programação de Alto Desempenho
#pragma omp parallel
Aula 03 - Atividades

Questão 9: Pesquise sobre os dois outros tipos de cláusulas que podem ser usadas com o construtor #pragma omp parallel.

Resposta: A cláusula copyin permite que variáveis threadprivate sejam inicializadas nas threads com o mesmo valor que possuem na thread principal,
onde todas as threads recebem uma cópia inicial da variável baseada no valor da thread master (thread principal).
*/

#include <stdio.h>
#include <omp.h>

int x = 10;
#pragma omp threadprivate(x)

int main() {
    x = 20;  // Valor da variável na thread principal

    #pragma omp parallel copyin(x)
    {
        printf("Thread %d: x = %d\n", omp_get_thread_num(), x);
    }

    return 0;
}
