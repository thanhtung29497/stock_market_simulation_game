package stockexchange;

import common.BidStatus;
import common.BidType;
import common.IBid;
import common.IStock;
import common.Utility;

public class Bid implements IBid {
	
	private static final long serialVersionUID = 1L;
	private final int id;
	private IStock stock;
	private String transactorName;
	private BidType type;
	private int quantity;
	private double offerPrice;
	private BidStatus status;

	public Bid(BidType type, IStock stock, String transactorName, int quantity, double offerPrice) {
		this.id = Utility.generateBidId();
		this.status = BidStatus.Available;
		this.type = type;
		this.stock = stock;
		this.transactorName = transactorName;
		this.quantity = quantity;
		this.offerPrice = offerPrice;
	}
	
	@Override
	public BidType getType() {
		return this.type;
	}

	@Override
	public IStock getStock() {
		return this.stock;
	}

	@Override
	public String getOfferorName() {
		return this.transactorName;
	}

	@Override
	public int getQuantity() {
		return this.quantity;
	}

	@Override
	public double getOfferPrice() {
		return this.offerPrice;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public BidStatus getStatus() {
		return this.status;
	}

	@Override
	public void changeStatus(BidStatus status) {
		this.status = status;
	}

	@Override
	public double getValue() {
		return this.offerPrice * this.quantity;
	}

}
