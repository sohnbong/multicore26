#include <omp.h>
#include <stdio.h>
#include <stdlib.h>

int fib(int n) {
  int x, y;

  if (n < 2)
    return n;

  #pragma omp task shared(x)
  x = fib(n-1);

  #pragma omp task shared(y)
  y = fib(n-2);

  #pragma omp taskwait

  return x+y;
}

int main(int argc, char* argv[])
{
  int result=0;
  double start_time = omp_get_wtime( );
  int N,t;

  if (argc==3) {
    N=atoi(argv[1]);
    t=atoi(argv[2]);
  } else {
    N=30;
    t=8;
  }
  
  #pragma omp parallel shared(result) num_threads(t)
  {
    #pragma omp single
    result = fib(N);
  }
  double end_time = omp_get_wtime( );

  printf("a.out fib_number #_of_threads\n");
  printf("fib(%d)=%d\n",N,result);
  printf("# of threads: %d, time elapsed: %lfs\n",t,end_time-start_time);
}
