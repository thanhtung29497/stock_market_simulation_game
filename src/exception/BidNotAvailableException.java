package exception;

public class BidNotAvailableException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BidNotAvailableException(int bidId) {
		System.out.println("Bid with id " + bidId + " is not available");
	}
}
