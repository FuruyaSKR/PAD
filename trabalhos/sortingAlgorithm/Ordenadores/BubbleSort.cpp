#include <iostream>
#include <vector>
#include <omp.h>
#include "Ordenador.hpp"

using namespace std;

class BubbleSort : public Ordenador
{
private:
    bool paralelo;

void bubbleSortSerial(vector<int>& arr)
{
    int n = arr.size();
    bool isSorted;

    for (int i = 0; i < n - 1; i++) {
        isSorted = true;
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(arr[j], arr[j + 1]);
                isSorted = false;
            }
        }
        if (isSorted) break; 
    }
}
   void bubbleSortParalelo(vector<int>& arr)
{
    int n = arr.size();
    bool isSorted = false;

    while (!isSorted) {
        isSorted = true;

        #pragma omp parallel for
        for (int i = 1; i < n - 1; i += 2) {
            if (arr[i] > arr[i + 1]) {
                swap(arr[i], arr[i + 1]);
                isSorted = false;
            }
        }

        #pragma omp parallel for
        for (int i = 0; i < n - 1; i += 2) {
            if (arr[i] > arr[i + 1]) {
                swap(arr[i], arr[i + 1]);
                isSorted = false;
            }
        }
    }
}

public:
    BubbleSort(bool paralelo = false)
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
                bubbleSortParalelo(copia);
                double tEnd = omp_get_wtime();

                double tempo = 1000.0 * (tEnd - tStart);
                printf("Threads: %d | Tempo: %.4f ms\n", numThread, tempo);
            }

            omp_set_num_threads(omp_get_max_threads());
            bubbleSortParalelo(valores);

        } else {
            double tStart = omp_get_wtime();
            bubbleSortSerial(valores);
            double tEnd = omp_get_wtime();

            double tempo = 1000.0 * (tEnd - tStart);
            printf("Serial | Tempo: %.4f ms\n", tempo);
        }

        return valores;
    }
};
