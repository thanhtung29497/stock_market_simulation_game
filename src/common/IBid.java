package common;

public interface IBid {
	public BidType getBidType();
	public IStock getStock();
	public String getTransactorName();
	public int getQuantity();
	public double getOfferPrice();
}
