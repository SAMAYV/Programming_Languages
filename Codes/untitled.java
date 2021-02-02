import java.util.*;
import java.util.concurrent.locks.ReentrantLock; 
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class Account {
	long no;
	long value;
	public static final long MOD = 1000000000;
	public Account(long no,long value){
		this.no = no;
		this.value = value;
	}
	public long hashCode(Account x){
		return x.no / MOD;
	}
}
class CoarseHashSet {
	ExecutorService mythreads;
	List<Account> a;
	AtomicInteger c;
	int v;

	public CoarseHashSet(){
		a = Collections.synchronizedList(new LinkedList<Account>()); 
		mythreads = Executors.newFixedThreadPool(10);
	}
	public class Initialize implements Runnable {
		@Override
		public void run(){
			for(int i=0;i<10000;i++){
				long v1 = (long)(10000000000L*(Math.random()));
				a.add(new Account(v1,1000));
			}
		}
	}
	public void start(){
		for(int j=0;j<10;j++){
			mythreads.execute(new Initialize());
		}
		mythreads.shutdown();
		while(!mythreads.isTerminated()){
			
		}
	}
}
class Test {
	public static void main(String args[]) {
		CoarseHashSet c1 = new CoarseHashSet();
		c1.start();
		System.out.println(c1.a.size());
	}
}
