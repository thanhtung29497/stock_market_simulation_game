package stockexchange;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Random;
import java.util.TimerTask;

import common.Convention;
import common.IStock;
import exception.NotFoundAccountException;

public class StockPriceAdjustmentTask extends TimerTask {

	private StockExchangeManager stockExchangeManager;
	
	@Override
	public void run() {
		Random rand = new Random();
		HashMap<String, Double> stockPrices = new HashMap<>();
		for (IStock stock: this.stockExchangeManager.getStocks().toArray()) {
			Double currentPrice = stock.getPrice();
			Double floorPrice = currentPrice * (1 - Convention.STOCK_PERCENT_RANGE);
			Double priceRange = currentPrice * Convention.STOCK_PERCENT_RANGE * 2;
			stockPrices.put(stock.getCode(), floorPrice + rand.nextInt(priceRange.intValue() + 1));
		}
		
		try {
			this.stockExchangeManager.adjustStockPrice(stockPrices);
			this.stockExchangeManager.closeSession();
		} catch (RemoteException e) {
			System.out.println("Failed to connect to bank server");
		} catch (NotFoundAccountException e) {
			System.out.println("Something wrong with accounts");
		}
		
	}
	
	public StockPriceAdjustmentTask(StockExchangeManager stockExchangeManager) {
		this.stockExchangeManager = stockExchangeManager;
	}
	
}
