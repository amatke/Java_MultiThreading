package Synchronization.demo12;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App {

	public static void main(String[] args) {
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Random random = new Random();
		
		Future<Integer> future = executor.submit(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("Starting call...");
				
				int duration = random.nextInt(4000);
				Thread.sleep(duration);
				
				if(duration > 2000) {
					throw new Exception("Sleeping to much!");
				}
				
				System.out.println("Finished!");
				
				return duration;
			}
		});
		
		try {
			System.out.println("Duration: " + future.get());
		} catch (ExecutionException e) {
			System.out.println(e);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		
		
		executor.shutdown();
	}
}
