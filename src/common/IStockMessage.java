package common;

import java.util.ArrayList;

public interface IStockMessage extends IMessage {
	ArrayList<IStock> getStocks();
}
