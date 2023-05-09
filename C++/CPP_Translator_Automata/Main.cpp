#include <iostream>
#include <fstream>
#include <vector>
#include <map>
#include <stack>
#include <string>
#include <algorithm>
#include <cstdlib>
#include <cstring>
using namespace std;

struct tranzitie
{
	string staresosire;
	string inlocuitorsimbol;
	string inlocuitorvarfstiva;
};

// Configuratia translatorului
vector<string> stari;
string stareainitiala;
vector<string> alfabetuldeintrare;
vector<string> alfabetuldeiesire;
vector<string> alfabetulstivei;
string simbolulinitialalstivei;

// tranzitiile[stare][simbolcitit][varfstiva] = { .staresosire; .inlocuitorsimbol; .inlocuitorvarfstiva }
map<string, map<string, map<string, vector<tranzitie>>>> tranzitiile;

stack<string> stiva;
vector<string> rezultate;
vector<string> arbore;
//

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

string remove_char(string str, char ch)
{
	str.erase(remove(str.begin(), str.end(), ch), str.end());
	return str;
}

bool isElementOfArray(string el, vector<string> arr)
{
	auto pos = find(arr.begin(), arr.end(), el);
	if (pos != arr.end())
		return true;
	else
		return false;
}

int citireDate()
{
	ifstream date("ExProfa.txt");
	vector<string> datetemp;
	if (date.is_open())
	{
		for (string line; getline(date, line);)
			datetemp.push_back(line);
		date.close();
	}

	// Stari
	stari = split(datetemp[0], " ");
	stareainitiala = datetemp[1];

	// Starea initiala trebuie sa apartina multimii starilor!
	if (!isElementOfArray(stareainitiala, stari))
	{
		cout << "    !! Starea initiala " << stareainitiala << " nu apartine multimii starilor. !!" << endl;
		return 0;
	}
	//

	alfabetuldeintrare = split(datetemp[2], " ");
	alfabetuldeiesire = split(datetemp[3], " ");

	// Stiva
	alfabetulstivei = split(datetemp[4], " ");
	simbolulinitialalstivei = datetemp[5];

	// Simbolul initial al stivei trebuie sa apartina alfabetului stivei!
	if (!isElementOfArray(simbolulinitialalstivei, alfabetulstivei))
	{
		cout << "    !! Simbolul initial al stivei " << simbolulinitialalstivei << " nu apartine alfabetului stivei. !!" << endl;
		return 0;
	}
	stiva.push(simbolulinitialalstivei);
	//

	// Tranzitiile
	for (int it = 6; it < datetemp.size(); it++)
	{
		vector<string> temp = split(datetemp[it], " =>,|-");

		// Starea de plecare trebuie sa apartina multimii starilor!
		if (!isElementOfArray(temp[0], stari))
		{
			cout << "    !! Starea " << temp[0] << " nu apartine multimii starilor. !!" << endl;
			return 0;
		}

		// Starea de sosire trebuie sa apartina multimii starilor!
		if (!isElementOfArray(temp[5], stari))
		{
			cout << "    !! Starea " << temp[5] << " nu apartine multimii starilor. !!" << endl;
			return 0;
		}

		// Simbolul citit trebuie sa apartina alfabetului de intrare sau sa fie lambda!
		if (temp[1] != "*" && !isElementOfArray(temp[1], alfabetuldeintrare))
		{
			cout << "    !! Simbolul " << temp[1] << " nu apartine alfabetului de intrare. !!" << endl;
			return 0;
		}

		// Simbolurile ce alcatuiesc sirul translatat trebuie sa apartina alfabetului de iesire sau sa fie lambda!
		for (int l = 0; l < temp[4].size(); l++)
		{
			string aux(1, temp[4][l]);
			if (aux != "*" && !isElementOfArray(aux, alfabetuldeiesire))
			{
				cout << "    !! Simbolul " << aux << " nu apartine alfabetului de iesire. !!" << endl;
				return 0;
			}
		}

		// Simbolul din varful stivei trebuie sa apartina alfabetului stivei sau sa fie lambda!
		if (temp[2] != "*" && !isElementOfArray(temp[2], alfabetulstivei))
		{
			cout << "    !! Simbolul " << temp[2] << " nu apartine alfabetului stivei. !!" << endl;
			return 0;
		}

		// Simbolurile ce alcatuiesc sirul inlocuitor pe stiva trebuie sa apartina alfabetului stivei sau sa fie lambda!
		for (int l = 0; l < temp[3].size(); l++)
		{
			string aux(1, temp[3][l]);
			if (aux != "*" && !isElementOfArray(aux, alfabetulstivei))
			{
				cout << "    !! Simbolul " << aux << " nu apartine alfabetului stivei. !!" << endl;
				return 0;
			}
		}

		tranzitie t = {temp[5], temp[4], temp[3]};
		tranzitiile[temp[0]][temp[1]][temp[2]].push_back(t);
	}
	//
	return 1;
}

