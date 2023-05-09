#pragma once

#ifndef POINTCARD_H
#define POINTCARD_H

#include "Card.h"

class PointCard : public Card
{
  public:
	PointCard(string, string);

	void playCard(Card, int, int &, int &);

	~PointCard();
};
#endif