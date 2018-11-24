package stockexchange;

import java.util.ArrayList;

import common.IStock;
import common.IStockMessage;
import common.Message;
import common.MessageType;

public class StockMessage extends Message implements IStockMessage {

	private ArrayList<IStock> stocks;
	private static final long serialVersionUID = 1L;

	public StockMessage(MessageType type, String message, ArrayList<IStock> stocks) {
		super(message, type);
		this.stocks = stocks;
	}

	@Override
	public ArrayList<IStock> getStocks() {
		return this.stocks;
	}
	
}
