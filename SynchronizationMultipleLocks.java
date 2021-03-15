import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SynchronizationMultipleLocks {
	
	static Object lock1 = new Object();
	static Object lock2 = new Object();
	
	static List<Integer> list1 = new ArrayList<>();
	static List<Integer> list2 = new ArrayList<>();
	static Random random = new Random();
	
	public static void stageOne() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized(lock1) {
			list1.add(random.nextInt(100));
		}
		
	}
	
	public static void stageTwo() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized(lock1) {
			list2.add(random.nextInt(100));
		}
	}
	
	public static void process() {
		for(int i=0;i<1000;i++) {
			stageOne();
			stageTwo();
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				process();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				process();
			}
		});
		
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println(end-start);
		System.out.println(list1.size());
		System.out.println(list1.size());
	}
}
