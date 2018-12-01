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
	
	private Comparator<IBid> buyBidComparator = (SerializableComparator<IBid>)((IBid bid0, IBid bid1)
			-> (bid0.getOfferPrice() < bid1.getOfferPrice() ? 1 : -1));
	private Comparator<IBid> sellBidComparator = (SerializableComparator<IBid>)((IBid bid0, IBid bid1)
			-> (bid0.getOfferPrice() > bid1.getOfferPrice() ? 1 : -1));
	private Comparator<IBid> topBidComparator = (SerializableComparator<IBid>)((IBid bid0, IBid bid1)
			-> {
				double standard0 = bid0.getType() == BidType.Buy ? (bid0.getOfferPrice() - bid0.getStock().getPrice()) 
						: (bid0.getStock().getPrice() - bid0.getOfferPrice());
				double standard1 = bid1.getType() == BidType.Buy ? (bid1.getOfferPrice() - bid1.getStock().getPrice()) 
						: (bid1.getStock().getPrice() - bid1.getOfferPrice());
				return standard0 < standard1 ? 1 : -1;
			});
	
	private final Predicate<IBid> buyBidFilter = (SerializablePredicate<IBid>)((IBid bid) -> bid.getType() == BidType.Buy);
	private final Predicate<IBid> sellBidFilter = (SerializablePredicate<IBid>)((IBid bid) -> bid.getType() == BidType.Sell);
	private final Predicate<IBid> matchedBidFilter = (SerializablePredicate<IBid>)((IBid bid) -> bid.getStatus() == BidStatus.Matched);
		
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, IBid> bids;
	private HashMap<String, IBid> lastestMatchedBid;
	private Boolean hasChanged;

	public BidCollection() {
		this.bids = new HashMap<>();
		this.lastestMatchedBid = new HashMap<>();
		this.hasChanged = false;
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
		ArrayList<IBid> bids = this.getBidsByStockCode(stockCode);
		if (bids.isEmpty()) {
			return bids;
		}
		bids.removeIf(this.matchedBidFilter);
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
		return new ArrayList<>(bids.subList(0, number));
	}

	@Override
	public IBid getLatestMatchedBid(String stockCode) {
		return this.lastestMatchedBid.get(stockCode);
	}

	@Override
	public void addBid(IBid bid) {
		this.bids.put(bid.getId(), bid);
		this.hasChanged = true;
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
			this.lastestMatchedBid.put(bid.getStock().getCode(), bid);
		}
		this.bids.put(id, bid);
		this.hasChanged = true;
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

	@Override
	public ArrayList<IBid> getTopBids(String stockCode, int number) {
		ArrayList<IBid> topBuyBids = this.getTopBids(BidType.Buy, stockCode, number);
		ArrayList<IBid> topSellBids = this.getTopBids(BidType.Sell, stockCode, number);
		topBuyBids.addAll(topSellBids);
		return topBuyBids;
	}

	@Override
	public ArrayList<IBid> getAllTopBids(int number) {
		ArrayList<IBid> result = this.getAllBids();
		result.sort(this.topBidComparator);
		if (result.size() < number) {
			return result;
		}
		
		return new ArrayList<>(result.subList(0, number));
	}

	@Override
	public Boolean wasChanged() {
		Boolean result = this.hasChanged;
		this.hasChanged = false;
		return result;
	}

	@Override
	public void reset() {
		this.lastestMatchedBid = new HashMap<>();
		this.bids = new HashMap<>();
		this.hasChanged = true;
	}

}
