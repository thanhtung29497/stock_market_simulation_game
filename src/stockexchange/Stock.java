package stockexchange;

import common.IStock;

public class Stock implements IStock {
	
	private static final long serialVersionUID = 1L;
	private String code;
	private int quantity;
	private double price;
	private String companyName;

	public Stock(String code, int quantity, double price, String companyName) {
		this.code = code;
		this.quantity = quantity;
		this.price = price;
		this.companyName = companyName;
	}
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String getCompanyName() {
		return this.companyName;
	}

}
