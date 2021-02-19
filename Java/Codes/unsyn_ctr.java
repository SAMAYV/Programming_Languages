import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Counter implements Runnable {
	private static int counter = 0;
	private static final int limit = 1000;
	private static final int threadPoolSize = 5;
	
	private void increaseCounter() {
		System.out.println(Thread.currentThread().getName() + " : " + counter);
		counter++;
	}
	@Override
	public void run() {
		while (counter < limit) {
			increaseCounter();
		}
	}
	public static void main(String[] args) {
		ExecutorService ES = Executors.newFixedThreadPool(threadPoolSize);
		for (int i = 0; i < threadPoolSize; i++) { ES.submit(new Counter()); }
		ES.shutdown();
	}
}