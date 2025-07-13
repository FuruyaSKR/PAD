#include <iostream>
#include <vector>
#include <ctime>
#include "Ordenadores/MergeSort.cpp"
#include "Ordenadores/QuickSort.cpp"
#include "Ordenadores/BubbleSort.cpp"
#include "Ordenadores/InsertionSort.cpp"

using namespace std;

int main()
{
    cout << "Selecione o algoritmo:\n";
    cout << "1 - MergeSort\n";
    cout << "2 - QuickSort\n";
    cout << "3 - BubbleSort\n";
    cout << "4 - InsertionSort\n";
    int algoritmo;
    cin >> algoritmo;

    cout << "Rodar em modo paralelo? (1 = Sim, 0 = Nao): ";
    bool paralelo;
    cin >> paralelo;

    Ordenador* ordenador;

    switch (algoritmo)
    {
        case 1:
            ordenador = new MergeSort(paralelo);
            break;
        case 2:
            ordenador = new QuickSort(paralelo);
            break;
        case 3:
            ordenador = new BubbleSort(paralelo);
            break;
        case 4:
            ordenador = new InsertionSort(paralelo);
            break;
        default:
            cout << "Opcao invalida." << endl;
            return 1;
    }

    const int SIZE = 100000;
    vector<int> inteiros(SIZE);

    srand(time(NULL));
    for (int i = 0; i < SIZE; i++) {
        inteiros[i] = rand() % SIZE; 
    }

    // cout << "Vetor gerado: ";
    // for (int num : inteiros) cout << num << " ";
    // cout << endl;

    vector<int> ordenado = ordenador->ordenador(inteiros);

    // cout << "Vetor ordenado: ";
    // for (auto v : ordenado) cout << v << " ";
    // cout << endl;

    delete ordenador;
    return 0;
}
