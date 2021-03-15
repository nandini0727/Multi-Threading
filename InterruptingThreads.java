import java.util.Random;

public class InterruptingThreads {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Starting..");
				Random rand = new Random();
				
				for(int i=0;i<1E8;i++) {
					Math.sin(rand.nextDouble());
					
//					try {
//						Thread.sleep(500000);
//					} catch (InterruptedException e) {
//						System.out.println("Interrupted..");
//						break;
//					}
					if(Thread.currentThread().isInterrupted()) {
						System.out.println("Interrupted..");
						break;
					}
				}
				
			}
			
		});
		
		t1.start();
		Thread.sleep(5);
		
		t1.interrupt();
		
		t1.join();
		System.out.println("Finished..");
	}
}
