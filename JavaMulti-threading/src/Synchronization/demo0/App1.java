package Synchronization.demo0;

class Runner1 extends Thread{

	public void run() {
		for(int i=0; i<10; i++) {
			System.out.println("Hello " + i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

/*Prvi nacin za pokretanje treda*/
public class App1 {

	public static void main(String[] args) {
		Runner1 r1 = new Runner1();
		Runner1 r2 = new Runner1();
		
		r1.start();
		r2.start();
	}
}
