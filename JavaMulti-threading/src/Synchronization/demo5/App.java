package Synchronization.demo5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Process implements Runnable {
	
	private CountDownLatch latch;
	
	public Process(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		System.out.println("Started ");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		latch.countDown();	
		
	}
}

public class App {
	
	public static void main(String[] args) {
		
		/*	Odbrojava u nazad od prosledjenog broja do 0, izvrsavajuci Niti. 
		Niti cekaju na lecu dok se on ne dekrementira do 0.*/
		CountDownLatch latch = new CountDownLatch(3);
		
		//ovde pravimo 3 treda
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		
		//ovde pravimo prcese - trenutno ih imamo samo 3 u primeru
		for(int i=0; i<3; i++) {
			executor.submit(new Process(latch));
		}
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Completed ");
		executor.shutdown();
	}
}
