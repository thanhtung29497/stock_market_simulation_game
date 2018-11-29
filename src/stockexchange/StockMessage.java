package stockexchange;

import common.IStockCollection;
import common.IStockMessage;
import common.Message;
import common.MessageType;

public class StockMessage extends Message implements IStockMessage {

	private IStockCollection stocks;
	private static final long serialVersionUID = 1L;

	public StockMessage(MessageType type, String message, IStockCollection stocks) {
		super(type, message);
		this.stocks = stocks;
	}

	@Override
	public IStockCollection getStocks() {
		return this.stocks;
	}
	
}
