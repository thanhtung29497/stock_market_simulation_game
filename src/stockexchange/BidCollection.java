package stockexchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Predicate;

import common.BidStatus;
import common.BidType;
import common.IBid;
import common.IBidCollection;
import exception.NotFoundBidException;

public class BidCollection implements IBidCollection {
	
	private interface SerializablePredicate<T> extends Predicate<T>, Serializable {};
	private interface SerializableComparator<T> extends Comparator<T>, Serializable {};
	
	private final Comparator<IBid> buyBidComparator = (SerializableComparator<IBid>)((IBid bid0, IBid bid1)
			-> ((Double)(bid0.getOfferPrice() - bid1.getOfferPrice())).intValue());
	private final Comparator<IBid> sellBidComparator = (SerializableComparator<IBid>)((IBid bid0, IBid bid1)
			-> ((Double)(bid1.getOfferPrice() - bid0.getOfferPrice())).intValue());

	private final Predicate<IBid> buyBidFilter = (SerializablePredicate<IBid>)((IBid bid) -> bid.getType() == BidType.Buy);
	private final Predicate<IBid> sellBidFilter = (SerializablePredicate<IBid>)((IBid bid) -> bid.getType() == BidType.Sell);
	
		
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, IBid> bids;
	private IBid lastestMatchedBid;

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
	public ArrayList<IBid> getTopBids(BidType type, String stockCode, int number) {
		ArrayList<IBid> bids = this.getAllBids();
		if (type == BidType.Buy) {
			bids.removeIf(this.sellBidFilter);
			bids.sort(this.buyBidComparator);
		} else {
			bids.removeIf(this.buyBidFilter);
			bids.sort(this.sellBidComparator);
		}
		
		if (bids.size() < number) {
			return bids;
		}
		return new ArrayList<>(bids.subList(0, number - 1));
	}

	@Override
	public IBid getLatestMatchedBid(String stockCode) {
		return this.lastestMatchedBid;
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
		if (status == BidStatus.Matched) {
			this.lastestMatchedBid = bid;
		}
		this.bids.put(id, bid);
	}

	@Override
	public void clear() {
		this.bids.clear();
	}

	@Override
	public ArrayList<IBid> getBidsByStockCode(String stockCode) {
		ArrayList<IBid> result = new ArrayList<>();
		for (IBid bid: this.bids.values()) {
			if (bid.getStock().getCode().equals(stockCode)) {
				result.add(bid);
			}
		}
		return result;
	}

}
