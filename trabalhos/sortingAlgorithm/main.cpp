#include <iostream>
#include <vector>
#include <ctime>
#include "Ordenadores/MergeSort.cpp"
#include "Ordenadores/QuickSort.cpp"
#include "Ordenadores/TimSort.cpp"
#include "Ordenadores/HeapSort.cpp"
#include "Ordenadores/BuscaBinaria.hpp"

using namespace std;

void imprimirVetores(const vector<int>& original, const vector<int>& ordenado)
{
    cout << "Vetor gerado: ";
    for (int num : original) cout << num << " ";
    cout << endl;

    cout << "Vetor ordenado: ";
    for (int num : ordenado) cout << num << " ";
    cout << endl;
}

int main()
{
    cout << "Selecione o algoritmo:\n";
    cout << "1 - MergeSort\n";
    cout << "2 - QuickSort\n";
    cout << "3 - TimSort\n";
    cout << "4 - HeapSort\n";
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
            ordenador = new TimSort(paralelo);
            break;
        case 4:
            ordenador = new HeapSort(paralelo);
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

    vector<int> ordenado = ordenador->ordenador(inteiros);

    // imprimirVetores(inteiros, ordenado);

    int valorBusca;
    cout << "Digite o valor para busca binaria: ";
    cin >> valorBusca;

    executarBuscaBinaria(ordenado, valorBusca, paralelo);


    delete ordenador;
    return 0;
}
