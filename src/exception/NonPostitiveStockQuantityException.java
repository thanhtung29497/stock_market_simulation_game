package exception;

public class NonPostitiveStockQuantityException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NonPostitiveStockQuantityException() {
		System.out.println("The quantity of stock must be positive");
	}
}
