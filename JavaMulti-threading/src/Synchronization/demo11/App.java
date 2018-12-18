package Synchronization.demo11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {

	public static void main(String[] args) throws InterruptedException {
		
/*		Semaphore sem = new Semaphore(1);
		
		//povecava semafor za 1
		sem.release();
		
		//smanjuje semafor za 1
		sem.release();
		
		System.out.println("Available permits " + sem.availablePermits());*/
		
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		for(int i=0; i<200; i++) {
			
			executor.submit(new Runnable() {
				@Override
				public void run() {
					try {
						Connection.getInstance().connect();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		executor.shutdown();
		
		executor.awaitTermination(1, TimeUnit.DAYS);
		
		
	}
}
