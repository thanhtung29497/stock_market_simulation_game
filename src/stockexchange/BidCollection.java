package stockexchange;

import java.util.ArrayList;
import java.util.HashMap;

import common.BidStatus;
import common.BidType;
import common.IBid;
import common.IBidCollection;
import exception.NotFoundBidException;

public class BidCollection implements IBidCollection {

	private static final long serialVersionUID = 1L;
	private HashMap<Integer, IBid> bids;

	public BidCollection() {
		this.bids = new HashMap<>();
	}
	
	@Override
	public ArrayList<IBid> getAllBids() {
		ArrayList<IBid> results = new ArrayList<>();
		for (IBid bid: this.bids.values()) {
			results.add(bid);
		}
		return results;
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

	@Override
	public void addBid(IBid bid) {
		this.bids.put(bid.getId(), bid);
	}

	@Override
	public IBid getBidById(int id) throws NotFoundBidException{
		if (!this.bids.containsKey(id)) {
			throw new NotFoundBidException(id);
		}
		return this.bids.get(id);
	}

	@Override
	public void changeBidStatus(int id, BidStatus status) throws NotFoundBidException {
		if (!this.bids.containsKey(id)) {
			throw new NotFoundBidException(id);
		}
		
		IBid bid = this.bids.get(id);
		bid.changeStatus(status);
		this.bids.put(id, bid);
	}

}
