import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ArraySum {
    private static class SumTask extends RecursiveTask<Long> {
        private final int[] array;
        private final int start, end;
        private static final int THRESHOLD = 1000;

        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            int length = end - start;
            if (length <= THRESHOLD) {
                // Compute directly
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += array[i];
                }
                return sum;
            } else {
                // Split task
                int mid = start + length / 2;
                SumTask leftTask = new SumTask(array, start, mid);
                SumTask rightTask = new SumTask(array, mid, end);

                // Fork subtasks
                leftTask.fork();
                long rightResult = rightTask.compute();
                long leftResult = leftTask.join();

                return leftResult + rightResult;
            }
        }
    }

    public static void main(String[] args) {
        // Sample array
        int[] array = new int[10_000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(array, 0, array.length);
        long result = pool.invoke(task);

        System.out.println("Sum of array elements: " + result);
    }
}
