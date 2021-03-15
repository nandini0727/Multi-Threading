import java.util.Scanner;

class Processor extends Thread{
	private volatile boolean running = true;
	
	public void run() {
		while(running) {
			System.out.println("Hello");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown() {
		running = false;
	}
}


public class ThreadSynchronization {

	public static void main(String[] args) {
		Processor proc = new Processor();
		proc.start();
		
		System.out.println("Press Enter to STOP....");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		
		proc.shutdown();
	}
}
