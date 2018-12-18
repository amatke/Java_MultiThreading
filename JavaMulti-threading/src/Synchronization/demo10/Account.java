package Synchronization.demo10;

public class Account {

	private int balance = 10000;
	
	public void deposit(int amount) {
		this.balance += amount;
	}
	
	public void withDraw(int amount) {
		this.balance -= amount;
	}
	
	public int getBalance() {
		return balance;
	}
	
	/**ac1 is sending amount to ac2*/
	public static void transfer(Account ac1, Account ac2, int amount) {
		ac1.withDraw(amount);
		ac2.deposit(amount);
	}
}
