#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

#define NUM_ELEMENTS (1024*1024*1024)


void genData(float* _data, int numbins)
{
	for (int i = 0; i < NUM_ELEMENTS; i++) 
		_data[i] = rand()%numbins + (rand()%100)/100.0f;
}

int main(int argc, char* argv[])
{
	int  num_bins;
	if (argc==2) num_bins=atoi(argv[1]);
	else num_bins=10;

	float* Data=(float*)malloc(sizeof(float)*NUM_ELEMENTS);
	genData(Data,num_bins);

	// test performance for {1,2,4,8,12,16} threads

	free(Data);
	return 0;
}
