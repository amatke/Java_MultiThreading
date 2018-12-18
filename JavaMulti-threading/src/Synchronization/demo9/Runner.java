package Synchronization.demo9;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {
	
	private Lock lock = new ReentrantLock();
	private int count = 0;
	
	//ova ce nam omoguciti metode wait() i notify() za reentrantLock
	private Condition cond = lock.newCondition();

	
	//ovo je neka npr inkrement metoda ali u njoj moze npr da se desi neki exception...
	//zato u firstThread i secondThread imamo try - finally blok
	public void increment() {
		for(int i=0; i<10000; i++) {
			count++;
		}
	}

	public void firstThread() throws InterruptedException {
		
		lock.lock();
		System.out.println("Waiting...");
		cond.await();			//cekamo ovde dok nam druga nit ne signalizira da se probudimo!
		
		System.out.println("Woken up!");
		
		try {
			increment();
		} finally {
			lock.unlock();
		}
		
		
	}
	
	public void secondThread() throws InterruptedException {
		
		//ovaj sleep() ovde je samo da budemo sigurni da ce prva nit da prva zapocne
		Thread.sleep(100);
		
		lock.lock();
		
		System.out.println("Press ENTER to continue");
		new Scanner(System.in).nextLine();
		System.out.println("ENTER pressed.");
		cond.signal();		//signalizira drugoj niti da se probudi!
		
		try {
			increment();
		} finally {
			//lock.unlock();			//bez ovog unlock-a duga nit se ne bi probudila
		}
	}
	
	public void finished() {
		System.out.println("Count = " + count);
	}
}
