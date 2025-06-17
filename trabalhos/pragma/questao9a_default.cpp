/*
Programação de Alto Desempenho
#pragma omp parallel
Aula 03 - Atividades

Questão 9: Pesquise sobre os dois outros tipos de cláusulas que podem ser usadas com o construtor #pragma omp parallel.

Resposta: A cláusula default define o comportamento padrão das variáveis dentro da região paralela quanto ao compartilhamento,
onde o default(shared) deixa todas as variáveis são compartilhadas por padrão.
*/

#include <stdio.h>
#include <omp.h>

int main() {
    int x = 5;

    #pragma omp parallel default(shared)
    {
        printf("Thread %d: x = %d\n", omp_get_thread_num(), x);
    }

    return 0;
}