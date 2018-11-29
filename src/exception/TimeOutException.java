package exception;

public class TimeOutException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public TimeOutException() {
		System.out.println("Time is out!");
	}
}
