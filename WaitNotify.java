import java.util.Scanner;

class Processor1{
	public void producer() throws InterruptedException {
		synchronized(this) {
			System.out.println("Process started..");
			wait();
			System.out.println("Process resumed");
		}
	}
	
	public void consumer() throws InterruptedException {
		Scanner scanner = new Scanner(System.in);
		Thread.sleep(2000);
		synchronized(this) {
			System.out.println("Press return key..");
			scanner.nextLine();
			System.out.println("Return key pressed..");
			notify();
			Thread.sleep(5000);
		}
	}
}


public class WaitNotify {
	public static void main(String[] args) throws InterruptedException {
		Processor1 process = new Processor1();
		
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
