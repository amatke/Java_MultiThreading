package Synchronization.demo10;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {
	
	private Account ac1 = new Account();
	private Account ac2 = new Account();
	
	private Lock lock1 = new ReentrantLock();
	private Lock lock2 = new ReentrantLock();

/*	Metoda dobija lock-ove u bilo kom rasporedu, a njen zadatak je da nikad ne nepravi DeadLock
 *  Koristimo tryLock() metodu koja nam odmah fraca true ili false ako je uspela ili ne.*/
	private void acquireLock(Lock firstLock, Lock secondLock) throws InterruptedException {
		
		boolean gotFirstLock = false;
		boolean gotSecondLock = false;
		
		//ovde je petlja da bismo osigurali da uvek na kraju dobijemo lock objekte kako bi svaka nit odradila svoj posao
		while(true) {
			
			try {
				gotFirstLock = firstLock.tryLock();
				gotSecondLock = secondLock.tryLock();
			} finally {
				if(gotFirstLock && gotSecondLock)
					return;
				if(gotFirstLock)		//ako je zakljucan firstLock onda ga otkljucavamo
					firstLock.unlock();
				if(gotSecondLock)
					secondLock.unlock();
			}
			Thread.sleep(1);
		}

	}
	
	
	public void firstThread() throws InterruptedException {
		
		Random random = new Random();

		for(int i=0; i<10000; i++) {
			
		/*	lock1.lock();
			lock2.lock();*/
			
			acquireLock(lock1, lock2);

			
			try {
				Account.transfer(ac1, ac2, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
			
		}
	}
	
	public void secondThread() throws InterruptedException {
		
		Random random = new Random();
		
		for(int i=0; i<10000; i++) {
			
			// uvek pozivati lock() metode u istom redosledu da ne bismo imali DeadLock !
			/*lock1.lock();
			lock2.lock();*/
			
			acquireLock(lock2, lock1);
			
			try {
				Account.transfer(ac2, ac1, random.nextInt(100));
			} finally {
				lock1.unlock();
				lock2.unlock();
			}
		}
	}
	
	public void finished() {
		System.out.println("Ac1 balance: " + ac1.getBalance());
		System.out.println("Ac2 balance: " + ac2.getBalance());
		System.out.println("Total balance: " + (ac1.getBalance() + ac2.getBalance()) );
	}
}
