package stockexchange;

import common.Convention;
import common.IStock;

public class Stock implements IStock {
	
	private static final long serialVersionUID = 1L;
	private String code;
	private double price;
	private String companyName;

	public Stock(String code, double price, String companyName) {
		this.code = code;
		this.price = price;
		this.companyName = companyName;
	}
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public double getPrice() {
		return price;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String getCompanyName() {
		return this.companyName;
	}

	@Override
	public double getCapPrice() {
		return this.price * (1 + Convention.STOCK_PERCENT_RANGE);
	}

	@Override
	public double getFloorPrice() {
		return this.price * (1 - Convention.STOCK_PERCENT_RANGE);
	}

	@Override
	public Boolean isOutOfPriceRange(double price) {
		return price > this.getCapPrice() || price < this.getFloorPrice();
	}

}
