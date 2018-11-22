package exception;

public class NotEnoughMoneyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotEnoughMoneyException() {
		System.out.println("Your account has not enough money to make transaction!");
	}
}
