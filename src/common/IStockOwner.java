package common;

import java.io.Serializable;
import java.util.ArrayList;

public interface IStockOwner extends Serializable {
	public ArrayList<IStock> getStocks();
	public void processBid(IBid bid);
}
