#include "../Headers/CutCard.h"

CutCard::CutCard(string value = "0", string suit = "0") : Card(value, suit)
{
}

void CutCard::playCard(Card downcard, int player, int &leader, int &points)
{
	if (downcard.getValue() != "0")
		leader = player;
}

CutCard::~CutCard()
{
}
