package Synchronization.demo6;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class App {

	//zasnovan na FIFO pravilu za brisanje
	private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
	
	public static void main(String[] args) {
		
		Thread t1 = new Thread(new Runnable() {	
			@Override
			public void run() {
				try {
					producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {	
			@Override
			public void run() {
				try {
					consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
	}
	
	/*stavljanje u queue ce se desiti samo ako nije pun queue*/
	private static void producer() throws InterruptedException {
		Random random = new Random();
		
		while(true) {
			queue.put(random.nextInt(100));
		}
	}
	
	/*Iz queue se uzima samo ako nije prazan*/
	private static void consumer() throws InterruptedException {
		
		Random random = new Random();
		
		while(true) {
			Thread.sleep(100);
			if(random.nextInt(10) == 0) {
				Integer value = queue.take();
				System.out.println("Taken value " + value + "; queue size " + queue.size());
			}
		}
	}
}
