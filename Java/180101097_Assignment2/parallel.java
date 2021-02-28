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

class Account {
	Long amount;
	Long account;
	public final long min = 1000000000;
	public Account(Long account,Long amount){
		this.amount = amount;
		this.account = account;
	}
	public int hashCode(){
		return (int)(this.account/min);
	}
}
class Node {
	Long account;
	Long amount;
	Node next;
	final ReentrantLock lock;
	Node(Long acc){
		next = null;
		lock = new ReentrantLock();
		amount = 0L;
		account = acc;
	}
}
class Bank {
	final Node [] table;						// table used to store the branches
	final ReentrantLock [] locks;				// lock of each branch
	final AtomicInteger [] count;				// stores the current number of transactions occurring in each branch
	final ConcurrentHashMap<Long, Node> hash;	// hash table for accessing the nodes through their account numbers
	ExecutorService [] mythreads;				// threads of each branch
	public final long min = 1000000000;

	public Bank(){
		table = new Node[10];
		count = new AtomicInteger[10];
		hash = new ConcurrentHashMap<>(); 
		for(int i = 0; i < 10; i++){
			table[i] = new Node(i*min);
			table[i].next = new Node((i+1)*min - 1);
			count[i] = new AtomicInteger(0);
		}
		locks = new ReentrantLock[10];
		for(int j = 0; j < 10; j++){
			locks[j] = new ReentrantLock();
		}
		mythreads = new ExecutorService[10];
		for(int i = 0; i < 10; i++){
			mythreads[i] = Executors.newFixedThreadPool(10);
		}
	}
	public void contains(Account a){
		int key = a.hashCode();

		// wait till the branch is locked due to add, delete or transfer account transaction
		locks[key].lock();

		// one more transaction is going to be performed in this branch hence increment the count
		count[key].incrementAndGet();

		// unlock the branch
		locks[key].unlock();
		
		if(!hash.containsKey(a.account)){
			return;
		}
		Node curr = hash.get(a.account);
		
		try {
			curr.lock.lock();
			curr.amount += a.amount;
			return;
		}
		finally {
			count[key].decrementAndGet(); 
			curr.lock.unlock();
		}
	}
	// implemented with coarse grained locking 
	public void add(Account a){
		int key = a.hashCode();

		// while there is a transaction occurring in this branch, wait
		while(count[key].get() > 0){

		}

		// locking this branch to implement coarse grained method
		locks[key].lock();
		try {
			Node pred = table[key];
			Node curr = pred.next;
			while(curr.account < a.account){
				pred = curr;
				curr = curr.next;
			}
			if(curr.account - a.account == 0){
				return;
			}
			Node newNode = new Node(a.account);
			newNode.amount = a.amount;
			newNode.next = curr;
			pred.next = newNode;
			hash.put(a.account,newNode);
			return;
		} 
		finally {

			// releasing lock of this branch
			locks[key].unlock();
		}
	}
	// implemented with coarse grained locking 
	public Long remove(Account a){
		int key = a.hashCode();

		// while there is a transaction occurring in this branch, wait
		while(count[key].get() > 0){

		}

		// locking this branch to implement coarse grained method
		locks[key].lock();
		try {
			Node pred = table[key];
			Node curr = pred.next;
			while(curr.account < a.account){
				pred = curr;
				curr = curr.next;
			}
			if(a.account - curr.account == 0){
				Long temp = curr.amount;
				pred.next = curr.next;
				hash.remove(a.account);

				// returning the amount present in the removed account
				return temp;
			}
			else {
				return 0L;
			}
		} 
		finally {

			// releasing lock of this branch
			locks[key].unlock();
		}
	}
	public class Withdraw implements Runnable {
		Account a;
		public Withdraw(Account a){
			this.a = a;
		} 
		@Override
		public void run(){
			contains(a);
		}
	}
	public class Deposit implements Runnable {
		Account a;
		public Deposit(Account a){
			this.a = a;
		} 
		@Override
		public void run(){
			contains(a);
		}
	}
	public class Add implements Runnable {
		Account a;
		public Add(Account a){
			this.a = a;
		} 
		@Override
		public void run(){
			add(a);
		}	
	}
	public class Delete implements Callable<Long> {
		Account a;
		public Delete(Account a){
			this.a = a;
		} 
		public Long call() throws Exception {
			return remove(a);
		}	
	}
	public void calculate(ArrayList<ArrayList<Long>> arr) throws Exception {
		for(int i = 0; i < arr.size(); i++)
		{
			int p = (int)(arr.get(i).get(1)/min);

			// withdraw money
			if(arr.get(i).get(0) == 1){
				Account a = new Account(arr.get(i).get(1),-arr.get(i).get(2));
				mythreads[p].execute(new Withdraw(a));
			}
			// deposit money
			else if(arr.get(i).get(0) == 2){
				Account a = new Account(arr.get(i).get(1),arr.get(i).get(2));
				mythreads[p].execute(new Deposit(a));	
			}
			// transfer money
			else if(arr.get(i).get(0) == 3){
				Account a = new Account(arr.get(i).get(1),-arr.get(i).get(3));
				mythreads[p].execute(new Withdraw(a));

				p = (int)(arr.get(i).get(2)/min);
				a = new Account(arr.get(i).get(2),arr.get(i).get(3));
				mythreads[p].execute(new Deposit(a));
			}
			// add account
			else if(arr.get(i).get(0) == 4){
				Account a = new Account(arr.get(i).get(1),arr.get(i).get(2));
				mythreads[p].execute(new Add(a));
			}
			// delete account
			else if(arr.get(i).get(0) == 5){
				Account a = new Account(arr.get(i).get(1),0L);
				Future<Long> x = mythreads[p].submit(new Delete(a));
			}
			// transfer account
			else if(arr.get(i).get(0) == 6){
				Account a = new Account(arr.get(i).get(1),0L);
				Future<Long> x = mythreads[p].submit(new Delete(a));

				Long temp = arr.get(i).get(1);
				temp -= min*p;
				temp += min*(arr.get(i).get(2));
				p = arr.get(i).get(2).intValue();

				a = new Account(temp,x.get());
				mythreads[p].execute(new Add(a));
			}
		}
		for(int i = 0; i < 10; i++){
			mythreads[i].shutdown();
			while(!mythreads[i].isTerminated()){
				
			}
		}
	}
}

