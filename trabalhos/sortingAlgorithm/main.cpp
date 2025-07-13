#include <iostream>
#include <vector>
#include <ctime>
#include "Ordenadores/MergeSort.cpp"
#include "Ordenadores/QuickSort.cpp"
#include "Ordenadores/BubbleSort.cpp"
#include "Ordenadores/InsertionSort.cpp"

using namespace std;

// Classe auxiliar para pesquisa binária
class PesquisaBinaria {
public:
    int buscar(vector<int>& arr, int valor) {
        int esquerda = 0;
        int direita = arr.size() - 1;

        while (esquerda <= direita) {
            int meio = esquerda + (direita - esquerda) / 2;

            if (arr[meio] == valor)
                return meio;

            if (arr[meio] < valor)
                esquerda = meio + 1;
            else
                direita = meio - 1;
        }

        return -1; // Não encontrado
    }
};

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

    const int SIZE = 20;
    vector<int> inteiros(SIZE);

    srand(time(NULL));
    for (int i = 0; i < SIZE; i++) {
        inteiros[i] = rand() % 100; 
    }

    cout << "Vetor gerado: ";
    for (int num : inteiros) cout << num << " ";
    cout << endl;

    vector<int> ordenado = ordenador->ordenador(inteiros);

    cout << "Vetor ordenado: ";
    for (int num : ordenado) cout << num << " ";
    cout << endl;

    // Pesquisa binária após ordenar
    PesquisaBinaria pb;
    cout << "Digite o valor para buscar com pesquisa binaria: ";
    int valorBusca;
    cin >> valorBusca;

    clock_t startBusca = clock();
    int resultado = pb.buscar(ordenado, valorBusca);
    clock_t endBusca = clock();

    double tempoBusca = 1000.0 * (endBusca - startBusca) / CLOCKS_PER_SEC;

    if (resultado != -1) {
        cout << "Valor " << valorBusca << " encontrado na posicao " << resultado << endl;
    } else {
        cout << "Valor " << valorBusca << " nao encontrado." << endl;
    }
    printf("Tempo da Pesquisa Binaria: %.4f ms\n", tempoBusca);


    delete ordenador;
    return 0;
}