void afisareDate()
{
	int i;
	cout << "    Date:" << endl;
	cout << "    Starile sunt : ";
	for (i = 0; i < stari.size(); i++)
		cout << stari[i] << " ";

	cout << endl
		 << "    Starea initiala este : " << stareainitiala;

	cout << endl
		 << "    Alfabetul de intrare este : ";
	for (i = 0; i < alfabetuldeintrare.size(); i++)
		cout << alfabetuldeintrare[i] << " ";

	cout << endl
		 << "    Alfabetul de iesire este : ";
	for (i = 0; i < alfabetuldeiesire.size(); i++)
		cout << alfabetuldeiesire[i] << " ";

	cout << endl
		 << "    Alfabetul stivei este : ";
	for (i = 0; i < alfabetulstivei.size(); i++)
		cout << alfabetulstivei[i] << " ";

	cout << endl
		 << "    Simbolul initial al stivei este : " << simbolulinitialalstivei;

	cout << endl
		 << endl
		 << "    Tranzitiile sunt : ";
	for (auto stare = tranzitiile.cbegin(); stare != tranzitiile.cend(); stare++)
		for (auto simbol = (stare->second).cbegin(); simbol != (stare->second).cend(); simbol++)
			for (auto varfstiva = (simbol->second).cbegin(); varfstiva != (simbol->second).cend(); varfstiva++)
				for (int i = 0; i < varfstiva->second.size(); i++)
				{
					cout << endl
						 << "    Din starea " << stare->first
						 << " cu simbolul " << simbol->first
						 << " si varful stivei " << varfstiva->first
						 << " se ajunge in starea " << varfstiva->second[i].staresosire
						 << " inlocuind varful stivei cu " << varfstiva->second[i].inlocuitorvarfstiva
						 << " si concatenand la sirul de iesire " << varfstiva->second[i].inlocuitorsimbol;
				}
	cout << endl
		 << endl;
}

void afisareCerinta()
{
	cout << endl
		 << endl;
	cout << "    Cerinta:" << endl
		 << "    Program care simuleaza functionarea unui translator stiva nedeterminist cu lambda-tranzitii." << endl
		 << "    Programul citeste elementele unui translator stiva nedeterminist cu lambda-tranzitii oarecare:" << endl
		 << "    	Starile" << endl
		 << "    	Starea initiala" << endl
		 << "    	Starile finale" << endl
		 << "    	Alfabetul de intrare" << endl
		 << "    	Alfabetul de iesire" << endl
		 << "    	Alfabetul stivei" << endl
		 << "    	Simbolul initial al stivei" << endl
		 << "    	Tranzitiile" << endl
		 << "    " << endl
		 << "    Programul permite citirea unui numar oarecare de siruri peste alfabetul de intrare al translatorului." << endl
		 << "    Pentru fiecare astfel de sir se afiseaza toate iesirile (sirurile peste alfabetul de iesire) corespunzatoare." << endl
		 << "    Pot exista 0,1 sau mai multe iesiri pentru acelasi sir de intrare." << endl
		 << "    " << endl
		 << "    Conventii:" << endl
		 << "    	Simbolul lambda este *." << endl
		 << "    	Alfabetul de intrare este inclus in { a, b, c, ..., z, 0, 1, 2, ..., 9 }." << endl
		 << "    	Alfabetul de iesire este inclus in { a, b, c, ..., z, 0, 1, 2, ..., 9 }." << endl
		 << "    	Translatorul se opreste:" << endl
		 << "    	Stiva este vida && Sirul de intrare este vid => Afisam sirul de iesire." << endl
		 << "    	Stiva este vida && Sirul de intrare NU este vid => NU afisam sirul de iesire." << endl
		 << "    	Stiva NU este vida && Sirul de intrare este vid => NU afisam sirul de iesire." << endl
		 << endl;
}

