package stockexchange;

import java.util.ArrayList;

import common.BidType;
import common.IBid;
import common.IBidCollection;

public class BidCollection implements IBidCollection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BidCollection(ArrayList<IBid> bids) {
		
	}
	
	@Override
	public ArrayList<IBid> getAllBids() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBid getBestBid(BidType type, String stockCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBid getLatestMatchedBid(String stockCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
