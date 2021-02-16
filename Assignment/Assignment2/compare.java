import java.util.*;
import java.util.concurrent.*; 
import java.util.concurrent.locks.ReentrantLock; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.File; 
import java.io.FileReader; 
import java.io.IOException; 
import java.io.FileNotFoundException;
import java.util.Collections;
import java.io.FileWriter; 
import java.util.concurrent.Callable; 
import java.util.concurrent.Future; 
import java.util.concurrent.TimeUnit;

class Assignment2_180101097 {
	public static void main(String args[]) throws FileNotFoundException, IOException, Exception
	{
		File ac1 = new File("parallel_account.txt");
		File am1 = new File("parallel_amount.txt");
		File ac2 = new File("sequential_account.txt");
		File am2 = new File("sequential_amount.txt");
		Scanner para_acc = new Scanner(ac1);
		Scanner para_amt = new Scanner(am1);
		Scanner seq_acc = new Scanner(ac2);
		Scanner seq_amt = new Scanner(am2);

		int problems = 0;

		// comparing accounts in parallel_account.txt and sequential_account.txt
		while(para_acc.hasNext()){
			String word1 = para_acc.next();
   			String word2 = seq_acc.next();
   			long acc1 = Long.parseLong(word1);
   			long acc2 = Long.parseLong(word2);

   			// if corresponding accounts are not same
   			if(acc1 != acc2){
   				System.out.println(acc1 + " " + acc2);
   				problems++;
   			}
		}
		
		// comparing accounts in parallel_amount.txt and sequential_amount.txt
		while(para_amt.hasNext()){
			String word1 = para_amt.next();
   			String word2 = seq_amt.next();
   			long amt1 = Long.parseLong(word1);
   			long amt2 = Long.parseLong(word2);

   			// if corresponding amounts are not same
   			if(amt1 != amt2){
   				System.out.println(amt1 + " " + amt2);
   				problems++;
   			}
		}

		if(problems > 0){
			System.out.println(problems + " are found");
		}
		else {
			System.out.println("No problems are found");
		}
	}
}