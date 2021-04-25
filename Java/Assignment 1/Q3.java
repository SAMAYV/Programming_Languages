class Area
{
	int [][] A;			// matrix used for computation
	int [][] B;			// matrix used for computation
	int [][] C;			// output matrix
	int n;				// stores the number of rows for the square matrix
	int no_of_threads;	// stores number of threads used

	// initialising the matrix A and B with random values
	public void initialise(int TID)
	{
		// size of computation for each thread
		int row_sz = n / no_of_threads;

		// lower bound for the row for current thread
		int l = TID*row_sz;

		// upper bound for the row for current thread
		int r = (TID+1)*row_sz;
		if(TID == no_of_threads - 1){
			r = n;
		}

		// initialising A and B from rows l to r
		for(int i=l;i<r;i++){
			for(int j=0;j<n;j++){
				A[i][j] = (int)(Math.random()*10);
				B[i][j] = (int)(Math.random()*10);
				C[i][j] = 0;
			}
		}
	}

	// multipling matrix A and B 
	public void multiply(int TID)
	{
		// size of computation for each thread
		int row_sz = n / no_of_threads;

		// lower bound for the row for current thread
		int l = TID*row_sz;

		// upper bound for the row for current thread
		int r = (TID+1)*row_sz;
		if(TID == no_of_threads - 1){
			r = n;
		}

		// computing final values of C from rows l to r
		for(int i=l;i<r;i++){
			for(int j=0;j<n;j++){
				for(int k=0;k<n;k++){
					C[i][j] = C[i][j] + A[i][k]*B[k][j];
				}
			}
		}	
	}

	class Rectangle extends Thread {
		int id;			// stores each thread id
		int f;
		public void run(){
			// calling initialise if f is 1
			if(f == 1){
				initialise(id);	
			}
			// calling multiply if f is 0
			else {
				multiply(id);
			}
			return;
		}
	}

	public Area(int i){
		this.n = 1000;
		this.A = new int[n][n];
		this.B = new int[n][n];
		this.C = new int[n][n];
		this.no_of_threads = i;
	}
	
	public void compute(int flag)
	{
		// creating thread array to store each thread
		Rectangle myThreads[] = new Rectangle[no_of_threads];
		
		// starting all the threads having f as flag
		// using flag, initialisation of A and B is done first, then computation of C is done.
		// This is done by calling compute(1) and then compute(0) in the order

		for(int i = 0; i < no_of_threads; i++){
			myThreads[i] = new Rectangle();
			myThreads[i].id = i;
			myThreads[i].f = flag;
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

class Question3
{
	public static void main(String[] args){
		
		// args[0] is the input which is no of threads taken
		int num_threads = Integer.parseInt(args[0]);
		Area m = new Area(num_threads);
		
		// it will initialise matrix A and B with random values
		m.compute(1);

		// it will perform matrix multiplication on A and B and stores output in C 
		m.compute(0);

		// Printing the matrix C
		System.out.println("Array elements after multiplication are: ");
		for(int i=0;i<m.n;i++){
			for(int j=0;j<m.n;j++){
				System.out.print(m.C[i][j] + " ");
			}
			System.out.println();
		}
	}
}