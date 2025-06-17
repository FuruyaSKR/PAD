/*
Programação de Alto Desempenho
#pragma omp parallel
Aula 03 - Atividades

4. Qual o funcionamento da cláusula reduction e faça um pequeno exemplo com os devidos comentários no código:

Resposta:
A cláusula reduction no OpenMP é usada para realizar operações de redução (como soma, multiplicação, máximo, mínimo, etc.) de forma segura entre múltiplas threads,
onde cada thread trabalha com uma cópia local da variável de redução, e ao final da região paralela, o OpenMP combina os resultados de todas as threads em uma única 
variável global, usando o operador especificado (ex: soma, produto).
*/

#include <stdio.h>
#include <omp.h>

int main() {
    int i;
    int soma = 0;

    #pragma omp parallel for reduction(+:soma)
    for (i = 1; i <= 10; i++) {
        soma += i;  // Cada thread acumula localmente e o OpenMP soma no final
    }

    printf("Soma final de 1 a 10: %d\n", soma);

    return 0;
}
