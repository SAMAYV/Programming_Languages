import java.util.*;
import java.util.concurrent.locks.ReentrantLock; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Account {
	Long amount;
	Long account;
	public final long min = 1000000000;
	public Account(Long amount,Long account){
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
			if(curr.account == a.account){
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
			if(a.account == curr.account){
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

				Long temp = arr.get(i).get(2);
				temp -= min*p;
				temp += min*(arr.get(i).get(2));
				Long p1 = arr.get(i).get(2);
				p = p1.intValue();

				a = new Account(temp,0L);
				mythreads[p].execute(new Add(a));
			}
		}
	}
}
class Assign {
	public static void main(String args[]){

	}
}