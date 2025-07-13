#pragma once
#include <iostream>
#include <vector>
using namespace std;

class Ordenador
{
public:
    virtual vector<int> ordenador(vector<int> valores) = 0;
};
