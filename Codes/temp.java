import java.util.*;
import java.util.concurrent.locks.ReentrantLock; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.File; 
import java.io.FileWriter; 
import java.io.IOException; 

class Account {
	long no;
	long value;
	public static final long MOD = 1000000000;
	public Account(long no,long value){
		this.no = no;
		this.value = value;
	}
	public int hashCode(){
		return (int)(this.no / MOD);
	}
}
class Bank {
	ArrayList<List<Account>> table;
	final ReentrantLock [] locks;
	ExecutorService [] mythreads;
	public final long min = 1000000000;

	public Bank(){
		table = new ArrayList<List<Account>>();
		for(int i = 0; i < 10; i++){
			table.add(Collections.synchronizedList(new LinkedList<Account>()));
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
	public final void acquire(int x){
		locks[x].lock();
	}
	public final void release(int x){
		locks[x].unlock();
	}
	public class Initialize implements Runnable {
		int id;
		public Initialize(int id){
			this.id = id;
		}
		@Override
		public void run(){
			for(int i=0;i<10;i++){
				long rand_account_no = (long)(id*min + (long)(Math.random()*min));
				long rand_amount = (long)(min*(Math.random()));
				table.get(id).add(new Account(rand_account_no, rand_amount));
			}
		}
	}
	public class Withdraw implements Runnable {
		long acc_no;
		long amount;
		public Withdraw(long acc_no,long amount){
			this.acc_no = acc_no;
			this.amount = amount;
		} 
		@Override
		public void run(){
			int idx = (int)(acc_no/min);
			for(int i = 0; i < table.get(idx).size(); i++)
			{ 
            	Account c = table.get(idx).get(i); 
            	if(c.no == acc_no){
            		c.value -= amount;
            		table.get(idx).set(i,c);
            		break;
            	}
        	} 
		}
	}
	public class Deposit implements Runnable {
		long acc_no;
		long amount;
		public Deposit(long acc_no,long amount){
			this.acc_no = acc_no;
			this.amount = amount;
		} 
		@Override
		public void run(){
			int idx = (int)(acc_no/min);
			for(int i = 0; i < table.get(idx).size(); i++)
			{ 
            	Account c = table.get(idx).get(i); 
            	if(c.no == acc_no){
            		c.value += amount;
            		table.get(idx).set(i,c);
            		break;
            	}
        	} 
		}
	}
	public class Remove implements Runnable {
		long acc_no;
		public Remove(long acc_no){
			this.acc_no = acc_no;
		}
		@Override
		public void run(){
			int idx = (int)(acc_no/min);
			for(int i = 0; i < table.get(idx).size(); i++){
				Account c = table.get(idx).get(i);
				if(c.no == acc_no){
					Account p = table.get(idx).remove(i);
					break;
				}
			}
		}
	}
	public class Add implements Runnable {
		long acc_no;
		public Add(long acc_no){
			this.acc_no = acc_no;
		} 
		@Override
		public void run(){

		}
	}
	public void calculate(ArrayList<ArrayList<Long>> arr){
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
				Account a = new Account(arr.get(i).get(1),0L);
				mythreads[p].execute(new Add(a));
			}
			// delete account
			else if(arr.get(i).get(0) == 5){
				Account a = new Account(arr.get(i).get(1),0L);
				mythreads[p].execute(new Delete(a));		
			}
			// transfer account
			else if(arr.get(i).get(0) == 6){
				Account a = new Account(arr.get(i).get(1),0L);
				mythreads[p].execute(new Delete(a));

				Long temp = arr.get(i).get(1);
				temp -= min*p;
				temp += min*(arr.get(i).get(2));
				Long p1 = arr.get(i).get(2);
				p = p1.intValue();

				a = new Account(temp,0L);
				mythreads[p].execute(new Add(a));
			}
		}
	}
	public void start(){
		for(int i=0;i<10;i++){
			for(int j=0;j<10;j++){
				mythreads[i].execute(new Initialize(i));
			}
		}
	}
}
class Test {
	public static void main(String args[]){
		Bank c = new Bank();
		c.start();
		
		for(int i=0;i<10;i++){
			c.mythreads[i].shutdown();
			while(!c.mythreads[i].isTerminated()){}
		}

		FileWriter ac = null, am = null;
		try {
			ac = new FileWriter("output_account.txt");
			am = new FileWriter("output_amount.txt");
		}
		catch(IOException fe){}

		for(int i = 0; i < 10; i++){
			for(int j = 0; j < c.table.get(i).size(); j++){
				try {
					ac.write(c.table.get(i).get(j).no + " ");
					am.write(c.table.get(i).get(j).value + " ");	
				}
				catch(IOException fe){}
			}
			try {
				ac.write("\n");
				am.write("\n");	
			}
			catch(IOException fe){}
		}
		try {
			ac.close();	
			am.close();
		}
		catch(IOException fe){}
		
		// Scanner sc = new Scanner(System.in);
		// ArrayList<ArrayList<Long>> arr = new ArrayList<ArrayList<Long>>();
		
		// int rows = sc.nextInt();
		// for(int i=0;i<rows;i++){
		// 	Long type = sc.nextLong();
		// 	ArrayList<Long> curr = new ArrayList<Long>();
		// 	curr.add(type);
		// 	if(type == 1){
		// 		Long acc = sc.nextLong();
		// 		Long amt = sc.nextLong();
		// 		curr.add(acc);
		// 		curr.add(amt);
		// 	}
		// 	arr.add(curr);
		// }
		// c.calculate(arr);
	}
}
