package exception;

public class NotFoundBidException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotFoundBidException(int id) {
		System.out.println("Bid with id " + id + " doesn't exist");
	}
}
