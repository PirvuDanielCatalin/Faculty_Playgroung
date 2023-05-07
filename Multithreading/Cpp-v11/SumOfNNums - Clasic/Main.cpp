#include <iostream>
#include <cstdlib>
#include <ctime>
#include <thread>
#include <mutex>
#include <vector>
using namespace std;

int totalSum;
vector<int> arr;
mutex mtx;

void sum_part_arr(vector<int> arr, int ll, int rl)
{
	for (int i = ll; i <= rl; i++)
	{
		mtx.lock();
		totalSum += arr[i];
		mtx.unlock();
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

	cout << endl;

	int no_of_processes;
	cout << "Number of processes:";
	cin >> no_of_processes;

	vector<thread> threads;

	for (int i = 0; i < size; i += int(size / no_of_processes))
	{
		int ll = i;
		int rl = i + int(size / no_of_processes) - 1;
		if (rl > size)
		{
			rl = size - 1;
		}

		thread ted(sum_part_arr, arr, ll, rl);
		threads.push_back(move(ted));
	}

	cout << threads.size() << endl;

	for (thread &thread : threads)
	{
		if (thread.joinable())
			thread.join();
	}

	cout << "Total sum is: " << totalSum << endl;

	cout << endl;
	getchar();
	cout << "Press any key to exit ...";
	getchar();
	return 0;
}