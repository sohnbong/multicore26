import java.util.concurrent.*;
import java.util.*;

public class ArraySumWithExecutor {
    // Task to sum a portion of the array
    static class SumTask implements Callable<Integer> {
        private final int[] array;
        private final int start;
        private final int end;

        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer call() {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] array = new int[1000];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1; // Filling array with 1 to 1000
        }

        int numThreads = 4;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Integer>> futures = new ArrayList<>();

        int chunkSize = (int) Math.ceil(array.length / (double) numThreads);

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(array.length, start + chunkSize);
            futures.add(executor.submit(new SumTask(array, start, end)));
        }

        int totalSum = 0;
        for (Future<Integer> future : futures) {
            totalSum += future.get();
        }

        executor.shutdown();

        System.out.println("Total Sum: " + totalSum);
    }
}

