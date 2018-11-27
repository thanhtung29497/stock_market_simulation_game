package exception;

public class OutOfStockPriceRangeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OutOfStockPriceRangeException(double price, String stockCode) {
		System.out.println("Price " + price + " is out of price range of stock " + stockCode);
	}
}
