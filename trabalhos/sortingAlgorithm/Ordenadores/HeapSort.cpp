#include <iostream>
#include <vector>
#include <omp.h>
#include "Ordenador.hpp"

using namespace std;

class HeapSort : public Ordenador
{
private:
    bool paralelo;

    void heapify(vector<int>& arr, int n, int i)
    {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;

        if (l < n && arr[l] > arr[largest])
            largest = l;

        if (r < n && arr[r] > arr[largest])
            largest = r;

        if (largest != i) {
            swap(arr[i], arr[largest]);
            heapify(arr, n, largest);
        }
    }

    void heapSortSerial(vector<int>& arr)
    {
        int n = arr.size();

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        for (int i = n - 1; i > 0; i--) {
            swap(arr[0], arr[i]);
            heapify(arr, i, 0);
        }
    }

public:
    HeapSort(bool paralelo = false)
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
                heapSortSerial(copia); // 🔑 Mantém serial
                double tEnd = omp_get_wtime();

                double tempo = 1000.0 * (tEnd - tStart);
                printf("Threads: %d | Tempo: %.4f ms (serial)\n", numThread, tempo);
            }

            omp_set_num_threads(omp_get_max_threads());
            heapSortSerial(valores);

        } else {
            double tStart = omp_get_wtime();
            heapSortSerial(valores);
            double tEnd = omp_get_wtime();

            double tempo = 1000.0 * (tEnd - tStart);
            printf("Serial | Tempo: %.4f ms\n", tempo);
        }

        return valores;
    }
};
