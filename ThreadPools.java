import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Process implements Runnable{
	private int id;
	Process(int id){
		this.id = id;
	}
	
	public void run() {
		System.out.println("Starting: "+id);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Completed: "+id);
	}
}


public class ThreadPools {
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2); 
		
		for(int i=0;i<5;i++) {
			executor.submit(new Process(i));
		}
		
		executor.shutdown();
		System.out.println("All Tasks submitted");
		
		try {
			executor.awaitTermination(10, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
	}
}
