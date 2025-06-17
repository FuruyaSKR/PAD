/*
Programação de Alto Desempenho
#pragma omp parallel
Aula 03 - Atividades

5. Qual o funcionamento da cláusula if e faça um pequeno exemplo com os devidos comentários no código:

Resposta:
A cláusula if no OpenMP é usada para controlar se uma região paralela (ou parte dela) será executada de forma paralela ou sequencial,
onde se a condição dentro do if for verdadeira, o bloco será executado em paralelo e se for falsa, o bloco será executado apenas por uma única thread (modo sequencial).
*/

#include <stdio.h>
#include <omp.h>

int main() {
    int tamanho = 5;

    #pragma omp parallel if(tamanho > 10)
    {
        printf("Thread %d executando\n", omp_get_thread_num());
    }

    return 0;
}
