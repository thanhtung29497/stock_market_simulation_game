package common;

import java.io.Serializable;
import java.util.ArrayList;

public interface IBidCollection extends Serializable {
	public ArrayList<IBid> getAllBids();
	public IBid getBestBid(BidType type, String stockCode);
	public IBid getLatestMatchedBid(String stockCode);
}
