#include <iostream>
#include <vector>
#include <ctime>
#include <omp.h>
#include "Ordenador.hpp"

using namespace std;

class InsertionSort : public Ordenador
{
private:
    bool paralelo;

    void insertionSortSerial(vector<int>& arr)
    {
        int n = arr.size();
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    void insertionSortParalelo(vector<int>& arr)
    {
        int n = arr.size();
        #pragma omp parallel for
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

public:
    InsertionSort(bool paralelo = false)
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
                insertionSortParalelo(copia);
                clock_t end = clock();

                double tempo = 1000.0 * (end - start) / CLOCKS_PER_SEC;
                printf("Teste com %d Threads: %.2f ms\n", numThread, tempo);
            }
        } else {
            clock_t start = clock();
            insertionSortSerial(valores);
            clock_t end = clock();

            double tempo = 1000.0 * (end - start) / CLOCKS_PER_SEC;
            printf("Tempo Serial: %.2f ms\n", tempo);
        }

        return valores;
    }
};
