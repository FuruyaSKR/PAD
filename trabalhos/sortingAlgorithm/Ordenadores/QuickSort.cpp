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

        if ((high - low) > 10000) {
            #pragma omp parallel sections
            {
                #pragma omp section
                quickSortParalelo(arr, low, pi - 1);

                #pragma omp section
                quickSortParalelo(arr, pi + 1, high);
            }
        } else {
            quickSortSerial(arr, low, pi - 1);
            quickSortSerial(arr, pi + 1, high);
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

            double tStart = omp_get_wtime();

            #pragma omp parallel
            {
                #pragma omp single
                quickSortParalelo(copia, 0, n - 1);
            }

            double tEnd = omp_get_wtime();
            double tempo = 1000.0 * (tEnd - tStart);
            printf("Threads: %d | Tempo: %.4f ms\n", numThread, tempo);
        }

        omp_set_num_threads(omp_get_max_threads());
        #pragma omp parallel
        {
            #pragma omp single
            quickSortParalelo(valores, 0, n - 1);
        }

    } else {
        double tStart = omp_get_wtime();
        quickSortSerial(valores, 0, n - 1);
        double tEnd = omp_get_wtime();

        double tempo = 1000.0 * (tEnd - tStart);
        printf("Serial | Tempo: %.4f ms\n", tempo);
    }

    return valores;
}
};
