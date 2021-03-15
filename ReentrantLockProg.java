import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class App1{
	private int count = 0;
	private Lock lock = new ReentrantLock();
	private Condition cond = lock.newCondition();
	private void increment() {
		
		for(int i=0;i<10000;i++)
			count++;
	}
	
	public void firstThread() throws InterruptedException {
		lock.lock();
		System.out.println("Waiting..");
		cond.await();
		System.out.println("Resumed..");
		
		try {
			increment();
		}
		finally {
			lock.unlock();
		}	
	}
	
	public void secondThread() throws InterruptedException {
		
		Thread.sleep(1000);
		lock.lock();
		
		System.out.println("Press return key");
		new Scanner(System.in).nextLine();
		System.out.println("Return key entered");
		cond.signal();
		
		try {
			increment();
		}
		finally {
			lock.unlock();
		}
	}

	public void finalBlock() {
		System.out.println(count);
	}
}

public class ReentrantLockProg {
	public static void main(String[] args) throws InterruptedException {
		App1 process = new App1();
		
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					process.firstThread();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					process.secondThread();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		process.finalBlock();
	}
}
