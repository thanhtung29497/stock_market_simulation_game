package common;

import java.io.Serializable;

public interface IStock extends Serializable {
	public String getCode();
	public int getQuantity();
	public double getPrice();
	public void setQuantity(int quantity);
	public void setPrice(double price);
	public String getCompanyName();
}