string afisareStiva(stack<string> st)
{
	string s;
	s = s + "[ ";
	while (!st.empty())
	{
		s = s + st.top() + " ";
		st.pop();
	}
	s = s + "]";
	return s;
}

void afisareArbore()
{
	cout << endl
		 << "    Arborele de recursivitate:" << endl;
	for (int i = 0; i < arbore.size(); i++)
		cout << "    " << arbore[i] << endl;
	cout << endl;
}

void drum(string stare, string input, stack<string> stiva, string output, int nivel)
{

	arbore.push_back("( " + to_string(nivel) + " ) Begin:  Stare = " + stare + ", Input = '" + input + "', Stiva = " + afisareStiva(stiva) + ", Output = '" + output + "' ");

	if (input == "")
	{
		if (stiva.empty())
		{
			// Daca sirul si stiva sunt vide, sirul translatat este valid si se salveaza in vectorul rezultatelor.
			rezultate.push_back(output);
			arbore.push_back("( " + to_string(nivel) + " ) End:    Stare = " + stare + ", Input = '" + input + "', Stiva = " + afisareStiva(stiva) + ", Output = '" + output + "' => Sir valid");
			return;
		}
		else
		{
			// Daca sirul este vid si stiva este inca nevida se pot parcurge doar lambda tranzitiile
			for (auto trc = tranzitiile[stare].cbegin(); trc != tranzitiile[stare].cend(); trc++)
			{
				// Parcurgem toate tranzitiile care pornesc din starea din primul argument al functiei
				string inputnou = input;
				string simboldepetranzitie = trc->first;

				if (simboldepetranzitie == "*")
				{
					// Lambda-tranzitiile = Inputul nu se va modifica, iar actiunile se fac doar asupra stivei
					for (auto trs = tranzitiile[stare][simboldepetranzitie].cbegin(); trs != tranzitiile[stare][simboldepetranzitie].cend(); trs++)
					{
						// Parcurgem toate lambda-tranzitiile care pornesc din starea pasului curent
						string simbolulstiveidepetranzitie = trs->first;
						for (int i = 0; i < tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie].size(); i++)
						{
							string stareanoua = tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie][i].staresosire;
							string inlocuitorpestiva = tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie][i].inlocuitorvarfstiva;
							string inlocuitorsimbol = tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie][i].inlocuitorsimbol;

							string outputnou = output;
							stack<string> stivanoua = stiva;

							if (simbolulstiveidepetranzitie == "*")
							{
								// Nu se citeste din stiva
								if (inlocuitorpestiva != "*")
								{
									// Push al stringului inlocuitor pe stiva daca acesta este diferit de lambda
									// Stiva retine pe fiecare nivel cate o litera
									for (int l = inlocuitorpestiva.size() - 1; l >= 0; l--)
									{
										string s(1, inlocuitorpestiva[l]);
										stivanoua.push(s);
									}
								}

								if (inlocuitorsimbol != "*")
								{
									// Daca simbolul inlocuitor este diferit de lambda, acesta se concateneaza la output
									outputnou = outputnou + inlocuitorsimbol;
								}

								// Se merge in noua configuratie
								drum(stareanoua, inputnou, stivanoua, outputnou, nivel + 1);
							}
							else
							{
								// Se citeste din stiva
								string varfstiva = stivanoua.top();			  // Aflam elementul din varful stivei
								if (simbolulstiveidepetranzitie == varfstiva) // Verificam ca este identic cu cel necesar tranzitiei
								{
									stivanoua.pop(); // Se elimina de pe stiva
									if (inlocuitorpestiva != "*")
									{
										// Push al stringului inlocuitor pe stiva daca acesta este diferit de lambda
										// Stiva retine pe fiecare nivel cate o litera
										for (int l = inlocuitorpestiva.size() - 1; l >= 0; l--)
										{
											string s(1, inlocuitorpestiva[l]);
											stivanoua.push(s);
										}
									}

									if (inlocuitorsimbol != "*")
									{
										// Daca simbolul inlocuitor este diferit de lambda, acesta se concateneaza la output
										outputnou = outputnou + inlocuitorsimbol;
									}

									// Se merge in noua configuratie
									drum(stareanoua, inputnou, stivanoua, outputnou, nivel + 1);
								}
							}
						}
					}
				}
			}
		}
	}
	else
	{
		if (!stiva.empty())
		{
			string simboldinsiruldeintrare(1, input[0]);
			for (auto trc = tranzitiile[stare].cbegin(); trc != tranzitiile[stare].cend(); trc++)
			{
				// Parcurgem toate tranzitiile care pornesc din starea din primul argument al functiei
				string inputnou = input;
				string simboldepetranzitie = trc->first;

				if (simboldepetranzitie == "*")
				{
					// Lambda-tranzitie = Inputul nu se va modifica, iar actiunile se fac doar asupra stivei
					for (auto trs = tranzitiile[stare][simboldepetranzitie].cbegin(); trs != tranzitiile[stare][simboldepetranzitie].cend(); trs++)
					{
						// Parcurgem toate lambda-tranzitiile care pornesc din starea pasului curent
						string simbolulstiveidepetranzitie = trs->first;
						for (int i = 0; i < tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie].size(); i++)
						{
							string stareanoua = tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie][i].staresosire;
							string inlocuitorpestiva = tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie][i].inlocuitorvarfstiva;
							string inlocuitorsimbol = tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie][i].inlocuitorsimbol;

							string outputnou = output;
							stack<string> stivanoua = stiva;

							if (simbolulstiveidepetranzitie == "*")
							{
								// Nu se citeste din stiva
								if (inlocuitorpestiva != "*")
								{
									// Push al stringului inlocuitor pe stiva daca acesta este diferit de lambda
									// Stiva retine pe fiecare nivel cate o litera
									for (int l = inlocuitorpestiva.size() - 1; l >= 0; l--)
									{
										string s(1, inlocuitorpestiva[l]);
										stivanoua.push(s);
									}
								}

								if (inlocuitorsimbol != "*")
								{
									// Daca simbolul inlocuitor este diferit de lambda, acesta se concateneaza la output
									outputnou = outputnou + inlocuitorsimbol;
								}

								// Se merge in noua configuratie
								drum(stareanoua, inputnou, stivanoua, outputnou, nivel + 1);
							}
							else
							{
								// Se citeste din stiva
								string varfstiva = stivanoua.top();			  // Aflam elementul din varful stivei
								if (simbolulstiveidepetranzitie == varfstiva) // Verificam ca este identic cu cel necesar tranzitiei
								{
									stivanoua.pop(); // Se elimina de pe stiva
									if (inlocuitorpestiva != "*")
									{
										// Push al stringului inlocuitor pe stiva daca acesta este diferit de lambda
										// Stiva retine pe fiecare nivel cate o litera
										for (int l = inlocuitorpestiva.size() - 1; l >= 0; l--)
										{
											string s(1, inlocuitorpestiva[l]);
											stivanoua.push(s);
										}
									}

									if (inlocuitorsimbol != "*")
									{
										// Daca simbolul inlocuitor este diferit de lambda, acesta se concateneaza la output
										outputnou = outputnou + inlocuitorsimbol;
									}

									// Se merge in noua configuratie
									drum(stareanoua, inputnou, stivanoua, outputnou, nivel + 1);
								}
							}
						}
					}
				}
				else
				{
					// Tranzitie = Inputul si stiva se vor modifica
					if (simboldepetranzitie == simboldinsiruldeintrare)
					{
						// Verificam ca litera citita din input este identica cu cel necesara tranzitiei
						inputnou.erase(inputnou.begin()); // Eliminam litera citita din input

						// Lambda-tranzitie = Inputul nu se va modifica, iar actiunile se fac doar asupra stivei
						for (auto trs = tranzitiile[stare][simboldepetranzitie].cbegin(); trs != tranzitiile[stare][simboldepetranzitie].cend(); trs++)
						{
							// Parcurgem toate lambda-tranzitiile care pornesc din starea pasului curent
							string simbolulstiveidepetranzitie = trs->first;
							for (int i = 0; i < tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie].size(); i++)
							{
								string stareanoua = tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie][i].staresosire;
								string inlocuitorpestiva = tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie][i].inlocuitorvarfstiva;
								string inlocuitorsimbol = tranzitiile[stare][simboldepetranzitie][simbolulstiveidepetranzitie][i].inlocuitorsimbol;

								string outputnou = output;
								stack<string> stivanoua = stiva;

								if (simbolulstiveidepetranzitie == "*")
								{
									// Nu se citeste din stiva
									if (inlocuitorpestiva != "*")
									{
										// Push al stringului inlocuitor pe stiva daca acesta este diferit de lambda
										// Stiva retine pe fiecare nivel cate o litera
										for (int l = inlocuitorpestiva.size() - 1; l >= 0; l--)
										{
											string s(1, inlocuitorpestiva[l]);
											stivanoua.push(s);
										}
									}

									if (inlocuitorsimbol != "*")
									{
										// Daca simbolul inlocuitor este diferit de lambda, acesta se concateneaza la output
										outputnou = outputnou + inlocuitorsimbol;
									}

									// Se merge in noua configuratie
									drum(stareanoua, inputnou, stivanoua, outputnou, nivel + 1);
								}
								else
								{
									// Se citeste din stiva
									string varfstiva = stivanoua.top();			  // Aflam elementul din varful stivei
									if (simbolulstiveidepetranzitie == varfstiva) // Verificam ca este identic cu cel necesar tranzitiei
									{
										stivanoua.pop(); // Se elimina de pe stiva
										if (inlocuitorpestiva != "*")
										{
											// Push al stringului inlocuitor pe stiva daca acesta este diferit de lambda
											// Stiva retine pe fiecare nivel cate o litera
											for (int l = inlocuitorpestiva.size() - 1; l >= 0; l--)
											{
												string s(1, inlocuitorpestiva[l]);
												stivanoua.push(s);
											}
										}

										if (inlocuitorsimbol != "*")
										{
											// Daca simbolul inlocuitor este diferit de lambda, acesta se concateneaza la output
											outputnou = outputnou + inlocuitorsimbol;
										}

										// Se merge in noua configuratie
										drum(stareanoua, inputnou, stivanoua, outputnou, nivel + 1);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	arbore.push_back("( " + to_string(nivel) + " ) End:    Stare = " + stare + ", Input = '" + input + "', Stiva = " + afisareStiva(stiva) + ", Output = '" + output + "' ");
}

bool verificasirintrare(string sirdeintrare)
{
	for (int li = 0; li < alfabetuldeintrare.size(); li++)
		sirdeintrare = remove_char(sirdeintrare, alfabetuldeintrare[li][0]);

	if (sirdeintrare == "")
		return true;
	else
		return false;
}

int main()
{
	afisareCerinta();

	if (citireDate() == 0)
	{
		cout << endl
			 << "    Press any key to exit ...";
		getchar();
		return 0;
	}

	afisareDate();

	string sirdeintrare;
	while (1)
	{
		cout << "    Sir de intrare : ";
		cin >> sirdeintrare;

		if (sirdeintrare == "-")
			break;

		if (sirdeintrare == "*")
			sirdeintrare = "";

		if (verificasirintrare(sirdeintrare))
		{
			drum(stareainitiala, sirdeintrare, stiva, "", 0);

			afisareArbore();
			arbore.clear();

			if (rezultate.size() == 0)
			{
				cout << "    Nu s-au gasit traduceri pentru sirul de intrare." << endl
					 << endl
					 << endl;
			}
			else
			{
				cout << "    Rezultate:" << endl;
				for (int i = 0; i < rezultate.size(); i++)
				{
					if (rezultate[i] == "")
						cout << "      ->  "
							 << "Sirul vid" << endl;
					else
						cout << "      ->  " << rezultate[i] << endl;
				}

				cout << endl
					 << endl;
			}

			rezultate.clear();
		}
		else
		{
			cout << "    !! Sirul de intrare nu respecta alfabetul de intrare. !!" << endl
				 << endl
				 << endl;
		}
	}

	getchar();
	cout << endl
		 << "    Press any key to exit ...";
	getchar();
	return 0;
}