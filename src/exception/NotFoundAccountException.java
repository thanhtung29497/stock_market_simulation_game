package exception;

public class NotFoundAccountException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundAccountException(String name) {
		System.out.println("Account with name \"" + name + "\n doesn't exist!");
	}
}
