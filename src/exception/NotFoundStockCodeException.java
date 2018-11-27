package exception;

public class NotFoundStockCodeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundStockCodeException(String stockCode) {
		System.out.println("Stock code " + stockCode + " doesn't exist");
	}
}
