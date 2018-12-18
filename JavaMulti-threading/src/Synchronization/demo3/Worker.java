package Synchronization.demo3;

import java.util.ArrayList;
import java.util.Random;

/*Dve niti mogu da pokrecu istovremeno metodu process (koja se sastoji iz 2 metode),
ali samo jedna nit moze izvrsavati jednu ili drugu fazu.
Ako je zauzeta prva faza, nit ce sacekati da se ono oslobodi sa zavrsavanjem syncronized bloka.
U ovom primeru se desava slucaj da jedna nit radi radi u stageOne a druga u stageTwo.*/

public class Worker {
	
	private ArrayList<Integer> list1 = new ArrayList<Integer>();
	private ArrayList<Integer> list2 = new ArrayList<Integer>();
	
	Random random = new Random();
	
	Object lock1 = new Object();

	Object lock2 = new Object();

	public void stageOne() {	
		
		synchronized (lock1) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));	
		}
	}
	
	
	public void stageTwo() {
		synchronized (lock2) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));	
		}
	}
	
	public void process() {
		for(int i=0; i<1000; i++) {
			stageOne();
			stageTwo();
		}
	}
	
	public void main() {

		System.out.println("Starting...");
		long start = System.currentTimeMillis();
	
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				process();
			}
		});
		
		Thread t2 = new Thread(new Runnable() {	
			@Override
			public void run() {
				process();
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		

		System.out.println("Processed time: " + (end - start));
		System.out.println("list1: " + list1.size() + "; list2: " + list2.size());
	}

}
