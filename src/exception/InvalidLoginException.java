package exception;

public class InvalidLoginException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5665319979673453809L;

	public InvalidLoginException() {
		System.out.print("Invalid name or password");
	}
}
