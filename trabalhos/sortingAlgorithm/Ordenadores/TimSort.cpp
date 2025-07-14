#include <iostream>
#include <vector>
#include <ctime>
#include <omp.h>
#include "Ordenador.hpp"

using namespace std;

class TimSort : public Ordenador
{
private:
    bool paralelo;
    const int RUN = 64; 

    void insertionSort(vector<int>& arr, int left, int right)
    {
        for (int i = left + 1; i <= right; i++) {
            int temp = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > temp) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    void merge(vector<int>& arr, int l, int m, int r)
    {
        int len1 = m - l + 1, len2 = r - m;
        vector<int> left(len1), right(len2);

        for (int i = 0; i < len1; i++)
            left[i] = arr[l + i];
        for (int i = 0; i < len2; i++)
            right[i] = arr[m + 1 + i];

        int i = 0, j = 0, k = l;

        while (i < len1 && j < len2) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }

        while (i < len1) arr[k++] = left[i++];
        while (j < len2) arr[k++] = right[j++];
    }

    void timSortSerial(vector<int>& arr, int n)
    {
        for (int i = 0; i < n; i += RUN)
            insertionSort(arr, i, min((i + RUN - 1), (n - 1)));

        for (int size = RUN; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = min((left + 2 * size - 1), (n - 1));
                if (mid < right)
                    merge(arr, left, mid, right);
            }
        }
    }

    void mergeParalelo(vector<int>& arr, int left, int mid, int right)
    {
        if ((right - left) < 10000) {
            merge(arr, left, mid, right);
            return;
        }

        #pragma omp parallel sections
        {
            #pragma omp section
            mergeParalelo(arr, left, (left + mid) / 2, mid);

            #pragma omp section
            mergeParalelo(arr, mid + 1, (mid + 1 + right) / 2, right);
        }

        merge(arr, left, mid, right);
    }

    void timSortParalelo(vector<int>& arr, int n)
    {
        for (int i = 0; i < n; i += RUN)
            insertionSort(arr, i, min((i + RUN - 1), (n - 1)));

        for (int size = RUN; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {
                int mid = left + size - 1;
                int right = min((left + 2 * size - 1), (n - 1));
                if (mid < right)
                    mergeParalelo(arr, left, mid, right);
            }
        }
    }

public:
    TimSort(bool paralelo = false)
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
                    timSortParalelo(copia, n);
                }

                double tEnd = omp_get_wtime();
                double tempo = 1000.0 * (tEnd - tStart);
                printf("Threads: %d | Tempo: %.4f ms\n", numThread, tempo);
            }

            omp_set_num_threads(omp_get_max_threads());
            #pragma omp parallel
            {
                #pragma omp single
                timSortParalelo(valores, n);
            }

        } else {
            double tStart = omp_get_wtime();
            timSortSerial(valores, n);
            double tEnd = omp_get_wtime();

            double tempo = 1000.0 * (tEnd - tStart);
            printf("Serial | Tempo: %.4f ms\n", tempo);
        }

        return valores;
    }
};
