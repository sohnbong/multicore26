import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class WorkerThread implements Runnable {
        private String command;
        public WorkerThread(String s) { this.command=s; }
        @Override
        public void run() {
                System.out.println(Thread.currentThread().getName()+",task="+command);
        }
}

public class ExecutorExample {
  public static void main(String[] args) {
	ExecutorService e = Executors.newFixedThreadPool(5);
	for (int i = 0; i < 20; i++) {
		Runnable worker = new WorkerThread("" + i);
		e.execute(worker);
	}
        e.shutdown();
	try {
		e.awaitTermination(5000 , TimeUnit.MILLISECONDS ); // blocks until all tasks have completed execution after a shutdown request or timeout occurs
	} catch (InterruptedException ex) {
		e.shutdownNow();
	}
	System.out.println("Finished all threads");
  }
}
