package common;

import java.io.Serializable;

public interface IStock extends Serializable {
	public String getCode();
	public double getPrice();
	public void setPrice(double price);
	public String getCompanyName();
	public double getCapPrice();
	public double getFloorPrice();
}
