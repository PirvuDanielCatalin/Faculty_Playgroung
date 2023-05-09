#pragma once

#ifndef DECK_H
#define DECK_H

#include "Card.h"
#include "PointCard.h"
#include "CutCard.h"
#include <vector>

class Deck
{
	friend class Player;

	vector<Card *> cards;
	int size;
	vector<int> used;

  public:
	Deck(int = 2);

	Card *operator[](int);
	int getSize();
	int getUsed();

	friend ostream &operator<<(ostream &, const Deck &);

	Deck(const Deck &);
	Deck &operator=(const Deck &);

	~Deck();
};
#endif