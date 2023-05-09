#include <iostream>
#include <fstream>
#include <vector>
#include <map>
#include <string>
#include <algorithm>
#include <queue>
#include <cstring>
using namespace std;

vector<string> split(string str, string sep)
{
	vector<string> arr;

	char *cstr = const_cast<char *>(str.c_str());
	char *current;

	current = strtok(cstr, sep.c_str());
	while (current != NULL)
	{
		arr.push_back(current);
		current = strtok(NULL, sep.c_str());
	}

	return arr;
}

bool isElementOfArray(string el, vector<string> arr)
{
	auto pos = find(arr.begin(), arr.end(), el);
	if (pos != arr.end())
		return true;
	else
		return false;
}

struct gramatica
{
	vector<string> neterminale;
	vector<string> alfabet;
	string stareInitiala;
	map<string, vector<string>> productii;
	// productii[neterminal] = { alpha1, alpha2, ... , alphan }, unde alphai sunt siruri formate cu terminale si neterminale
} G;

struct productie
{
	string neterminal;
	string derivare;
};

struct coadaDeProductii // { neterminal curent,  <--[ (neterminal productie, derivare productie), (), (), ... ]<-- }
{
	string neterminal;
	queue<productie> productii;
};

struct result
{
	map<string, vector<string>> First;
	map<string, vector<string>> Follow;
	map<string, map<string, productie>> M;
	map<string, map<string, vector<string>>> LookAhead;
} R;

void afisareCerinta()
{
	cout << endl
		 << endl;
	cout << "    Cerinta:" << endl
		 << "    Sa se scrie un program care genereaza un fisier text care va contine functiile (C sau C++)" << endl
		 << "    pentru algoritmul recursiv descendent pentru o gramatica data." << endl
		 << "    La intrare programul primeste gramatica respectiva, care se presupune ca este de tip LL(1)." << endl
		 << "    Pentru fiecare productie A->x trebuie sa se calculeze multimea First'(xFollow'(A))." << endl
		 << endl
		 << "    Conventii:" << endl
		 << "    Simbolul lambda este @." << endl
		 << endl;
}

int citireDate()
{
	ifstream date("Gramatica3.txt");
	vector<string> aux1, aux2, aux3;
	if (date.is_open())
	{
		for (string linie; getline(date, linie);)
		{
			aux1 = split(linie, " -> ");
			G.neterminale.push_back(aux1[0]);

			aux2 = split(aux1[1], "|");

			for (int i = 0; i < aux2.size(); i++)
			{
				G.productii[aux1[0]].push_back(aux2[i]);
				for (int j = 0; j < aux2[i].size(); j++)
					if (!('A' <= aux2[i][j] && aux2[i][j] <= 'Z') && !isElementOfArray(string(1, aux2[i][j]), G.alfabet))
						G.alfabet.push_back(string(1, aux2[i][j]));
			}
		}
		G.stareInitiala = G.neterminale[0];
		date.close();
	}
	return 1;
}

void afisareDate()
{
	cout << "    Date:" << endl;
	cout << "    Neterminale: ";
	for (int i = 0; i < G.neterminale.size() - 1; i++)
		cout << G.neterminale[i] << ", ";
	cout << G.neterminale[G.neterminale.size() - 1] << "." << endl
		 << endl;

	cout << "    Alfabet: ";
	for (int i = 0; i < G.alfabet.size() - 1; i++)
		cout << G.alfabet[i] << ", ";
	cout << G.alfabet[G.alfabet.size() - 1] << "." << endl
		 << endl;

	cout << "    Starea initiala: " << G.stareInitiala << endl
		 << endl;

	cout << "    Productiile: " << endl;
	for (auto neterminal = G.productii.cbegin(); neterminal != G.productii.cend(); neterminal++)
		for (int productie = 0; productie < (neterminal->second).size(); productie++)
			cout << "    " << neterminal->first << " -> " << G.productii[neterminal->first][productie] << endl;
	cout << endl;
}

int verificareContineNeterminalul(string neterminal, string sir)
{
	for (int i = 0; i < sir.length(); i++)
		if (neterminal[0] == sir[i])
			return i;
	return -1;
}

