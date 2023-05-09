#include "Headers/Player.h"

int main()
{
	cout << "\n\n\n";
	cout << "\n        //------------------------------------------------------------------\\";
	cout << "\n";
	cout << "\n                               Sedma Playing Cards Game";
	cout << "\n";
	cout << "\n             		The game starts directly with two players!";
	cout << "\n             		If you want to force quit, press Ctrl + C";
	cout << "\n";
	cout << "\n        \\------------------------------------------------------------------//";
	cout << "\n";

	Deck TwoPlayers;
	Player P1, P2;
	P1.Create(TwoPlayers);
	P2.Create(TwoPlayers);

	vector<Player> Players;
	Players.push_back(P1);
	Players.push_back(P2);

	Card downCard;
	int TempWinner = 0;
	int TempScore = 0;
	string value, suit;
	int position;
	int rounds = 0;

	while (TwoPlayers.getUsed() != TwoPlayers.getSize())
	{
		int StartPlayer = TempWinner;
		if (TempWinner == 0)
		{
			cout << "\n        //------------------------------------------------------------------\\";
			cout << "\n";
			cout << "\n                                   ~ Player 1 ~";
			cout << "\n";
			cout << "\n        " << Players[0];
			cout << "\n";
			cout << "\n          Card:";
			cout << "\n          + Value: ";
			cin >> value;
			cout << "          + Suit: ";
			cin >> suit;

			position = Players[0].Valide(value, suit);
			while (position == -1)
			{
				cout << "\n          Card:";
				cout << "\n          + Value: ";
				cin >> value;
				cout << "          + Suit: ";
				cin >> suit;
				position = Players[0].Valide(value, suit);
			}

			Players[0].getHand()[position]->playCard(downCard, 0, TempWinner, TempScore);

			downCard = *Players[0].getHand()[position];

			Players[0].Erase(position);

			cout << "\n";
			cout << "\n";
			cout << "                             Down Card : " << downCard;
			cout << "\n";
			cout << "\n";
			cout << "\n                                   ~ Player 2 ~";
			cout << "\n";
			cout << "\n        " << Players[1];
			cout << "\n";
			cout << "\n          Card:";
			cout << "\n          + Value: ";
			cin >> value;
			cout << "          + Suit: ";
			cin >> suit;

			position = Players[1].Valide(value, suit);
			while (position == -1)
			{
				cout << "\n          Card:";
				cout << "\n          + Value: ";
				cin >> value;
				cout << "          + Suit: ";
				cin >> suit;
				position = Players[1].Valide(value, suit);
			}

			Players[1].getHand()[position]->playCard(downCard, 1, TempWinner, TempScore);

			Players[1].Erase(position);

			cout << "\n        \\------------------------------------------------------------------//";
			cout << "\n\n";
		}
		else
		{
			cout << "\n        //------------------------------------------------------------------\\";
			cout << "\n";
			cout << "\n                                   ~ Player 2 ~";
			cout << "\n";
			cout << "\n        " << Players[1];
			cout << "\n";
			cout << "\n          Card:";
			cout << "\n          + Value: ";
			cin >> value;
			cout << "          + Suit: ";
			cin >> suit;

			position = Players[1].Valide(value, suit);
			while (position == -1)
			{
				cout << "\n          Card:";
				cout << "\n          + Value: ";
				cin >> value;
				cout << "          + Suit: ";
				cin >> suit;
				position = Players[1].Valide(value, suit);
			}

			Players[1].getHand()[position]->playCard(downCard, 1, TempWinner, TempScore);

			downCard = *Players[1].getHand()[position];

			Players[1].Erase(position);

			cout << "\n";
			cout << "\n";
			cout << "                             Down Card : " << downCard;
			cout << "\n";
			cout << "\n";
			cout << "\n                                   ~ Player 1 ~";
			cout << "\n";
			cout << "\n        " << Players[0];
			cout << "\n";
			cout << "\n          Card:";
			cout << "\n          + Value: ";
			cin >> value;
			cout << "          + Suit: ";
			cin >> suit;

			position = Players[0].Valide(value, suit);
			while (position == -1)
			{
				cout << "\n          Card:";
				cout << "\n          + Value: ";
				cin >> value;
				cout << "          + Suit: ";
				cin >> suit;
				position = Players[0].Valide(value, suit);
			}

			Players[0].getHand()[position]->playCard(downCard, 0, TempWinner, TempScore);

			Players[0].Erase(position);

			cout << "\n        \\------------------------------------------------------------------//";
			cout << "\n\n";
		}

		rounds++;

		string answer = "No";
		if (StartPlayer != TempWinner && Players[1 - TempWinner].CanContinue(downCard.getValue()))
		{
			cout << "\n        Player " << 2 - TempWinner << "'s cards: " << Players[1 - TempWinner];
			cout << "\n";
			cout << "\n        Player " << 2 - TempWinner << ", do you continue ? < Yes / No > ";
			cin >> answer;
		}

		if (answer == "Yes")
		{
			TempWinner = 1 - TempWinner;
		}
		else
		{
			Players[TempWinner].AddScore(TempScore);

			Players[0].Complete(TwoPlayers, rounds);
			Players[1].Complete(TwoPlayers, rounds);

			TempScore = 0;
			downCard.set("0", "0");
			rounds = 0;
		}
	}

	while (Players[0].Remain() && Players[1].Remain())
	{
		int StartPlayer = TempWinner;
		if (TempWinner == 0)
		{
			cout << "\n        //------------------------------------------------------------------\\";
			cout << "\n";
			cout << "\n                                   ~ Player 1 ~";
			cout << "\n";
			cout << "\n        " << Players[0];
			cout << "\n";
			cout << "\n          Card:";
			cout << "\n          + Value: ";
			cin >> value;
			cout << "          + Suit: ";
			cin >> suit;

			position = Players[0].Valide(value, suit);
			while (position == -1)
			{
				cout << "\n          Card:";
				cout << "\n          + Value: ";
				cin >> value;
				cout << "          + Suit: ";
				cin >> suit;
				position = Players[0].Valide(value, suit);
			}

			Players[0].getHand()[position]->playCard(downCard, 0, TempWinner, TempScore);

			downCard = *Players[0].getHand()[position];

			Players[0].Erase(position);

			cout << "\n";
			cout << "\n";
			cout << "                             Down Card : " << downCard;
			cout << "\n";
			cout << "\n";
			cout << "\n                                   ~ Player 2 ~";
			cout << "\n";
			cout << "\n        " << Players[1];
			cout << "\n";
			cout << "\n          Card:";
			cout << "\n          + Value: ";
			cin >> value;
			cout << "          + Suit: ";
			cin >> suit;

			position = Players[1].Valide(value, suit);
			while (position == -1)
			{
				cout << "\n          Card:";
				cout << "\n          + Value: ";
				cin >> value;
				cout << "          + Suit: ";
				cin >> suit;
				position = Players[1].Valide(value, suit);
			}

			Players[1].getHand()[position]->playCard(downCard, 1, TempWinner, TempScore);

			Players[1].Erase(position);

			cout << "\n        \\------------------------------------------------------------------//";
			cout << "\n\n";
		}
		else
		{
			cout << "\n        //------------------------------------------------------------------\\";
			cout << "\n";
			cout << "\n                                   ~ Player 2 ~";
			cout << "\n";
			cout << "\n        " << Players[1];
			cout << "\n";
			cout << "\n          Card:";
			cout << "\n          + Value: ";
			cin >> value;
			cout << "          + Suit: ";
			cin >> suit;

			position = Players[1].Valide(value, suit);
			while (position == -1)
			{
				cout << "\n          Card:";
				cout << "\n          + Value: ";
				cin >> value;
				cout << "          + Suit: ";
				cin >> suit;
				position = Players[1].Valide(value, suit);
			}

			Players[1].getHand()[position]->playCard(downCard, 1, TempWinner, TempScore);

			downCard = *Players[1].getHand()[position];

			Players[1].Erase(position);

			cout << "\n";
			cout << "\n";
			cout << "                             Down Card : " << downCard;
			cout << "\n";
			cout << "\n";
			cout << "\n                                   ~ Player 1 ~";
			cout << "\n";
			cout << "\n        " << Players[0];
			cout << "\n";
			cout << "\n          Card:";
			cout << "\n          + Value: ";
			cin >> value;
			cout << "          + Suit: ";
			cin >> suit;

			position = Players[0].Valide(value, suit);
			while (position == -1)
			{
				cout << "\n          Card:";
				cout << "\n          + Value: ";
				cin >> value;
				cout << "          + Suit: ";
				cin >> suit;
				position = Players[0].Valide(value, suit);
			}

			Players[0].getHand()[position]->playCard(downCard, 0, TempWinner, TempScore);

			Players[0].Erase(position);

			cout << "\n        \\------------------------------------------------------------------//";
			cout << "\n\n";
		}

		rounds++;

		string answer = "No";
		if (StartPlayer != TempWinner && Players[1 - TempWinner].CanContinue(downCard.getValue()))
		{
			cout << "\n        Player " << 2 - TempWinner << "'s cards: " << Players[1 - TempWinner];
			cout << "\n";
			cout << "\n        Player " << 2 - TempWinner << ", do you continue ? < Yes / No > ";
			cin >> answer;
		}

		if (answer == "Yes")
		{
			TempWinner = 1 - TempWinner;
		}
		else
		{
			Players[TempWinner].AddScore(TempScore);
			TempScore = 0;
			downCard.set("0", "0");
			rounds = 0;
		}
	}

	if (Players[0].getScore() > Players[1].getScore())
		cout << "\n        !!! The winner is Player 1 with " << Players[0].getScore() << " points agains " << Players[1].getScore() << " points of Player 2 !!!";
	else if (Players[0].getScore() < Players[1].getScore())
		cout << "\n        !!! The winner is Player 2 with " << Players[1].getScore() << " points agains " << Players[0].getScore() << " points of Player 1 !!!";
	else
		cout << "\n        !!! It's a draw. Both players have " << Players[1].getScore() << " points !!!";

	getchar();
	cout << "\n";
	cout << "\n        Press any key to exit ...";
	getchar();

	return 0;
}
