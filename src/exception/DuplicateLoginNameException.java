package exception;

public class DuplicateLoginNameException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateLoginNameException(String name) {
		System.out.println("Login name \"" + name + "\" has been already used!");
	}
}