bool verificareFirstComplet(string neterminal, queue<productie> productiiNeanalizate)
{
	// Verifica daca variabila "neterminal" apare ca stare de plecare intr-o productie din coada de productii "productiiNeanalizate"

	while (!productiiNeanalizate.empty())
	{
		productie p = productiiNeanalizate.front();
		if (p.neterminal == neterminal)
			return false;
		productiiNeanalizate.pop();
	}
	return true;
}

void First()
{
	queue<productie> productii;
	// Preiau fiecare productie ca pereche (neterminal, derivare) si o pun in coada
	for (auto neterminal = G.productii.cbegin(); neterminal != G.productii.cend(); neterminal++)
		for (int ctr = 0; ctr < (neterminal->second).size(); ctr++)
		{
			productie p = {neterminal->first, G.productii[neterminal->first][ctr]};
			productii.push(p);
		}

	// Ciclez prin coada, adica verific fiecare productie,
	// iar daca nu o pot prelucra complet o reintroduc in coada pt a fi reprocesata in viitor
	while (!productii.empty())
	{
		// Preiau productia curenta si aflu primum simbol din derivare, deoarece First( derivare ) = First( primulSimbolDinDerivare )
		productie p = productii.front();
		string caracter(1, p.derivare[0]);

		// Daca variabila "caracter" e terminal sau lambda, adica apartine alfabetului,
		// o inseram direct in vectorul First corespunzator neterminalului productiei curente
		if (isElementOfArray(caracter, G.alfabet))
		{
			if (!isElementOfArray(caracter, R.First[p.neterminal]))
				R.First[p.neterminal].push_back(caracter);
		}
		else
			// Daca variabila "caracter" e neterminal
			if (isElementOfArray(caracter, G.neterminale))
		{
			// Verificam daca First[caracter] a fost deja calculat
			// Daca nu, punem productia curenta la finalul cozii pt a fi procesata in viitor
			if (R.First[caracter].empty())
			{
				productii.push(p);
			}
			else
			{
				// Daca da, preluam elementele din First[caracter]
				for (int ctr = 0; ctr < R.First[caracter].size(); ctr++)
				{
					// Daca elementul este lambda,
					// simulam stergerea neterminalului prin introducerea in coada a unei noi productii
					// in care derivarea nu contine primul neterminal
					if (R.First[caracter][ctr][0] == '@')
					{
						string derivareNoua = p.derivare.erase(0, 1);
						if (derivareNoua == "")
							derivareNoua = "@";

						productie aux = {p.neterminal, derivareNoua};
						productii.push(aux);
					}
					else
					{
						// Daca elementul este diferit de lambda,
						// il introducem in vectorul First corespunzator neterminalului productiei curente
						if (!isElementOfArray(R.First[caracter][ctr], R.First[p.neterminal]))
							R.First[p.neterminal].push_back(R.First[caracter][ctr]);
					}
				}
				// Daca First[caracter] nu e complet, reintroducem productia pt a ne asigura ca toate simbolurile au fost preluate
				if (!verificareFirstComplet(caracter, productii))
					productii.push(p);
			}
		}
		productii.pop();
	}
}

