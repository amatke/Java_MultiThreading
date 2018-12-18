package Synchronization.demo4;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Process implements Runnable{
	
	private int id;
	
	public Process(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		System.out.println("Starting " + id);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Completed " + id);
	}
	
}

public class App {
	
	public static void main(String[] args) {
		
		//u newFixedThreadPool prosledjujemo broj tzv taskova koji ce se istovremeno paralelno izvrsavati
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		for(int i=0; i<5; i++) {
			executor.submit(new Process(i));
		}
		
		executor.shutdown();
		System.out.println("All tasks submited.");
		
		try {
			executor.awaitTermination(1, TimeUnit.HOURS);		//ceka da se svi taskovi zavrse posle shutdown-a prosledjeno vreme
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("All tasks completed.");

	}

}
