package Synchronization.demo11;

import java.util.concurrent.Semaphore;

public class Connection {

	private static Connection instance = new Connection();
	
	private int connections = 0;
	
	//limitiramo niti, tako da u moze u trenutku najvise 10 da ih se odvija
	// ovo true znaci da ce vaziti FIFO pravilo
	private Semaphore semaphore = new Semaphore(10, true);
	
	private Connection() {
		
	}
	
	public static Connection getInstance() {
		return instance;
	}
	
	public void connect() throws Exception {
		
		//
		semaphore.acquire();
		
		try {
			
			synchronized (this) {
				connections++;
				System.out.println("Current connections " + connections);
			}
			
			//simuliramo neki rad
			Thread.sleep(200);
			
			//ovo dekrementiranje imitira release f-ja semafora
			synchronized (this) {
				connections--;
			}
			
		} finally {
			semaphore.release();
		}	
	}
	
}