void Follow()
{
	queue<coadaDeProductii> cozi;

	// Preiau pt fiecare neterminal, toate productiile in care acesta apare in derivari.
	// Prelucrez productiile: modific derivarile astfel incat noile derivari sa reprezinte sirurile de dupa neterminalul curent
	// Introduc productiile prelucrate intr-o coada

	for (int i = 0; i < G.neterminale.size(); i++)
	{
		queue<productie> productii;
		for (auto neterminal = G.productii.cbegin(); neterminal != G.productii.cend(); neterminal++)
			for (int ctr = 0; ctr < (neterminal->second).size(); ctr++)
			{
				int pozitie = verificareContineNeterminalul(G.neterminale[i], G.productii[neterminal->first][ctr]);
				if (pozitie > -1)
				{
					string derivare = G.productii[neterminal->first][ctr];
					derivare = derivare.substr(pozitie + 1, derivare.length());

					if (derivare == "")
						derivare = "@";

					productie p = {neterminal->first, derivare};
					productii.push(p);
				}
			}

		coadaDeProductii cdp = {G.neterminale[i], productii};
		cozi.push(cdp);
	}

	R.Follow[G.stareInitiala].push_back("$");

	// Ciclez prin lista de neterminale si construiesc Follow[neterminal] pe baza productiile prelucrate asociate fiecaruia
	while (!cozi.empty())
	{
		coadaDeProductii cdp = cozi.front();
		int nevoieDeFollow = 0;

		// Preiau neterminalul curent si ciclez prin coada asociata acestuia

		while (!cdp.productii.empty())
		{
			productie p = cdp.productii.front();
			string derivare = p.derivare;

			// Preiau productia curenta si derivarea acesteia

			if (derivare == "@")
			{
				// Daca derivarea e lambda si
				// neterminalul curent analizat = cdp.neterminal este diferit de neterminalul productiei curente analizate = p.neterminal,
				// Atunci Follow[ neterminal curent ] = Follow[ neterminal productie curenta ]
				if (p.neterminal != cdp.neterminal)
				{
					// Daca Follow[ neterminal productie curenta ] nu a fost prelucrat, marcam asta prin setarea variabilei "nevoieDeFollow"
					// si fortam iesirea din while
					if (R.Follow[p.neterminal].empty())
					{
						//cdp.productii.push(p);
						nevoieDeFollow = 1;
						break;
					}
					else
					{
						// Daca Follow[ neterminal productie curenta ] a fost prelucrat, preluam toate elementele acestuia in Follow[ neterminal curent ]
						for (int i = 0; i < R.Follow[p.neterminal].size(); i++)
							if (!isElementOfArray(R.Follow[p.neterminal][i], R.Follow[cdp.neterminal]))
								R.Follow[cdp.neterminal].push_back(R.Follow[p.neterminal][i]);
					}
				}
			}
			else
			{
				// Daca derivarea este diferita de lambda, preluam primul simbol din derivare
				string caracter(1, p.derivare[0]);

				// Daca acesta este simbol apartinand alfabetului, il introducem in Follow[ neterminal curent ]
				if (isElementOfArray(caracter, G.alfabet))
				{
					if (!isElementOfArray(caracter, R.Follow[cdp.neterminal]))
						R.Follow[cdp.neterminal].push_back(caracter);
				}
				else
					// Daca este neterminal, in Follow[ neterminal curent ] preluam First-ul acestuia
					if (isElementOfArray(caracter, G.neterminale))
				{
					for (int i = 0; i < R.First[caracter].size(); i++)
					{
						// Daca simbolul din First este lambda, simulam stergerea neterminalului prin
						// inserarea unei noi productii in coada interna asociata neterminalului curent = cdp.neterminal
						if (R.First[caracter][i][0] == '@')
						{
							string derivareNoua = p.derivare.erase(0, 1);

							if (derivareNoua == "")
								derivareNoua = "@";

							productie aux = {p.neterminal, derivareNoua};
							cdp.productii.push(aux);
						}
						else
						{
							// Daca simbolul din First este diferit de lambda, il introducem in Follow[ neterminal curent ]
							if (!isElementOfArray(R.First[caracter][i], R.Follow[cdp.neterminal]))
								R.Follow[cdp.neterminal].push_back(R.First[caracter][i]);
						}
					}
				}
			}
			cdp.productii.pop();
		}

		// Daca s-a marcat nevoia de o prelucrarea a unui neterminal viitor, se reintroduce structura asociata neterminalului la finalul cozii
		if (nevoieDeFollow == 1)
			cozi.push(cdp);

		cozi.pop();
	}
}

