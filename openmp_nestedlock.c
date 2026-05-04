#include <stdio.h>
#include <omp.h>

int main() {
    omp_nest_lock_t nlock;

    omp_init_nest_lock(&nlock);

    #pragma omp parallel num_threads(2)
    {
        int tid = omp_get_thread_num();

        omp_set_nest_lock(&nlock);
        printf("Thread %d: first lock\n", tid);

        // Same thread acquires again
        omp_set_nest_lock(&nlock);
        printf("Thread %d: second lock\n", tid);

        // Must release the same number of times
        omp_unset_nest_lock(&nlock);
        omp_unset_nest_lock(&nlock);
    }

    omp_destroy_nest_lock(&nlock);
    return 0;
}
