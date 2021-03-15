class Runner1 implements Runnable{

	@Override
	public void run() {
		for(int i=0;i<10;i++) {
			System.out.println("Hello "+i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
public class demo2 {
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runner1());
		t1.start();
		
		Thread t2 = new Thread(new Runner1());
		t2.start();
		
		
		System.out.println("Hello");
	}
	
}