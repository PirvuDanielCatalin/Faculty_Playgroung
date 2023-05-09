#pragma once

#ifndef CUTCARD_H
#define CUTCARD_H

#include "Card.h"

class CutCard : public Card
{
  public:
	CutCard(string, string);

	void playCard(Card, int, int &, int &);

	~CutCard();
};
#endif