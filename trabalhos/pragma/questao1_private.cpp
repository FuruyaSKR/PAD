/*
Programação de Alto Desempenho
#pragma omp parallel
Aula 03 - Atividades

1. Qual o funcionamento da cláusula private e faça um pequeno exemplo com os devidos comentários no código:

Resposta:
A cláusula private no OpenMP especifica que cada thread terá sua própria cópia de uma variável, não compartilhando o valor com outras threads, 
onde cada thread inicia a variável sem valor definido (garbage) dentro da região paralela, a menos que um valor inicial seja explicitamente atribuído dentro da região.
*/

#include <stdio.h>
#include <omp.h>

int main() {
    int i;
    int x = 10;

    #pragma omp parallel private(x)
    {
        x = omp_get_thread_num();  // Cada thread tem sua própria cópia de x
        printf("Thread %d: x = %d\n", omp_get_thread_num(), x);
    }

    return 0;
}
