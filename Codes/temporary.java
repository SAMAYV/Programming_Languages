import java.util.*;
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
	final Node [] table;
	final ReentrantLock [] locks;
	final AtomicInteger [] count;
	ExecutorService [] mythreads;
	public final long min = 1000000000;

	public Bank(){
		table = new Node[10];
		count = new AtomicInteger[10];
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
		locks[key].lock();
		count[key].incrementAndGet();
		locks[key].unlock();
		while(true){
			Node pred = table[key]; 
			Node curr = pred.next;
			while(curr.account < a.account){
				pred = curr; 
				curr = curr.next;
			}
			try {
				pred.lock.lock(); 
				curr.lock.lock();
				curr.amount += a.amount;
				return;
			} 
			finally {
				count[key].decrementAndGet();
				pred.lock.unlock(); 
				curr.lock.unlock();
			}
		}
	}
	public void add(Account a){
		int key = a.hashCode();
		while(count[key].get() > 0){}
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
			newNode.next = curr;
			pred.next = newNode;
			return;
		} 
		finally {
			locks[key].unlock();
		}
	}
	public Long remove(Account a){
		int key = a.hashCode();
		while(count[key].get() > 0){}
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
				return temp;
			}
			else {
				return 0L;
			}
		} 
		finally {
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
	public class Delete implements Runnable {
		Account a;
		public Delete(Account a){
			this.a = a;
		} 
		@Override
		public void run(){
			remove(a);
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
		for(int i = 0; i < 10; i++){
			mythreads[i].shutdown();
			while(!mythreads[i].isTerminated()){}
		}
	}
}

class FirstNameSorter implements Comparator<Account> {
	@Override
	public int compare(Account o1,Account o2){
		return o1.account.compareTo(o2.account);
	}
}

class Assign {
	public static void main(String args[]){
		Bank b = new Bank();
		File ac = new File("output_account.txt");
		File am = new File("output_amount.txt");
		Scanner input = null, input1 = null;
		try {
			input = new Scanner(ac);
			input1 = new Scanner(am);
		}
		catch(FileNotFoundException e){}
		ArrayList<ArrayList<Account>> temp = new ArrayList<ArrayList<Account>>();

		// sorting according to account numbers
		for(int j = 0; j < 10; j++){
			ArrayList<Account> p = new ArrayList<Account>();
   			for(int i = 0; i < 10; i++){
   				String word1 = input.next();
   				String word2 = input1.next();
   				long acc = Long.parseLong(word1);
   				long amt = Long.parseLong(word2);
   				p.add(new Account(acc,amt));
   			}
   			Collections.sort(p, new FirstNameSorter());
   			temp.add(p);
		}

		// building the hash table and linked list
		for(int j = 0; j < 10; j++){
			Node n = b.table[j];
			for(int i = 0; i < 10; i++){
				Account curr = temp.get(j).get(i);
				Node q = n.next;
   				Node p = new Node(curr.account);
   				p.amount = curr.amount;
   				n.next = p;
   				p.next = q;
   				n = n.next;
			}
		}

		// writing before processing transactions
		FileWriter ac2 = null, am2 = null;
		try {
			ac2 = new FileWriter("final0_account.txt");
			am2 = new FileWriter("final0_amount.txt");
		}
		catch(IOException fe){}

		for(int j = 0; j < 10; j++){
			Node n = b.table[j].next;
   			while(n.next != null){
   				try {
   					ac2.write(n.account + " ");
   					am2.write(n.amount + " ");
   				}
   				catch(IOException fe){}
   				n = n.next;	
   			}
   			try {
   				ac2.write("\n");
   				am2.write("\n");
   			}
   			catch(IOException fe){}
		}
		try {
			ac2.close();	
			am2.close();
		}
		catch(IOException fe){}
		
		// processing transactions
		File queries = new File("queries.txt");
		Scanner query = null;
		try {
			query = new Scanner(queries);
		}
		catch(FileNotFoundException e){}

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
		b.calculate(arr);

		// writing after processing transactions
		FileWriter ac1 = null, am1 = null;
		try {
			ac1 = new FileWriter("final_account.txt");
			am1 = new FileWriter("final_amount.txt");
		}
		catch(IOException fe){}

		for(int j = 0; j < 10; j++){
			Node n = b.table[j].next;
   			while(n.next != null){
   				try {
   					ac1.write(n.account + " ");
   					am1.write(n.amount + " ");
   				}
   				catch(IOException fe){}
   				n = n.next;	
   			}
   			try {
   				ac1.write("\n");
   				am1.write("\n");
   			}
   			catch(IOException fe){}
		}
		try {
			ac1.close();	
			am1.close();
		}
		catch(IOException fe){}
	}
}