void MatricePredictiva()
{
	for (auto neterminal = G.productii.cbegin(); neterminal != G.productii.cend(); neterminal++)
		for (int ctr = 0; ctr < (neterminal->second).size(); ctr++)
		{
			string neterm = neterminal->first;
			string derivare = G.productii[neterminal->first][ctr];
			string term(1, derivare[0]);

			if ('A' <= derivare[0] && derivare[0] <= 'Z')
			{
				for (int i = 0; i < R.First[neterm].size(); i++)
				{
					if (R.First[neterm][i][0] != '@')
					{
						term = R.First[neterm][i];
						R.M[neterm][term] = {neterminal->first, G.productii[neterminal->first][ctr]};
					}
					else
					{
						if (!isElementOfArray("@", G.productii[neterm]))
						{
							for (int j = 0; j < R.Follow[neterm].size(); j++)
							{
								term = R.Follow[neterm][j];
								R.M[neterm][term] = {neterminal->first, G.productii[neterminal->first][ctr]};
							}
						}
					}
				}
			}
			else if (derivare[0] == '@')
			{
				for (int j = 0; j < R.Follow[neterm].size(); j++)
				{
					term = R.Follow[neterm][j];
					R.M[neterm][term] = {neterminal->first, G.productii[neterminal->first][ctr]};
				}
			}
			else
			{
				R.M[neterm][term] = {neterminal->first, G.productii[neterminal->first][ctr]};
			}
		}
}

void LookAhead()
{
	for (auto neterminal : G.neterminale)
		for (auto derivare : G.productii[neterminal])
		{
			string caracter(1, derivare[0]);

			// Primul simbol din derivarea curenta este simbol din alfabet
			if (isElementOfArray(caracter, G.alfabet))
			{
				// Diferit de lambda
				if (caracter != "@")
				{
					if (!isElementOfArray(caracter, R.LookAhead[neterminal][derivare]))
						R.LookAhead[neterminal][derivare].push_back(caracter);
				}
				// Egal cu lambda
				else
					for (auto f : R.Follow[neterminal])
						if (!isElementOfArray(f, R.LookAhead[neterminal][derivare]))
							R.LookAhead[neterminal][derivare].push_back(f);
			}
			// Primul simbol din derivarea curenta este neterminal
			else if (isElementOfArray(caracter, G.neterminale))
			{
				// Derivarile care contin simbolul neterminal sunt puse intr-o coada
				queue<string> derivari;
				derivari.push(derivare);

				while (!derivari.empty())
				{
					string deriv = derivari.front();
					string d(1, deriv[0]);
					// Primul simbol din noua derivare este neterminal
					if (isElementOfArray(d, G.neterminale))
					{
						// Se uita in First-ul noii derivari
						for (auto f : R.First[d])
							// Adauga in lookahead simboluri diferite de lambda
							if (f != "@")
							{
								if (!isElementOfArray(f, R.LookAhead[neterminal][derivare]))
									R.LookAhead[neterminal][derivare].push_back(f);
							}
							// Daca First contine lambda, adauga in coada o noua derivare care sa fie analizata ulterior
							else
							{
								if (deriv.substr(1, deriv.length()) == "")
									deriv = "@";
								else
									deriv = deriv.substr(1, deriv.length());
								derivari.push(deriv);
							}
					}
					// Primul simbol din noua derivare este simbol din alfabet
					else if (isElementOfArray(d, G.alfabet))
					{
						// Diferit de lambda
						if (d != "@")
						{
							if (!isElementOfArray(d, R.LookAhead[neterminal][derivare]))
								R.LookAhead[neterminal][derivare].push_back(d);
						}
						// Egal cu lambda
						else
							for (auto f : R.Follow[neterminal])
								if (!isElementOfArray(f, R.LookAhead[neterminal][derivare]))
									R.LookAhead[neterminal][derivare].push_back(f);
					}
					derivari.pop();
				}
			}
		}
}

