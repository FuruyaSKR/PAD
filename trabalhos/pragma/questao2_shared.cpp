/*
Programação de Alto Desempenho
#pragma omp parallel
Aula 03 - Atividades

2. Qual o funcionamento da cláusula shared e faça um pequeno exemplo com os devidos comentários no código:

Resposta:
A cláusula shared no OpenMP indica que uma variável é compartilhada entre todas as threads dentro de uma região paralela,
onde todas as threads acessam e modificam a mesma instância da variável, o que pode levar a condições de corrida se não houver sincronização adequada.
*/

#include <stdio.h>
#include <omp.h>

int main() {
    int i;
    int soma = 0;

    #pragma omp parallel shared(soma)
    {
        #pragma omp critical
        {
            soma += 1;  // Todas as threads modificam a mesma variável 'soma'
            printf("Thread %d: soma parcial = %d\n", omp_get_thread_num(), soma);
        }
    }

    printf("Soma final: %d\n", soma);

    return 0;
}
