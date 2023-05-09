#pragma once

#ifndef PLAYER_H
#define PLAYER_H

#include "Deck.h"
#include <stdlib.h>
#include <time.h>

class Player
{
	vector<Card *> hand;
	int score;

  public:
	Player();
	int getScore();
	vector<Card *> getHand();

	void AddScore(int);
	void Create(Deck &);
	void Erase(int);

	void Complete(Deck &, int);
	int Valide(string, string);
	bool CanContinue(string);

	friend ostream &operator<<(ostream &, const Player &);

	int Remain();

	~Player();
};
#endif