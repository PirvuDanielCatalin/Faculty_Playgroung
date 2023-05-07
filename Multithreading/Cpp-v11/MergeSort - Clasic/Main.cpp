#include <iostream>
#include <cstdlib>
#include <ctime>
#include <thread>
#include <mutex>
#include <vector>
#include <algorithm>
using namespace std;

vector<int> arr;

void merge_sort(vector<int>::iterator begin, vector<int>::iterator end) {
	if (end > begin + 1) {
		auto m = begin + ((end - begin) / 2);

		thread tleft(merge_sort, begin, m);
		thread tright(merge_sort, m, end);
		tleft.join();
		tright.join();

		vector<int> temp(end - begin);
		merge(begin, m, m, end, temp.begin());
		copy(temp.begin(), temp.end(), begin);
	}
}

int main()
{
	int size;
	cout << "Size:";
	cin >> size;

	srand((unsigned)time(0));

	for (int i = 0; i < size; i++)
	{
		arr.push_back((rand() % 100) + 1);
		cout << arr[i] << " ";
	}

	cout << endl << endl << "Sorting ..." << endl;
	merge_sort(arr.begin(), arr.end());

	cout << endl;

	for (int i = 0; i < size; i++)
		cout << arr[i] << " ";

	cout << endl << endl;
	getchar();
	cout << "Press any key to exit ...";
	getchar();
	return 0;
}