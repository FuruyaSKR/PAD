/*
Programação de Alto Desempenho
#pragma omp parallel
Aula 03 - Atividades

3. Qual o funcionamento da cláusula firstprivate e faça um pequeno exemplo com os devidos comentários no código:

Resposta:
A cláusula firstprivate no OpenMP é uma combinação entre private e a inicialização com o valor da variável antes da região paralela,
onde cada thread recebe sua própria cópia da variável (como no private), mas diferente do private, cada cópia é inicializada com o valor atual da variável antes da região paralela começar.
*/

#include <stdio.h>
#include <omp.h>

int main() {
    int x = 5;

    #pragma omp parallel firstprivate(x)
    {
        x += omp_get_thread_num();  // Cada thread modifica sua própria cópia de x
        printf("Thread %d: x = %d\n", omp_get_thread_num(), x);
    }

    return 0;
}
