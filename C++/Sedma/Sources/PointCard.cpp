#include "../Headers/PointCard.h"

PointCard::PointCard(string value = "0", string suit = "0") : Card(value, suit)
{
	this->point = 1;
}

void PointCard::playCard(Card downcard, int player, int &leader, int &points)
{
	points++;
	if (downcard.getValue() == this->value)
		leader = player;
}

PointCard::~PointCard()
{
}
