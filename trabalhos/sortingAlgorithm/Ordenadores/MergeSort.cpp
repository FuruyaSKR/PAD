#include <iostream>
#include <vector>
#include <ctime>
#include <omp.h>
#include "Ordenador.hpp"

using namespace std;

class MergeSort : public Ordenador
{
private:
    bool paralelo;

    void merge(vector<int>& arr, int left, int mid, int right)
    {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        vector<int> leftArr(n1), rightArr(n2);

        for (int i = 0; i < n1; i++)
            leftArr[i] = arr[left + i];

        for (int j = 0; j < n2; j++)
            rightArr[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }

    void mergeSortSerial(vector<int>& arr, int left, int right)
    {
        if (left >= right)
            return;

        int mid = left + (right - left) / 2;

        mergeSortSerial(arr, left, mid);
        mergeSortSerial(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    void mergeSortParalelo(vector<int>& arr, int left, int right)
{
    if (left >= right)
        return;

    int mid = left + (right - left) / 2;

    if ((right - left) > 10000) { 
        #pragma omp parallel sections
        {
            #pragma omp section
            mergeSortParalelo(arr, left, mid);

            #pragma omp section
            mergeSortParalelo(arr, mid + 1, right);
        }
    } else {
        mergeSortSerial(arr, left, mid);
        mergeSortSerial(arr, mid + 1, right);
    }

    merge(arr, left, mid, right);
}

public:
    MergeSort(bool paralelo = false)
    {
        this->paralelo = paralelo;
    }

    vector<int> ordenador(vector<int> valores) override
{
    if (paralelo) {
        const int numThreads[] = {1, 2, 4, 8, 12};
        int n = valores.size();

        for (int i = 0; i < 5; i++) {
            vector<int> copia = valores;
            int numThread = numThreads[i];
            omp_set_num_threads(numThread);

            double tStart = omp_get_wtime();
            mergeSortParalelo(copia, 0, n - 1);
            double tEnd = omp_get_wtime();

            double tempo = 1000.0 * (tEnd - tStart); 
            printf("Threads: %d | Tempo: %.4f ms\n", numThread, tempo);
        }

        omp_set_num_threads(omp_get_max_threads());
        mergeSortParalelo(valores, 0, valores.size() - 1);

    } else {
        double tStart = omp_get_wtime();
        mergeSortSerial(valores, 0, valores.size() - 1);
        double tEnd = omp_get_wtime();

        double tempo = 1000.0 * (tEnd - tStart);
        printf("Serial | Tempo: %.2f ms\n", tempo);
    }

    return valores; 
}
};
