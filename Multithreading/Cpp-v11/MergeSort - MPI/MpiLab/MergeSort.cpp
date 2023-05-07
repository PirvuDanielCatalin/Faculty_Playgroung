#include <iostream>
#include <stdio.h>
#include <string>
#include <ctime> 
#include <algorithm>
#include <math.h>
#include <vector>

#include "mpi.h"

using namespace std;

#pragma comment(lib, "msmpi.lib")

int get_tag(int source, int destination)
{
	int num_of_digits = 0;
	int temp = destination;

	while (temp)
	{
		num_of_digits++;
		temp /= 10;
	}

	return source * pow(10, num_of_digits + 1) + destination;
}

void convert_string_to_array(string str, vector<int>& v)
{
	size_t prev = 0, pos = 0;
	do
	{
		pos = str.find(", ", prev);
		if (pos == string::npos)
			pos = str.length();
		string token = str.substr(prev, pos - prev);
		if (!token.empty())
			v.push_back(stoi(token));
		prev = pos + 2;
	} while (pos < str.length() && prev < str.length());
}

void merge(int* begin1, int* end1, int* begin2, int* end2, int* merged)
{
	int* curr1 = begin1;
	int* curr2 = begin2;
	while (curr1 < end1 || curr2 < end2)
	{
		if (curr2 >= end2 || (curr1 < end1 && *curr1 < *curr2))
		{
			*merged = *curr1;
			++curr1;
		}
		else
		{
			*merged = *curr2;
			++curr2;
		}
		++merged;
	}
}

void merge_sort_rec(int rank, vector<int>& buffer, int& max_rank_arg)
{
	vector<int> tmp;
	tmp.resize(buffer.size());
	copy(buffer.data(), buffer.data() + buffer.size(), tmp.data());

	printf("  Tmp Buffer: [ ");
	for (int i = 0; i < tmp.size() - 1; i++)
		printf("%d ; ", tmp[i]);
	printf("%d ] \n", tmp[tmp.size() - 1]);

	int half_size = tmp.size() / 2;

	int left_child = 2 * rank + 1;
	int right_child = 2 * rank + 2;

	printf("\n  Half size: %d , Left child rank: %d, Right child rank: %d\n\n", half_size, left_child, right_child);

	if (left_child <= max_rank_arg)
	{
		printf("  Sending half data to left child ( From rank %d to rank %d )\n", rank, left_child);
		int tag = get_tag(rank, left_child);
		MPI_Send(tmp.data(), half_size, MPI_INT, left_child, tag, MPI_COMM_WORLD);
		printf("  Left child received the data\n\n");
	}

	if (right_child <= max_rank_arg)
	{
		printf("  Sending half data to right child ( From rank %d to rank %d )\n", rank, right_child);
		int tag = get_tag(rank, right_child);
		MPI_Send(tmp.data() + half_size, tmp.size() - half_size, MPI_INT, right_child, tag, MPI_COMM_WORLD);
		printf("  Right child received the data\n\n");
	}

	MPI_Status status;
	if (left_child <= max_rank_arg)
	{
		printf("  Waiting for left child to send back processed data ( From rank %d to rank %d )\n", left_child, rank);
		int tag = get_tag(left_child, rank);
		MPI_Recv(tmp.data(), half_size, MPI_INT, left_child, tag, MPI_COMM_WORLD, &status);
		printf("  Parent received data from left child\n\n");
	}

	if (right_child <= max_rank_arg)
	{
		printf("  Waiting for right child to send back processed data ( From rank %d to rank %d )\n", right_child, rank);
		int tag = get_tag(right_child, rank);
		MPI_Recv(tmp.data() + half_size, tmp.size() - half_size, MPI_INT, right_child, tag, MPI_COMM_WORLD, &status);
		printf("  Parent received data from right child\n\n");
	}

	printf("  Merging these two halfs processed by children and save it back in buffer\n");
	vector<int> result(buffer.size());
	merge(tmp.data(), tmp.data() + half_size, tmp.data() + half_size, tmp.data() + tmp.size(), result.data());
	copy(result.data(), result.data() + result.size(), buffer.data());
	printf("  Finished merging\n\n");
}

