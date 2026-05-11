#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

#define NUM_BINS 10
#define NUM_ELEMENTS (1024*1024*1024)

void genData(float* _data)
{
	for (int i = 0; i < NUM_ELEMENTS; i++) 
		_data[i] = rand()%NUM_BINS + (rand()%100)/100.0f;
}

int main()
{
	float* Data=(float*)malloc(sizeof(float)*NUM_ELEMENTS);
	genData(Data);

	// insert your code for computing histogram.

	free(Data);
	return 0;
}
