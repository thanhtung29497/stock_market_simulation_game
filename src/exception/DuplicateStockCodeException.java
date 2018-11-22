package exception;

public class DuplicateStockCodeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateStockCodeException(String stockCode) {
		System.out.println("Stock Code \"" + "\" has been issued");
	}
}
