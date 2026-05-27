#include <iostream>
#include <vector>
#include <algorithm>
#include <ctime>
#include <chrono>
#include <thrust/device_vector.h>
#include <thrust/sort.h>

int main()
{
	std::vector<int> vec(100000000);

	// fill vector with random numbers
	std::srand(unsigned(std::time(nullptr)));
	std::generate(vec.begin(), vec.end(), std::rand);
  thrust::device_vector<int> dev_vec(vec.begin(), vec.end());

	auto start_time = std::chrono::high_resolution_clock::now();
  std::sort(vec.begin(), vec.end());
	auto end_time = std::chrono::high_resolution_clock::now();
	auto time_diff = end_time - start_time;
	std::cout << "sorting time: " <<
		time_diff / std::chrono::milliseconds(1) << "ms (STL single thread sort)\n";

  std::cout << vec[0] << " " << vec[1] << " " << vec[2] << "\n";


	start_time = std::chrono::high_resolution_clock::now();
 	thrust::sort(dev_vec.begin(), dev_vec.end());
	end_time = std::chrono::high_resolution_clock::now();
	time_diff = end_time - start_time;
	std::cout << "sorting time: " <<
		time_diff / std::chrono::milliseconds(1) << "ms (thrust GPU sort)\n";

  std::cout << dev_vec[0] << " " << dev_vec[1] << " " << dev_vec[2] << "\n";

	return 0;
}