void afisareRezultate()
{
	cout << "    First:" << endl;
	for (auto neterminal = R.First.cbegin(); neterminal != R.First.cend(); neterminal++)
	{
		cout << "    " << neterminal->first << ": [ ";
		for (int ctr = 0; ctr < (neterminal->second).size() - 1; ctr++)
			cout << '"' << (neterminal->second)[ctr] << '"' << " , ";
		cout << '"' << (neterminal->second)[(neterminal->second).size() - 1] << '"' << " ]" << endl;
	}

	cout << endl
		 << "    Follow:" << endl;
	for (auto neterminal = R.Follow.cbegin(); neterminal != R.Follow.cend(); neterminal++)
	{
		cout << "    " << neterminal->first << ": [ ";
		for (int ctr = 0; ctr < (neterminal->second).size() - 1; ctr++)
			cout << '"' << (neterminal->second)[ctr] << '"' << " , ";
		cout << '"' << (neterminal->second)[(neterminal->second).size() - 1] << '"' << " ]" << endl;
	}

	cout << endl
		 << "    Lookahead:" << endl;
	for (auto i : R.LookAhead)
		for (auto j : i.second)
		{
			cout << "    " << i.first << " -> " << j.first << ": [ ";
			for (int ctr = 0; ctr < j.second.size() - 1; ctr++)
				cout << '"' << j.second[ctr] << '"' << " , ";
			cout << '"' << j.second[j.second.size() - 1] << '"' << " ]" << endl;
		}

	cout << endl
		 << "    Matricea Predictiva:" << endl;
	for (auto neterminal = R.M.cbegin(); neterminal != R.M.cend(); neterminal++)
		for (auto terminal = (neterminal->second).cbegin(); terminal != (neterminal->second).cend(); terminal++)
			cout << "    Mat [ " << '"' << neterminal->first << '"' << " ][ " << '"' << terminal->first << '"' << " ]= "
				 << '"' << terminal->second.neterminal << " -> " << terminal->second.derivare << '"' << endl;
	cout << endl;
}

