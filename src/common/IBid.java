package common;

import java.io.Serializable;

public interface IBid extends Serializable{
	public int getId();
	public BidType getType();
	public IStock getStock();
	public String getOfferorName();
	public int getQuantity();
	public double getOfferPrice();
	public BidStatus getStatus();
	public void changeStatus(BidStatus status);
	public double getValue();
}
