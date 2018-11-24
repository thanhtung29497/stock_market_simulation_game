package common;

import java.io.Serializable;

public interface IStockOwner extends Serializable {
	public void processBid(IBid bid);
}
