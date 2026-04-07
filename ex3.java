class IntThread extends Thread {
  int my_id; // fields for communicating inputs
  int num_steps;
  int num_threads;
  double sum;

  IntThread(int id, int numSteps, int numThreads) {
    my_id=id; num_steps=numSteps; num_threads=numThreads;
    sum=0.0;
  }
  public void run() { 
    double x;
    int i;
    int i_start = my_id * (num_steps/num_threads);
    int i_end = i_start + (num_steps/num_threads);
    double step = 1.0/(double)num_steps;
    for (i=i_start;i<i_end;i++) { 
      x=(i+0.5)*step;
      sum=sum+4.0/(1.0+x*x);
    }
    sum = sum*step;
    System.out.println("myid"+my_id+", sum=" + sum);
  }
  public double getSum() { return sum; }
}

class ex3 {
  private static int NUM_THREAD = 4;
  private static int NUM_STEP = 1000000000;

  public static void main(String[] args) {
    int i;
    double sum=0.0;

    if (args.length==2) {
      NUM_THREAD = Integer.parseInt(args[0]);
      NUM_STEP = Integer.parseInt(args[1]);
    } 

    long start = System.currentTimeMillis();
    IntThread[] ts = new IntThread[NUM_THREAD];
    for(i=0; i < NUM_THREAD; i++) {
      ts[i] = new IntThread(i,NUM_STEP,NUM_THREAD);
      ts[i].start();
    }
     
    try {
      for (i=0;i<NUM_THREAD;i++) {
        ts[i].join();
        sum += ts[i].getSum();
      }
    } catch (InterruptedException e) {}
    long finish = System.currentTimeMillis();

    System.out.println("integration result=" + sum +"\n");
    System.out.println("Execution time (" + NUM_THREAD + "): "+(finish-start)+"ms");
  }

}

