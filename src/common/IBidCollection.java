package common;

import java.io.Serializable;
import java.util.ArrayList;

import exception.NotFoundBidException;

public interface IBidCollection extends Serializable {
	public ArrayList<IBid> getAllBids();
	public IBid getBestBid(BidType type, String stockCode);
	public IBid getLatestMatchedBid(String stockCode);
	public void addBid(IBid bid);
	public IBid getBidById(int id) throws NotFoundBidException;
	public void changeBidStatus(int id, BidStatus status) throws NotFoundBidException;
}
