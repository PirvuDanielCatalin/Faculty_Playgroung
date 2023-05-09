#pragma once

#ifndef CARD_H
#define CARD_H

#include <iostream>
#include <string>
using namespace std;

class Card
{
	friend class Deck;
	friend class Player;

protected:
	string value;
	string suit;
	int point;

public:
	Card(string = "0", string = "0");

	Card &operator=(Card &);

	friend ostream &operator<<(ostream &, const Card &);

	int getPoint();

	string getValue();

	void set(string, string);

	virtual void playCard(Card, int, int &, int &);
};
#endif