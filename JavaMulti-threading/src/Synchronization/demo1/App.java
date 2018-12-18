package Synchronization.demo1;

import java.util.Scanner;

class Processor extends Thread{
	
	// volatile sprecava da tredovi kesiraju promenjive kada se one ne menjaju
	// mada ovde ce rezultat biti isti i bez volatile
	// sprecava da kompajleri optimizuju kod
	public volatile boolean RUNNING = true;
	
	@Override
	public void run() {
		while(RUNNING) {
			System.out.println("Hello");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void shutDown() {
		RUNNING = false;
	}
}

public class App {

	public static void main(String[] args) {
		Processor proc1 = new Processor();
		
		proc1.start();
		
		System.out.println("Press Enter to stop ");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		
		proc1.shutDown();
		
	}
}
