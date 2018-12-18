package Synchronization.demo7;

import java.util.Scanner;

/*	Prvo ce se izvrsiti produce metoda jer u consume metodi imamo sleep(3000) te niti.
	U produce ce se niti zablokirati pozivom wait() i cekace da ga nit koja isto radi na
	this objektu ga odblokira pozivom notify().*/

public class Processor {

	public void produce() throws InterruptedException {
		synchronized (this) {
			System.out.println("Pruducer thread running...");
			wait();
			System.out.println("Resumed...");
		}
	}
	
	public void consume() throws InterruptedException {
		
		Scanner scanner = new Scanner(System.in);
		Thread.sleep(2000);		// stavili smo ovaj sleep da bi thread conusem prvi krenuo
		
		synchronized (this) {
			System.out.println("Press ENTER to continue...");
			scanner.nextLine();
			System.out.println("Return key pressed.");
			notify();				// notify se moze zvati samo iz syncronized bloka
			//Thread.sleep(5000);  	//ovaj sleep ce se izvrsiti pa ce tek onda ici u produce
		}
	}
}
