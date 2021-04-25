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
	Node(Long acc){
		next = null;
		amount = 0L;
		account = acc;
	}
}
class Bank {
	final Node [] table;
	final HashMap<Long, Node> hash;
	public final long min = 1000000000;

	public Bank(){
		table = new Node[10];
		hash = new HashMap<>(); 
		for(int i = 0; i < 10; i++){
			table[i] = new Node(i*min);
			table[i].next = new Node((i+1)*min - 1);
		}
	}
	public void contains(Account a){
		int key = a.hashCode();	
		Node curr = hash.get(a.account);
		curr.amount += a.amount;
	}
	public void add(Account a){
		int key = a.hashCode();
		Node pred = table[key];
		Node curr = pred.next;
		while(curr.account < a.account){
			pred = curr;
			curr = curr.next;
		}
		Node newNode = new Node(a.account);
		newNode.amount = a.amount;
		newNode.next = curr;
		pred.next = newNode;
		hash.put(a.account,newNode);
	}
	public Long remove(Account a){
		int key = a.hashCode();
		Node pred = table[key];
		Node curr = pred.next;
		while(curr.account < a.account){
			pred = curr;
			curr = curr.next;
		}
		Long temp = curr.amount;
		pred.next = curr.next;
		hash.remove(a.account);
		return temp;
	}
	public void calculate(ArrayList<ArrayList<Long>> arr){
		for(int i = 0; i < arr.size(); i++)
		{
			int p = (int)(arr.get(i).get(1)/min);

			// withdraw money
			if(arr.get(i).get(0) == 1){
				Account a = new Account(arr.get(i).get(1),-arr.get(i).get(2));
				contains(a);
			}
			// deposit money
			else if(arr.get(i).get(0) == 2){
				Account a = new Account(arr.get(i).get(1),arr.get(i).get(2));
				contains(a);	
			}
			// transfer money
			else if(arr.get(i).get(0) == 3){
				Account a = new Account(arr.get(i).get(1),-arr.get(i).get(3));
				contains(a);

				p = (int)(arr.get(i).get(2)/min);
				a = new Account(arr.get(i).get(2),arr.get(i).get(3));
				contains(a);
			}
			// add account
			else if(arr.get(i).get(0) == 4){
				Account a = new Account(arr.get(i).get(1),arr.get(i).get(2));
				add(a);
			}
			// delete account
			else if(arr.get(i).get(0) == 5){
				Account a = new Account(arr.get(i).get(1),0L);
				Long x = remove(a);
			}
			// transfer account
			else if(arr.get(i).get(0) == 6){
				Account a = new Account(arr.get(i).get(1),0L);
				Long x = remove(a);

				Long temp = arr.get(i).get(1);
				temp -= min*p;
				temp += min*(arr.get(i).get(2));
				p = arr.get(i).get(2).intValue();

				a = new Account(temp,x);
				add(a);
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
		FileWriter ac1 = new FileWriter("sequential_account.txt");
		FileWriter am1 = new FileWriter("sequential_amount.txt");

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
		System.out.println("Execution time of processing transactions in milliseconds of sequential program: " + timeElapsed/1000000);
	}
}