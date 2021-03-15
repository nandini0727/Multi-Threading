import java.util.LinkedList;

class Processor2{
	private LinkedList<Integer> list = new LinkedList<>();
	private int LIMIT = 10;
	private Object lock = new Object();
	
	public void producer() throws InterruptedException {
		int value = 0;
		
		while(true) {
			synchronized(lock) {
				
				while(list.size() == LIMIT)
					lock.wait();
				list.add(value++);
				lock.notify();
			}
		}
	}
	
	public void consumer() throws InterruptedException {
		while(true) {
			synchronized(lock) {
				while(list.size() == 0)
					lock.wait();
				System.out.println("List size is "+list.size());
				int value = list.removeFirst();
				System.out.println("value is "+value);
				lock.notify();
			}
			Thread.sleep(1000);
		}
	}
}

public class WaitNotifyLowLevel {
	public static void main(String[] args) throws InterruptedException {
		Processor2 process = new Processor2();
		
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					process.producer();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					process.consumer();
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
	}
}
