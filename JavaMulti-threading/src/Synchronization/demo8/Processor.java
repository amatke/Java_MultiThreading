package Synchronization.demo8;

import java.util.LinkedList;
import java.util.Random;



	
public class Processor {
	
	//koristimo LinkedList da bismo mogli da implementiramo FIFO
	LinkedList<Integer> list = new LinkedList<Integer>();
	
	Object lock = new Object();
	
	//max velicina liste
	static final int LIST_LIMIT = 10;
	
	
	/**	Produce metoda dodaje u nasu listu, samo ako nije puno, value inkrementira svaki put, i obavestava da je zavrsila.
	Ako je lista puna objekat lock se zakljuca pozivajuci lock.wait(). 
	Tako zakljucan ceka da ga iz produce metode otkljuca metoda lock.notify().*/
	public void produce() throws InterruptedException {
		
		int value = 0;
		
		while(true) {
			
			synchronized (lock) {
				
				//ako je lista puna cekamo...
				while(list.size() == LIST_LIMIT) {
					System.err.println("<==== LIST IS FULL ====>");
					lock.wait();
				}
				
				list.add(value++);
				lock.notify();
			}
		}
	}
	
	/**Consume metoda uzima int-ove, samo ukoliko lista nije prazna, i obavestava lock.notify() kada zavrsi sa radom.
	Imamo Thread.sleep(random.nextInt(100));  pa ce consume metoda uvek biti sporija od produce.*/
	public void consume() throws InterruptedException {
		
		Random random = new Random();
		
		while(true) {
			
			synchronized (lock) {
				
				//ako je lista prazna cekamo...
				while(list.isEmpty()) {
					System.err.println("<==== LIST IS EMPTY ====>");
					lock.wait();
				}
				
				int size = list.size();
				int val = list.removeFirst();		//fifo - First out!
				System.out.println("List size: " + size + "; value: " + val);
				
				lock.notify();
			}
			
			
			Thread.sleep(random.nextInt(1000));
		}
	}
}