void generareCodCpp()
{
	ofstream f;
	f.open("Generated.cpp", ofstream::out | ofstream::trunc);

	if (f.is_open())
	{
		// Librarii
		f << "#include <iostream>" << endl;
		f << "#include <fstream>" << endl;
		f << "#include <map>" << endl;
		f << "#include <vector>" << endl;
		f << "#include <string>" << endl;
		f << "#include <algorithm>" << endl;
		f << "using namespace std;" << endl;

		f << endl;

		// Structuri
		f << "struct gramatica" << endl;
		f << "{" << endl;
		f << "	vector<string> neterminale;" << endl;
		f << "	vector<string> alfabet;" << endl;
		f << "	string stareInitiala;" << endl;
		f << "	map<string, vector<string>> productii;" << endl;
		f << "	// productii[neterminal] = { alpha1, alpha2, ... , alphan }, unde alphai sunt siruri formate cu terminale si neterminale" << endl;
		f << "} Gram;" << endl;

		f << endl;

		f << "struct productie" << endl;
		f << "{" << endl;
		f << "	string neterminal;" << endl;
		f << "	string derivare;" << endl;
		f << "};" << endl;

		f << endl;

		f << "struct result" << endl;
		f << "{" << endl;
		f << "	map<string, vector<string>> First;" << endl;
		f << "	map<string, vector<string>> Follow;" << endl;
		f << "	map<string, map<string, productie>> M;" << endl;
		f << "	map<string, map<string, vector<string>>> LookAhead;" << endl;
		f << "} Result;" << endl;

		f << endl;

		f << "string sirDeIntrare;" << endl;
		f << "string token;" << endl;
		f << "bool stareDeEroare = false;" << endl;
		f << "ofstream fout(" << '"' << "Iesire.txt" << '"' << ");" << endl;

		f << endl;

		// Functii si Prototipuri
		f << "bool isElementOfArray(string el, vector<string> arr)" << endl;
		f << "{" << endl;
		f << "	auto pos = find(arr.begin(), arr.end(), el);" << endl;
		f << "	return (pos != arr.end());" << endl;
		f << "}" << endl;

		f << endl;

		f << "void citireDate()" << endl;
		f << "{" << endl;
		f << "	Gram.neterminale = { ";
		for (int i = 0; i < G.neterminale.size() - 1; i++)
			f << '"' << G.neterminale[i] << '"' << ", ";
		f << '"' << G.neterminale[G.neterminale.size() - 1] << '"' << " };" << endl;
		f << endl;
		f << "	Gram.alfabet = { ";
		for (int i = 0; i < G.alfabet.size() - 1; i++)
			f << '"' << G.alfabet[i] << '"' << ", ";
		f << '"' << G.alfabet[G.alfabet.size() - 1] << '"' << " };" << endl;
		f << endl;
		f << "    Gram.stareInitiala = " << '"' << G.stareInitiala << '"' << ";" << endl;
		f << endl;
		for (auto neterminal = G.productii.cbegin(); neterminal != G.productii.cend(); neterminal++)
		{
			f << "    Gram.productii[" << '"' << neterminal->first << '"' << "] = { ";
			for (int ctr = 0; ctr < (neterminal->second).size() - 1; ctr++)
				f << "{ " << '"' << neterminal->first << '"' << ", " << '"' << (neterminal->second)[ctr] << '"' << " }, ";
			f << "{ " << '"' << neterminal->first << '"' << ", " << '"' << (neterminal->second)[(neterminal->second).size() - 1] << '"' << " } };" << endl;
		}
		f << endl;
		for (auto neterminal = R.First.cbegin(); neterminal != R.First.cend(); neterminal++)
		{
			f << "    Result.First[" << '"' << neterminal->first << '"' << "] = { ";
			for (int ctr = 0; ctr < (neterminal->second).size() - 1; ctr++)
				f << '"' << (neterminal->second)[ctr] << '"' << ", ";
			f << '"' << (neterminal->second)[(neterminal->second).size() - 1] << '"' << " };" << endl;
		}
		f << endl;
		for (auto neterminal = R.Follow.cbegin(); neterminal != R.Follow.cend(); neterminal++)
		{
			f << "    Result.Follow[" << '"' << neterminal->first << '"' << "] = { ";
			for (int ctr = 0; ctr < (neterminal->second).size() - 1; ctr++)
				f << '"' << (neterminal->second)[ctr] << '"' << ", ";
			f << '"' << (neterminal->second)[(neterminal->second).size() - 1] << '"' << " };" << endl;
		}
		f << endl;
		for (auto neterminal = R.LookAhead.cbegin(); neterminal != R.LookAhead.cend(); neterminal++)
			for (auto derivare = (neterminal->second).cbegin(); derivare != (neterminal->second).cend(); derivare++)
			{
				f << "    Result.LookAhead[" << '"' << neterminal->first << '"' << "][" << '"' << derivare->first << '"' << "] = { ";
				for (int ctr = 0; ctr < (derivare->second).size() - 1; ctr++)
					f << '"' << (derivare->second)[ctr] << '"' << ", ";
				f << '"' << (derivare->second)[(derivare->second).size() - 1] << '"' << " };" << endl;
			}
		f << "}" << endl;

		f << endl;

		f << "string scan()" << endl;
		f << "{" << endl;
		f << "    string temp(1, sirDeIntrare[0]);" << endl;
		f << "    sirDeIntrare = sirDeIntrare.substr(1, sirDeIntrare.length());" << endl;
		f << "    return temp;" << endl;
		f << "}" << endl;

		f << endl;

		f << "void check(string sir);" << endl;

		f << endl;

		f << "void parse(string sir);" << endl;

		f << endl;

		f << "void apelNeterminal(string neterminal);" << endl;

		f << endl;

		for (int i = 0; i < G.neterminale.size(); i++)
			f << "void " << G.neterminale[i] << "();" << endl;

		f << endl;

		// Main
		f << "int main()" << endl;
		f << "{" << endl;
		f << "    citireDate();" << endl;
		f << endl;
		f << "    cout << " << '"' << "Introduceti sirul de intrare: " << '"' << "; cin >> sirDeIntrare;" << endl;
		f << "    sirDeIntrare = sirDeIntrare + " << '"' << "$" << '"' << ";" << endl;
		f << "    token = scan();" << endl;
		f << "    apelNeterminal(Gram.stareInitiala);" << endl;
		f << endl;
		f << "    if (token != " << '"' << "$" << '"' << " && stareDeEroare == false)" << endl;
		f << "        fout << " << '"' << "Error: EOF expected" << '"' << " << endl;" << endl;
		f << endl;
		f << "    return 0;" << endl;
		f << "}" << endl;

		f << endl;

		// Implementarea prototipurilor
		f << "void check(string sir)" << endl;
		f << "{" << endl;
		f << "    if (stareDeEroare == false)" << endl;
		f << "    {" << endl;
		f << "        string temp(1, sir[0]);" << endl;
		f << "        if (isElementOfArray(temp, Gram.alfabet))" << endl;
		f << "        {" << endl;
		f << "            if(temp == token)" << endl;
		f << "                token = scan();" << endl;
		f << "            else" << endl;
		f << "            {" << endl;
		f << "                fout << " << '"' << "Error: " << '"' << " << temp << " << '"' << " expected" << '"' << " << endl;" << endl;
		f << "                stareDeEroare = true;" << endl;
		f << "                return ;" << endl;
		f << "            }" << endl;
		f << "            if (sir.substr(1,sir.length()) != " << '"' << '"' << ")" << endl;
		f << "                check(sir.substr(1,sir.length()));" << endl;
		f << "        }" << endl;
		f << "        else" << endl;
		f << "        {" << endl;
		f << "            apelNeterminal(temp);" << endl;
		f << "            if (sir.substr(1,sir.length()) != " << '"' << '"' << ")" << endl;
		f << "                check(sir.substr(1,sir.length()));" << endl;
		f << "        }" << endl;
		f << "    }" << endl;
		f << "}" << endl;

		f << endl;

		f << "void parse(string sir)" << endl;
		f << "{" << endl;
		f << "    if (stareDeEroare == false)" << endl;
		f << "    {" << endl;
		f << "        string temp(1, sir[0]);" << endl;
		f << "        if (isElementOfArray(temp, Gram.alfabet))" << endl;
		f << "        {" << endl;
		f << "            if (temp != " << '"' << "@" << '"' << ")" << endl;
		f << "                token = scan();" << endl
		  << endl;
		f << "            if (sir.substr(1,sir.length()) != " << '"' << '"' << ")" << endl;
		f << "	    	      check(sir.substr(1,sir.length()));" << endl;
		f << "        }" << endl;
		f << "        else" << endl;
		f << "        {" << endl;
		f << "            apelNeterminal(temp);" << endl;
		f << "            if (sir.substr(1,sir.length()) != " << '"' << '"' << ")" << endl;
		f << "                check(sir.substr(1,sir.length()));" << endl;
		f << "        }" << endl;
		f << "    }" << endl;
		f << "}" << endl;

		f << endl;

		f << "void apelNeterminal(string neterminal)" << endl;
		f << "{" << endl;
		f << "    switch (neterminal[0])" << endl;
		f << "    {" << endl;
		for (int i = 0; i < G.neterminale.size(); i++)
		{
			f << "        case '" << G.neterminale[i][0] << "': ";
			f << G.neterminale[i] << "(); break;" << endl;
		}
		f << "        default: fout << " << '"' << "Error: Neterminal Inexistent" << '"' << " << endl; stareDeEroare = true;" << endl;
		f << "    }" << endl;
		f << "}" << endl;

		f << endl;

		for (int i = 0; i < G.neterminale.size(); i++)
		{
			f << "void " << G.neterminale[i] << "()" << endl;
			f << "{" << endl;
			f << "    if (stareDeEroare == false)" << endl;
			f << "    {" << endl;
			f << "        string deriv = " << '"' << '"' << ";" << endl;
			f << "        for (auto derivare : Result.LookAhead[" << '"' << G.neterminale[i] << '"' << "])" << endl;
			f << "            if (isElementOfArray(token, derivare.second))" << endl;
			f << "            {" << endl;
			f << "                deriv = derivare.first;" << endl;
			f << "                break;" << endl;
			f << "            }" << endl
			  << endl;
			f << "        if (deriv != " << '"' << '"' << ")" << endl;
			f << "        {" << endl;
			f << "            fout << " << '"' << G.neterminale[i] << " -> " << '"' << " << deriv << endl;" << endl;
			f << "            parse(deriv);" << endl;
			f << "        }" << endl;
			f << "        else" << endl;
			f << "        {" << endl;
			f << "            fout << " << '"' << "Error: Se astepta token diferit" << '"' << " << endl;" << endl;
			f << "            stareDeEroare = true;" << endl;
			f << "            return ;" << endl;
			f << "        }" << endl;
			f << "    }" << endl;
			f << "}" << endl;
			f << endl;
		}

		f.close();

		cout << "    Fisierul a fost generat!" << endl;
	}
	else
	{
		cout << "    Nu s-a putut deschide fisierul!" << endl;
	}
}

int main()
{
	afisareCerinta();
	citireDate();
	afisareDate();

	First();
	Follow();
	MatricePredictiva();
	LookAhead();
	afisareRezultate();

	generareCodCpp();

	return 0;
}