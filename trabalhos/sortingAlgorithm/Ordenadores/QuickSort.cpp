#include <iostream>
#include <vector>
#include <ctime>
#include <omp.h>
#include "Ordenador.hpp"

using namespace std;

class QuickSort : public Ordenador
{
private:
    bool paralelo;

    int partition(vector<int>& arr, int low, int high)
    {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr[i], arr[j]);
            }
        }
        swap(arr[i + 1], arr[high]);
        return i + 1;
    }

    void quickSortSerial(vector<int>& arr, int low, int high)
    {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortSerial(arr, low, pi - 1);
            quickSortSerial(arr, pi + 1, high);
        }
    }

    void quickSortParalelo(vector<int>& arr, int low, int high)
    {
        if (low < high) {
            int pi = partition(arr, low, high);

            #pragma omp parallel sections
            {
                #pragma omp section
                quickSortParalelo(arr, low, pi - 1);

                #pragma omp section
                quickSortParalelo(arr, pi + 1, high);
            }
        }
    }

public:
    QuickSort(bool paralelo = false)
    {
        this->paralelo = paralelo;
    }

    vector<int> ordenador(vector<int> valores) override
    {
        int n = valores.size();

        if (paralelo) {
            const int numThreads[] = {1, 2, 4, 8, 12};
            for (int i = 0; i < 5; i++) {
                vector<int> copia = valores;
                int numThread = numThreads[i];
                omp_set_num_threads(numThread);

                clock_t start = clock();
                quickSortParalelo(copia, 0, n - 1);
                clock_t end = clock();

                double tempo = 1000.0 * (end - start) / CLOCKS_PER_SEC;
                printf("Teste com %d Threads: %.2f ms\n", numThread, tempo);
            }
        } else {
            clock_t start = clock();
            quickSortSerial(valores, 0, n - 1);
            clock_t end = clock();

            double tempo = 1000.0 * (end - start) / CLOCKS_PER_SEC;
            printf("Tempo Serial: %.2f ms\n", tempo);
        }

        return valores;
    }
};
