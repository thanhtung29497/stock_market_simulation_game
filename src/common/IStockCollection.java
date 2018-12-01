package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public interface IStockCollection extends Serializable {
	public void addStock(IStock stock, int quantity);	
	public void updateStockPrice(HashMap<String, Double> prices);
	public ArrayList<IStock> toArray();
	public int getStockQuantity(String stockCode);
	public IStock getStock(String stockCode);
	public double compareWithLastPrice(String stockCode); // return percentage of increasing compared with last price
	public void addQuantity(String stockCode, Integer quantity);
	public Boolean hasStockCode(String stockCode);
	public double getTotalStockValue();
	public Boolean hasCompanyName(String companyName);
	public void resetPrice();
	public Boolean wasChanged();
	public int size();
}
