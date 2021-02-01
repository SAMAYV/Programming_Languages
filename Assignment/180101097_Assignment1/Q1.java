import java.util.concurrent.atomic.AtomicInteger;

class PiMonteCarlo 
{
	AtomicInteger inside_circle;    // stores number of points inside the circle of radius 1
	int n;							// stores total number of points taken
	int no_of_threads;				// stores number of threads used

	public void CalcPI(int TID)
	{
		// size of computation for each thread
		int szpert = n / no_of_threads;		

		// for last thread, remainder value is also added to consider all n points
		if(TID == no_of_threads - 1){		
			szpert += n % no_of_threads;
		}
		for(int i = 1; i <= szpert; i++){
			double x = Math.random();
			double y = Math.random();
	
			// if point is inside the circle, increment the count
			if(x*x + y*y <= 1){
				inside_circle.incrementAndGet();
			}
		}
	}

	class MonteCarlo extends Thread {
		int id;						// thread id for each thread 
		public void run(){
			CalcPI(id);
			return;
		}
	}

	public PiMonteCarlo(int i){
		this.inside_circle = new AtomicInteger(0);
		this.n = 1000000;
		this.no_of_threads = i;
	}

	public void getPi()
	{
		// creating thread array to store each thread
		MonteCarlo myThreads[] = new MonteCarlo[no_of_threads];
		
		// starting all the threads
		for(int i = 0; i < no_of_threads; i++){
			myThreads[i] = new MonteCarlo();
			myThreads[i].id = i;
			myThreads[i].start();	
		}

		// joining all the threads with the parent thread to get parallelism
		for(int i = 0; i < no_of_threads; i++){
			try {
				myThreads[i].join();	
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Question1 
{
	public static void main(String[] args){

		// args[0] is the input which is no of threads taken
		int num_threads = Integer.parseInt(args[0]);

		PiMonteCarlo PiVal = new PiMonteCarlo(num_threads);
		PiVal.getPi();
		
		// calculating the value according to the formula
		double value = 4.0 * PiVal.inside_circle.get() / PiVal.n;
		System.out.println("Approx PI Value: " + value);
	}
}