class Assignment2_180101097 {
	public static void main(String args[]) throws FileNotFoundException, IOException, Exception
	{
		Bank b = new Bank();
		File ac = new File("input_account.txt");
		File am = new File("input_amount.txt");
		Scanner input = new Scanner(ac), input1 = new Scanner(am);

		// building the hash table and linked list
		for(int j = 0; j < 10; j++){
			Node n = b.table[j];
			for(int i = 0; i < 100000; i++){
				String word1 = input.next();
   				String word2 = input1.next();
   				long acc = Long.parseLong(word1);
   				long amt = Long.parseLong(word2);

				Account curr = new Account(acc,amt);
				Node q = n.next;
   				Node p = new Node(curr.account);
   				p.amount = curr.amount;
   				b.hash.put(curr.account,p);
   				n.next = p;
   				p.next = q;
   				n = n.next;
			}
		}
		
		// writing transactions in arraylist
		File queries = new File("transactions.txt");
		Scanner query = null;
		query = new Scanner(queries);
		
		ArrayList<ArrayList<Long>> arr = new ArrayList<ArrayList<Long>>();
		while(query.hasNextLine()){
			String line = query.nextLine();
			String[] splitStr = line.split("\\s+");
			ArrayList<Long> a = new ArrayList<Long>();
			for(String s : splitStr){
				a.add(Long.parseLong(s));
			}
			arr.add(a);
		}
		
		// time before processing transactions
		long startTime = System.nanoTime();

		// processing transactions
		b.calculate(arr);

		// time after processing transactions
		long endTime = System.nanoTime();

		// time taken to perform all transactions
		long timeElapsed = endTime - startTime;

		// writing after processing transactions
		FileWriter ac1 = new FileWriter("parallel_account.txt");
		FileWriter am1 = new FileWriter("parallel_amount.txt");

		for(int j = 0; j < 10; j++){
			Node n = b.table[j].next;
   			while(n.next != null){
   				ac1.write(n.account + " ");
   				am1.write(n.amount + " ");
   				n = n.next;	
   			}
   			ac1.write("\n");
   			am1.write("\n");
		}
		ac1.close();	
		am1.close();

		// printing the time taken by transactions
		System.out.println("Execution time of processing transactions in milliseconds of parallel program: " + timeElapsed/1000000);
	}
}