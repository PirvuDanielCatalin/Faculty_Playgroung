#include <iostream>
#include <fstream>
#include <map>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;

struct gramatica
{
    vector<string> neterminale;
    vector<string> alfabet;
    string stareInitiala;
    map<string, vector<string>> productii;
    // productii[neterminal] = { alpha1, alpha2, ... , alphan }, unde alphai sunt siruri formate cu terminale si neterminale
} Gram;

struct productie
{
    string neterminal;
    string derivare;
};

struct result
{
    map<string, vector<string>> First;
    map<string, vector<string>> Follow;
    map<string, map<string, productie>> M;
    map<string, map<string, vector<string>>> LookAhead;
} Result;

string sirDeIntrare;
string token;
bool stareDeEroare = false;
ofstream fout("Iesire.txt");

bool isElementOfArray(string el, vector<string> arr)
{
    auto pos = find(arr.begin(), arr.end(), el);
    return (pos != arr.end());
}

void citireDate()
{
    Gram.neterminale = {"E", "G", "T", "S", "F"};

    Gram.alfabet = {"+", "@", "*", "n", "(", ")"};

    Gram.stareInitiala = "E";

    Gram.productii["E"] = {{"E", "TG"}};
    Gram.productii["F"] = {{"F", "n"}, {"F", "(E)"}};
    Gram.productii["G"] = {{"G", "+TG"}, {"G", "@"}};
    Gram.productii["S"] = {{"S", "*FS"}, {"S", "@"}};
    Gram.productii["T"] = {{"T", "FS"}};

    Result.First["E"] = {"n", "("};
    Result.First["F"] = {"n", "("};
    Result.First["G"] = {"+", "@"};
    Result.First["S"] = {"*", "@"};
    Result.First["T"] = {"n", "("};

    Result.Follow["E"] = {"$", ")"};
    Result.Follow["F"] = {"*", "+", "$", ")"};
    Result.Follow["G"] = {"$", ")"};
    Result.Follow["S"] = {"+", "$", ")"};
    Result.Follow["T"] = {"+", "$", ")"};

    Result.LookAhead["E"]["TG"] = {"n", "("};
    Result.LookAhead["F"]["(E)"] = {"("};
    Result.LookAhead["F"]["n"] = {"n"};
    Result.LookAhead["G"]["+TG"] = {"+"};
    Result.LookAhead["G"]["@"] = {"$", ")"};
    Result.LookAhead["S"]["*FS"] = {"*"};
    Result.LookAhead["S"]["@"] = {"+", "$", ")"};
    Result.LookAhead["T"]["FS"] = {"n", "("};
}

string scan()
{
    string temp(1, sirDeIntrare[0]);
    sirDeIntrare = sirDeIntrare.substr(1, sirDeIntrare.length());
    return temp;
}

void check(string sir);

void parse(string sir);

void apelNeterminal(string neterminal);

void E();
void G();
void T();
void S();
void F();

int main()
{
    citireDate();

    cout << "Introduceti sirul de intrare: ";
    cin >> sirDeIntrare;
    sirDeIntrare = sirDeIntrare + "$";
    token = scan();
    apelNeterminal(Gram.stareInitiala);

    if (token != "$" && stareDeEroare == false)
        fout << "Error: EOF expected" << endl;

    return 0;
}

void check(string sir)
{
    if (stareDeEroare == false)
    {
        string temp(1, sir[0]);
        if (isElementOfArray(temp, Gram.alfabet))
        {
            if (temp == token)
                token = scan();
            else
            {
                fout << "Error: " << temp << " expected" << endl;
                stareDeEroare = true;
                return;
            }
            if (sir.substr(1, sir.length()) != "")
                check(sir.substr(1, sir.length()));
        }
        else
        {
            apelNeterminal(temp);
            if (sir.substr(1, sir.length()) != "")
                check(sir.substr(1, sir.length()));
        }
    }
}

void parse(string sir)
{
    if (stareDeEroare == false)
    {
        string temp(1, sir[0]);
        if (isElementOfArray(temp, Gram.alfabet))
        {
            if (temp != "@")
                token = scan();

            if (sir.substr(1, sir.length()) != "")
                check(sir.substr(1, sir.length()));
        }
        else
        {
            apelNeterminal(temp);
            if (sir.substr(1, sir.length()) != "")
                check(sir.substr(1, sir.length()));
        }
    }
}

void apelNeterminal(string neterminal)
{
    switch (neterminal[0])
    {
    case 'E':
        E();
        break;
    case 'G':
        G();
        break;
    case 'T':
        T();
        break;
    case 'S':
        S();
        break;
    case 'F':
        F();
        break;
    default:
        fout << "Error: Neterminal Inexistent" << endl;
        stareDeEroare = true;
    }
}

void E()
{
    if (stareDeEroare == false)
    {
        string deriv = "";
        for (auto derivare : Result.LookAhead["E"])
            if (isElementOfArray(token, derivare.second))
            {
                deriv = derivare.first;
                break;
            }

        if (deriv != "")
        {
            fout << "E -> " << deriv << endl;
            parse(deriv);
        }
        else
        {
            fout << "Error: Se astepta token diferit" << endl;
            stareDeEroare = true;
            return;
        }
    }
}

void G()
{
    if (stareDeEroare == false)
    {
        string deriv = "";
        for (auto derivare : Result.LookAhead["G"])
            if (isElementOfArray(token, derivare.second))
            {
                deriv = derivare.first;
                break;
            }

        if (deriv != "")
        {
            fout << "G -> " << deriv << endl;
            parse(deriv);
        }
        else
        {
            fout << "Error: Se astepta token diferit" << endl;
            stareDeEroare = true;
            return;
        }
    }
}

void T()
{
    if (stareDeEroare == false)
    {
        string deriv = "";
        for (auto derivare : Result.LookAhead["T"])
            if (isElementOfArray(token, derivare.second))
            {
                deriv = derivare.first;
                break;
            }

        if (deriv != "")
        {
            fout << "T -> " << deriv << endl;
            parse(deriv);
        }
        else
        {
            fout << "Error: Se astepta token diferit" << endl;
            stareDeEroare = true;
            return;
        }
    }
}

void S()
{
    if (stareDeEroare == false)
    {
        string deriv = "";
        for (auto derivare : Result.LookAhead["S"])
            if (isElementOfArray(token, derivare.second))
            {
                deriv = derivare.first;
                break;
            }

        if (deriv != "")
        {
            fout << "S -> " << deriv << endl;
            parse(deriv);
        }
        else
        {
            fout << "Error: Se astepta token diferit" << endl;
            stareDeEroare = true;
            return;
        }
    }
}

void F()
{
    if (stareDeEroare == false)
    {
        string deriv = "";
        for (auto derivare : Result.LookAhead["F"])
            if (isElementOfArray(token, derivare.second))
            {
                deriv = derivare.first;
                break;
            }

        if (deriv != "")
        {
            fout << "F -> " << deriv << endl;
            parse(deriv);
        }
        else
        {
            fout << "Error: Se astepta token diferit" << endl;
            stareDeEroare = true;
            return;
        }
    }
}
