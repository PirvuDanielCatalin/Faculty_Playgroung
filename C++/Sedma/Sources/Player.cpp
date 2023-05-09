#include "../Headers/Player.h"

Player::Player()
{
	this->score = 0;
}

int Player::getScore()
{
	return this->score;
}

vector<Card *> Player::getHand()
{
	return this->hand;
}

void Player::AddScore(int points)
{
	this->score = this->score + points;
}

void Player::Create(Deck &obj)
{
	srand(time(NULL));
	int nr = obj.size;
	if (nr == 32) //For
		for (int i = 0; i < 4; i++)
		{
			int r = rand() % nr;
			while (obj.used[r] == 1)
				r = rand() % nr;

			if (obj[r]->value == "7")
				hand.push_back(new CutCard(obj[r]->value, obj[r]->suit));
			else if (obj[r]->value == "A" || obj[r]->value == "10")
				hand.push_back(new PointCard(obj[r]->value, obj[r]->suit));
			else
				hand.push_back(new Card(obj[r]->value, obj[r]->suit));

			obj.used[r] = 1;
		}
	else
		for (int i = 0; i < 4; i++)
		{
			int r = rand() % nr;
			while (obj.used[r] == 1)
				r = rand() % nr;

			if (obj[r]->value == "7" || obj[r]->value == "8")
				hand.push_back(new CutCard(obj[r]->value, obj[r]->suit));
			else if (obj[r]->value == "A" || obj[r]->value == "10")
				hand.push_back(new PointCard(obj[r]->value, obj[r]->suit));
			else
				hand.push_back(new Card(obj[r]->value, obj[r]->suit));
			obj.used[r] = 1;
		}
}

void Player::Erase(int i)
{
	this->hand[i] = NULL;
}

void Player::Complete(Deck &ob, int nr)
{
	srand(time(NULL));
	int size = ob.size;
	int j = 0;
	if (size == 32)
	{
		for (int i = 0; i < 4; i++)
			if (hand[i] == NULL && j < nr)
			{
				j++;
				int r = rand() % size;
				while (ob.used[r] == 1)
					r = rand() % size;

				if (ob[r]->value == "7")
					hand[i] = new CutCard(ob[r]->value, ob[r]->suit);
				else if (ob[r]->value == "A" || ob[r]->value == "10")
					hand[i] = new PointCard(ob[r]->value, ob[r]->suit);
				else
					hand[i] = new Card(ob[r]->value, ob[r]->suit);
				ob.used[r] = 1;
			}
	}
	else
	{
		for (int i = 0; i < 4; i++)
			if (hand[i] == NULL && j < nr)
			{
				j++;
				int r = rand() % size;
				while (ob.used[r] == 1)
					r = rand() % size;

				if (ob[r]->value == "7" || ob[r]->value == "8")
					hand[i] = new CutCard(ob[r]->value, ob[r]->suit);
				else if (ob[r]->value == "A" || ob[r]->value == "10")
					hand[i] = new PointCard(ob[r]->value, ob[r]->suit);
				else
					hand[i] = new Card(ob[r]->value, ob[r]->suit);
				ob.used[r] = 1;
			}
	}
}

int Player::Valide(string a, string b)
{
	for (int i = 0; i < 4; i++)
		if (hand[i] != NULL && hand[i]->value == a && hand[i]->suit == b)
			return i;
	return -1;
}

bool Player::CanContinue(string downValue)
{
	for (int i = 0; i < 4; i++)
		if (hand[i] != NULL && (hand[i]->value == "7" || hand[i]->value == downValue))
			return true;
	return false;
}

ostream &operator<<(ostream &os, const Player &player)
{
	for (int i = 0; i < 4; i++)
		if (player.hand[i] != NULL)
			os << *player.hand[i];
	cout << endl;
	return os;
}

int Player::Remain()
{
	int s = 0;
	for (int i = 0; i < 4; i++)
		if (hand[i] != NULL)
			s++;
	return s;
}

Player::~Player()
{
}
