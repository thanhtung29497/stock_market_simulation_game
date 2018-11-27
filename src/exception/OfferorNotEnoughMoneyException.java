package exception;

public class OfferorNotEnoughMoneyException extends Exception {

	private String offerorName;
	private static final long serialVersionUID = 1L;
	
	public OfferorNotEnoughMoneyException(String offerorName) {
		this.offerorName = offerorName;
		System.out.println("Offeror " + offerorName + " doesn't have enough money to make transaction");
	}
	
	public String getOfferorName() {
		return this.offerorName;
	}
}
