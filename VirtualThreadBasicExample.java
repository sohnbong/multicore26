public class VirtualThreadBasicExample {
    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            System.out.println("Running in: " + Thread.currentThread());
        };

        Thread vt = Thread.startVirtualThread(task);

        vt.join(); // 작업 완료 대기
    }
}
