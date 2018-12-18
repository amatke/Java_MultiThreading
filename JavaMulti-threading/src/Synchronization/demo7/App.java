package Synchronization.demo7;

public class App {

	/*Ovde pokrecemo dve niti i cekamo da obe zavrse posao.
	Jedna nit radi produce metodu a druga consume.*/
	
	public static void main(String[] args) throws InterruptedException {
		
		Processor processor = new Processor();

		
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					processor.produce();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					processor.consume();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
	}
}
