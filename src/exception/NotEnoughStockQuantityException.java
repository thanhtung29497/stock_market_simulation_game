package exception;

public class NotEnoughStockQuantityException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NotEnoughStockQuantityException(int ownQuantity, int offerQuantity, String stockCode) {
		System.out.println("You have " + ownQuantity + " stock " + stockCode + " not enough to sell " + offerQuantity);
	}
}
