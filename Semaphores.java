import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

class Account1{
	static Account1 acc = new Account1();
	private Semaphore sem = new Semaphore(10, true);
	private int connections = 0;
	
	Account1(){
		
	}
	
	public static Account1 getInstance() {
		return acc;
	}
	
	public void connect() {
		try {
			sem.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			doConnect();
		}finally {
			sem.release();
		}
	}
	
	public void doConnect() {
		
		synchronized(this) {
			connections++;
			System.out.println("Connections: "+connections);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		synchronized(this) {
			connections--;
		}
	}
}

public class Semaphores {
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for(int i=0;i<200;i++) {
			executor.submit(new Runnable() {

				@Override
				public void run() {
					Account1.getInstance().connect();
				}
				
			});
		}
		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.DAYS);
	}
}
