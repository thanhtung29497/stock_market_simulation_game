package stockexchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import common.Convention;
import common.IStock;
import common.IStockCollection;

public class StockCollection implements IStockCollection {
	
	private class StockInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public StockInfo(IStock stock, int quantity) {
			this.stock = stock;
			this.quantity = quantity;
			this.lastPrice = null;
		};
		public IStock stock;
		public int quantity;
		public Double lastPrice;
	}

	private HashMap<String, StockInfo> stockMap;
	private static final long serialVersionUID = 1L;
	
	public StockCollection() {
		this.stockMap = new HashMap<>();
	}
	
	public StockCollection(IStock stock, int quantity) {
		this.stockMap = new HashMap<>();
		this.stockMap.put(stock.getCode(), new StockInfo(stock, quantity));
	}

	@Override
	public ArrayList<IStock> toArray() {
		ArrayList<IStock> stocks = new ArrayList<>();
		for (StockInfo stockInfo: this.stockMap.values()) {
			stocks.add(stockInfo.stock);
		}
		return stocks;
	}

	@Override
	public int getStockQuantity(String stockCode) {
		if (this.hasStockCode(stockCode)) {
			return this.stockMap.get(stockCode).quantity;
		} else {
			return 0;
		}
		
	}

	@Override
	public IStock getStock(String stockCode) {
		if (this.hasStockCode(stockCode)) {
			return this.stockMap.get(stockCode).stock;
		} else {
			return null;
		}
	}

	@Override
	public double compareWithLastPrice(String stockCode) {
		if (this.hasStockCode(stockCode)) {
			StockInfo stockInfo = this.stockMap.get(stockCode);
			double currentPrice = stockInfo.stock.getPrice();
			Double lastPrice = stockInfo.lastPrice;
			if (lastPrice != null) {
				return (currentPrice - lastPrice) / lastPrice * 100;
			}
		}
		return 0.0;
	}

	@Override
	public void addQuantity(String stockCode, Integer quantity) {
		if (this.hasStockCode(stockCode)) {
			StockInfo stockInfo = this.stockMap.get(stockCode);
			stockInfo.quantity += quantity;
			this.stockMap.put(stockCode, stockInfo);
		}
	}

	@Override
	public Boolean hasStockCode(String stockCode) {
		return this.stockMap.containsKey(stockCode);
	}

	@Override
	public void addStock(IStock stock, int quantity) {
		this.stockMap.put(stock.getCode(), new StockInfo(stock, quantity));
	}

	@Override
	public void updateStockPrice(HashMap<String, Double> prices) {
		for (String stockCode: prices.keySet()) {
			Double price = prices.get(stockCode);
			if (this.hasStockCode(stockCode)) {
				StockInfo stockInfo = this.stockMap.get(stockCode);
				stockInfo.lastPrice = stockInfo.stock.getPrice();
				stockInfo.stock.setPrice(price);
				this.stockMap.put(stockCode, stockInfo);
			}
		}
	}

	@Override
	public double getTotalStockValue() {
		double totalValue = 0.0;
		for (StockInfo stockInfo: this.stockMap.values()) {
			totalValue += stockInfo.stock.getPrice() * stockInfo.quantity;
		}
		return totalValue;
	}

	@Override
	public Boolean hasCompanyName(String companyName) {
		for (StockInfo stockInfo: this.stockMap.values()) {
			if (stockInfo.stock.getCompanyName().equals(companyName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void resetPrice() {
		for (String key: this.stockMap.keySet()) {
			StockInfo stockInfo = this.stockMap.get(key);
			stockInfo.stock.setPrice(Convention.INITIAL_SHARE_PRICE);
			this.stockMap.put(key, stockInfo);
		}
	}

}
