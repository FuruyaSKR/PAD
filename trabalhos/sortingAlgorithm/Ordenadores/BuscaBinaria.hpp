#ifndef BUSCABINARIA_HPP
#define BUSCABINARIA_HPP

#include <vector>
#include <omp.h>
#include <cstdio>

using namespace std;

int buscaBinariaSerial(const vector<int>& arr, int x)
{
    int left = 0, right = arr.size() - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;

        if (arr[mid] == x)
            return mid;

        if (arr[mid] < x)
            left = mid + 1;
        else
            right = mid - 1;
    }
    return -1;
}

int buscaBinariaParalela(const vector<int>& arr, int x, int numThreads)
{
    int left = 0, right = arr.size() - 1;
    int result = -1;

    #pragma omp parallel num_threads(numThreads)
    {
        int tid = omp_get_thread_num();
        int localLeft = left + tid * (arr.size() / numThreads);
        int localRight = (tid == numThreads - 1) ? right : localLeft + (arr.size() / numThreads) - 1;

        while (localLeft <= localRight && result == -1) {
            int mid = localLeft + (localRight - localLeft) / 2;

            if (arr[mid] == x) {
                #pragma omp critical
                result = mid;
            }
            else if (arr[mid] < x)
                localLeft = mid + 1;
            else
                localRight = mid - 1;
        }
    }

    return result;
}

void executarBuscaBinaria(const vector<int>& arr, int valorBusca, bool paralelo)
{
    if (paralelo) {
        const int numThreads[] = {1, 2, 4, 8, 12};
        for (int i = 0; i < 5; i++) {
            int numThread = numThreads[i];
            omp_set_num_threads(numThread);

            double tStart = omp_get_wtime();
            int encontrado = buscaBinariaParalela(arr, valorBusca, numThread);
            double tEnd = omp_get_wtime();

            double tempo = 1000.0 * (tEnd - tStart);
            if (encontrado != -1)
                printf("Threads: %d | Encontrado em posicao %d | Tempo: %.4f ms\n", numThread, encontrado, tempo);
            else
                printf("Threads: %d | Valor nao encontrado | Tempo: %.4f ms\n", numThread, tempo);
        }
    } else {
        double tStart = omp_get_wtime();
        int encontrado = buscaBinariaSerial(arr, valorBusca);
        double tEnd = omp_get_wtime();

        double tempo = 1000.0 * (tEnd - tStart);
        if (encontrado != -1)
            printf("Serial | Encontrado em posicao %d | Tempo: %.4f ms\n", encontrado, tempo);
        else
            printf("Serial | Valor nao encontrado | Tempo: %.4f ms\n", tempo);
    }
}

#endif
