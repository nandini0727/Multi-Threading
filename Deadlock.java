import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account{
	private int balance = 10000;
	
	public void deposit(int amount) {
		balance += amount;
	}
	
	public void withdraw(int amount) {
		balance -= amount;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public static void transfer(Account acc1, Account acc2, int amount) {
		acc1.withdraw(amount);
		acc2.deposit(amount);
	}
}

class App2{
	Account acc1 = new Account();
	Account acc2 = new Account();
	
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();
	
	public void acquireLocks(Lock lock1, Lock lock2) throws InterruptedException {
		
		while(true) {
			boolean getFirstLock = false;
			boolean getSecondLock = false;
			
			try {
				getFirstLock = lock1.tryLock();
				getSecondLock = lock2.tryLock();
			}
			finally {
				if(getFirstLock && getSecondLock)
					return;
				if(getFirstLock)
					lock1.unlock();
				if(getSecondLock)
					lock2.unlock();
			}
			Thread.sleep(1);	
		}
	}
	
	public void firstThread() throws InterruptedException {
		Random rand = new Random();
		for(int i=0;i<100;i++) {
			acquireLocks(lock1, lock2);
			Account.transfer(acc1, acc2, rand.nextInt(100));
			lock1.unlock();
			lock2.unlock();
		}
	}
	
	public void secondThread() throws InterruptedException {
		Random rand = new Random();
		for(int i=0;i<100;i++) {
			acquireLocks(lock1, lock2);
			Account.transfer(acc2, acc1, rand.nextInt(100));
			lock1.unlock();
			lock2.unlock();
		}
	}
	
	public void finalBlock() {
		System.out.println("First account Balance: "+acc1.getBalance());
		System.out.println("Second account Balance: "+acc2.getBalance());
		System.out.println("Total Balance: "+(acc1.getBalance()+acc2.getBalance()));
	}
}


public class Deadlock {
	public static void main(String[] args) throws InterruptedException {
		App2 process = new App2();
		
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
