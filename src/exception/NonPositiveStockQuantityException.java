package exception;

public class NonPositiveStockQuantityException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NonPositiveStockQuantityException() {
		System.out.println("The quantity of stock must be positive");
	}
}
