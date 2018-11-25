package stockexchange;

import java.util.HashMap;
import java.util.Random;
import java.util.TimerTask;

import common.IStock;

public class StockPriceAdjustmentTask extends TimerTask {

	private StockExchangeManager stockExchangeManager;
	private final Double PRICE_PERCENT_RANGE = 0.1; 
	
	@Override
	public void run() {
		Random rand = new Random();
		HashMap<String, Double> stockPrices = new HashMap<>();
		for (IStock stock: this.stockExchangeManager.getStocks().toArray()) {
			Double currentPrice = stock.getPrice();
			Double floorPrice = currentPrice * (1 - this.PRICE_PERCENT_RANGE);
			Double priceRange = currentPrice * this.PRICE_PERCENT_RANGE * 2;
			stockPrices.put(stock.getCode(), floorPrice + rand.nextInt(priceRange.intValue() + 1));
		}
		
		this.stockExchangeManager.adjustStockPrice(stockPrices);
		
	}
	
	public StockPriceAdjustmentTask(StockExchangeManager stockExchangeManager) {
		this.stockExchangeManager = stockExchangeManager;
	}
	
}
