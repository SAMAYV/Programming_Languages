import java.util.*;
import java.util.concurrent.locks.ReentrantLock; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Account {
	int no;
	int value;
	public static final int MOD = 1000000000;
	public int hashCode(Account x){
		return x.no / MOD;
	}
}
class CoarseHashSet 
{
	LinkedList<Account> [] table;
	final ReentrantLock [] locks;
	ExecutorService [] mythreads;

	public CoarseHashSet(){
		for(int i = 0; i < 10; i++) {
			table[i] = new LinkedList<Account>();
		}
		locks = new ReentrantLock[10];
		for(int j = 0; j < 10; j++) {
			locks[j] = new ReentrantLock();
		}
		for(int i = 0; i < 10; i++){
			mythreads[i] = Executors.newFixedThreadPool(10);
		}
	}
	public final void acquire(Account x){
		locks[x.hashCode()].lock();
	}
	public void release(Account x){
		locks[x.hashCode()].unlock();
	}
	class Withdraw implements Runnable {
		Account curr;
		int index;
		public Withdraw(Account x,int index){
			this.curr = x;
			this.index = index;
		} 
		@Override
		public void run(){
			for(int i = 0; i < table[index].size(); i++){ 
            	Account c = table[index].get(i); 
            	if(c.no == curr.no){
            		c.value -= curr.value;
            		table[index].set(i,c);
            		break;
            	}
        	} 
		}
	}
	public void calculate(int type,Account x){
		int p = x.hashCode();
		
		// withdraw 
		if(type == 1){
			Runnable r = new Withdraw(x,p);
			mythreads[p].execute(r);
		}
	}
}
class Test {
	public static void main(String args[]) {
			
	}
}
