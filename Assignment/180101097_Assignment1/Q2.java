class Simpsons_Integration 
{
    double left;            // stores leftmost point at which integral should be calculated 
    double right;           // stores rightmost point at which integral should be calculated
    int no_of_threads;      // stores number of threads used
    int n;                  // stores total number of points taken
    double result;          // stores the final result of integral

    // function to calculate the value at point x
    public double func(double x){ 
        double r = Math.exp(-x*x/2);
        double val = Math.sqrt(2*Math.PI);
        return r / val; 
    } 

    public void EachThread(int TID)
    {
        // size of computation for each thread
        int szpert = n / no_of_threads;

        // lower bound for the current thread
        int l = (TID)*szpert;

        // upper bound for the current thread
        int r = (TID+1)*szpert;
        if(TID == no_of_threads - 1){
            r = n;
        }

        double h = (right - left)/n;
        double temp = 0, val;

        // calcuating values from lower bound to upper bound for current thread
        for(int i=l;i<r;i++){
            val = left + i*h;
            val = func(val);
            if(i == 0 || i == n){
                temp += val;
            }
            else if(i % 2 == 0){
                temp += 4*val;
            }
            else {
                temp += 2*val;
            }
        } 
        result += temp;
    }

    public class Simpsons extends Thread {
        int id;                                  // thread id for each thread 
        public void run(){
            EachThread(id);
            return;
        }
    }

    public Simpsons_Integration(int i){
        this.left = -1.0;
        this.right = 1.0;
        this.n = 1000000;
        this.no_of_threads = i;
        this.result = 0.0;
    }

    public void answer()
    {
        // creating thread array to store each thread
        Simpsons myThreads[] = new Simpsons[no_of_threads];

        // starting all the threads
        for(int i = 0; i < no_of_threads; i++){
            myThreads[i] = new Simpsons();
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

        // calculating the final result according to the formula
        double h = (right - left)/n;
        result = result*(h / 3); 
    }
}

class Question2
{
    public static void main(String[] args){

        // args[0] is the input which is no of threads taken
        int num_threads = Integer.parseInt(args[0]);
        Simpsons_Integration a = new Simpsons_Integration(num_threads);  
        a.answer();
        System.out.println("Approx Integral Value: " + a.result);
    }
}