package stockexchange;

import java.time.Duration;
import java.util.TimerTask;

import common.IBidCollection;
import common.IStockCollection;
import ui.server.StockExchangeFrame;

public class ViewUpdatingTask extends TimerTask{

	private StockExchangeManager manager;
	private StockExchangeFrame view;
	
	@Override
	public void run() {
		IStockCollection stocks = this.manager.getStocks();
		IBidCollection bids = this.manager.getBids();
		if (stocks.wasChanged() || bids.wasChanged()) {
			this.view.showStocks(stocks, bids);
		}
		Duration currentTime = this.manager.getCurrentTime();
		int minute = (int)currentTime.toMinutes() % 60;
		int second = (int)currentTime.getSeconds() % 60;
		this.view.showTime(minute, second);
		this.view.updateRank(this.manager.getRankBoard());
	}
	
	public ViewUpdatingTask (StockExchangeManager manager, StockExchangeFrame view) {
		this.manager = manager;
		this.view = view;
	}
	
}
