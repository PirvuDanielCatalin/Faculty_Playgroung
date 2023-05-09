#include "../Headers/Card.h"

Card::Card(string _value, string _suit)
{
	this->value = _value;
	this->suit = _suit;
	this->point = 0;
}

Card &Card::operator=(Card &aux)
{
	this->value = aux.value;
	this->suit = aux.suit;
	this->point = aux.point;
	return aux;
}

ostream &operator<<(ostream &os, const Card &card)
{
	os << "| " << card.value << " of " << card.suit << " | ";
	return os;
}

int Card::getPoint()
{
	return this->point;
}

string Card::getValue()
{
	return this->value;
}

void Card::set(string _value, string _suit)
{
	this->value = _value;
	this->suit = _suit;
}

void Card::playCard(Card downcard, int player, int &leader, int &points)
{
	if (downcard.getValue() == this->value)
		leader = player;
}