void merge_sort_rec_worker(int rank, int& max_rank_arg, vector<int>& dormant)
{
	int parent = (rank - 1) / 2;
	int received_size;

	printf("\n------------------------------------------------------------------------------------------------------------\n");
	printf("  Parent of this process: %d", parent);
	printf("\n------------------------------------------------------------------------------------------------------------\n\n");

	if (find(dormant.begin(), dormant.end(), rank) != dormant.end())
	{
		printf("  This process has nothing to do!\n\n");
		return;
	}

	printf("  Probe to catch a send from parent\n\n");

	MPI_Status stats;
	int tag = get_tag(parent, rank);

	MPI_Probe(parent, tag, MPI_COMM_WORLD, &stats);
	MPI_Get_count(&stats, MPI_INT, &received_size);

	vector<int> tmp;
	tmp.resize(received_size);

	MPI_Status status;

	printf("  Waiting for parent to send data ( From rank %d to rank %d )\n", parent, rank);
	MPI_Recv(tmp.data(), received_size, MPI_INT, parent, tag, MPI_COMM_WORLD, &status);
	printf("  Child received data from parent\n\n");

	if (received_size == 1)
	{
		printf("  Sending processed data to parent ( From rank %d to rank %d )\n", rank, parent);
		int tag = get_tag(rank, parent);
		MPI_Send(tmp.data(), received_size, MPI_INT, parent, tag, MPI_COMM_WORLD);
		printf("  Parent received the data\n\n");

		printf("  Tmp Buffer: [ ");
		for (int i = 0; i < tmp.size() - 1; i++)
			printf("%d ; ", tmp[i]);
		printf("%d ] \n\n", tmp[tmp.size() - 1]);
	}
	else if (received_size == 2)
	{
		if (tmp[0] > tmp[1])
			swap(tmp[0], tmp[1]);

		printf("  Sending processed data to parent ( From rank %d to rank %d )\n", rank, parent);
		int tag = get_tag(rank, parent);
		MPI_Send(tmp.data(), received_size, MPI_INT, parent, tag, MPI_COMM_WORLD);
		printf("  Parent received the data\n\n");

		printf("  Tmp Buffer: [ ");
		for (int i = 0; i < tmp.size() - 1; i++)
			printf("%d ; ", tmp[i]);
		printf("%d ] \n\n", tmp[tmp.size() - 1]);
	}
	else
	{
		merge_sort_rec(rank, tmp, max_rank_arg);

		printf("  Sending processed data to parent ( From rank %d to rank %d )\n", rank, parent);
		int tag = get_tag(rank, parent);
		MPI_Send(tmp.data(), tmp.size(), MPI_INT, parent, tag, MPI_COMM_WORLD);
		printf("  Parent received the data\n\n");

		printf("  Tmp Buffer: [ ");
		for (int i = 0; i < tmp.size() - 1; i++)
			printf("%d ; ", tmp[i]);
		printf("%d ] \n\n", tmp[tmp.size() - 1]);
	}
}

int main(int argc, char* argv[])
{
	if (argc != 4) {
		printf("\nInsufficient number of arguments!\n");
		return -1;
	}

	int numprocs, rank, rc;
	rc = MPI_Init(&argc, &argv);
	if (rc != MPI_SUCCESS)
	{
		printf("  Error starting MPI program. Terminating.\n");
		MPI_Abort(MPI_COMM_WORLD, rc);
	}

	MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	printf("############################################################################################################\n\n");
	printf("					I'm rank %d\n", rank);

	int ign;
	vector<int> buffer;
	int nr_words_arg;

	int max_rank_arg;
	vector<int> dormant;

	if (rank == 0)
	{
		ign = sscanf(argv[2], "%d", &nr_words_arg);
		buffer.resize(nr_words_arg);
		srand(time(0));
		for (int i = 0; i < buffer.size(); i++)
			buffer[i] = rand() % 123456;

		printf("\n------------------------------------------------------------------------------------------------------------\n");
		printf("  Buffer: [ ");
		for (int i = 0; i < buffer.size() - 1; i++)
			printf("%d ; ", buffer[i]);
		printf("%d ] ", buffer[buffer.size() - 1]);

		ign = sscanf(argv[1], "%d", &max_rank_arg);
		convert_string_to_array(string(argv[3]), dormant);

		printf("\n------------------------------------------------------------------------------------------------------------\n");
		printf("  Program arguments:\n");
		printf("   - Number of words: %d\n", nr_words_arg);
		printf("   - Number of processes: %d\n", numprocs);
		printf("   - Leaves: [ ");
		for (int i = 0; i < dormant.size() - 1; i++)
			printf("%d ; ", dormant[i]);
		printf("%d ] ", dormant[dormant.size() - 1]);
		printf("\n------------------------------------------------------------------------------------------------------------\n\n");
	}
	else
	{
		ign = sscanf(argv[1], "%d", &max_rank_arg);
		convert_string_to_array(string(argv[3]), dormant);
	}

	MPI_Barrier(MPI_COMM_WORLD);

	if (rank == 0)
	{
		merge_sort_rec(rank, buffer, max_rank_arg);
	}
	else
	{
		merge_sort_rec_worker(rank, max_rank_arg, dormant);
		printf("############################################################################################################\n\n\n");
	}

	MPI_Barrier(MPI_COMM_WORLD);

	if (rank == 0)
	{
		printf("  ==>  Sorted array: [ ");
		for (int i = 0; i < buffer.size() - 1; i++)
			printf("%d ; ", buffer[i]);
		printf("%d ]  <==  \n", buffer[buffer.size() - 1]);
		printf("\n############################################################################################################\n\n\n");
	}

	MPI_Finalize();
}