#include <stdio.h>
#include <omp.h>

#define N 4

int main() {
    int A[N][N] = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 16}
    };

    int B[N][N] = {
        {1, 0, 0, 0},
        {0, 1, 0, 0},
        {0, 0, 1, 0},
        {0, 0, 0, 1}
    };

    int C[N][N] = {0};

    #pragma omp parallel sections
    {
        #pragma omp section
        {
            #pragma omp for
            for (int i = 0; i < N / 2; i++) {
                for (int j = 0; j < N; j++) {
                    for (int k = 0; k < N; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }

        #pragma omp section
        {
            #pragma omp for
            for (int i = N / 2; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    for (int k = 0; k < N; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        }
    }

    printf("Matriz resultado (C):\n");
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            printf("%d ", C[i][j]);
        }
        printf("\n");
    }

    return 0;
}
