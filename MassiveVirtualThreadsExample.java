import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MassiveVirtualThreadsExample {
    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

            for (int i = 0; i < 100_000; i++) {
                int taskId = i;

                executor.submit(() -> {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    if (taskId % 10000 == 0) {
                        System.out.println("Processed: " + taskId);
                    }
                });
            }
        }
    }
}
