package stockexchange;

import java.rmi.RemoteException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import common.BidStatus;
import common.BidType;
import common.Convention;
import common.IAccount;
import common.IBankController;
import common.IBid;
import common.IBidCollection;
import common.IMessage;
import common.IRankCollection;
import common.IStock;
import common.IStockCollection;
import common.MessageType;
import common.Utility;
import exception.BidNotAvailableException;
import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;
import exception.InvalidLoginException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.NotFoundStockCodeException;
import exception.OfferorNotEnoughMoneyException;
import exception.OutOfStockPriceRangeException;

public class StockExchangeManager {
	
	private IBankController bankController;
	private IStockCollection stocks;
	private IBidCollection bids;
	private IRankCollection rankBoard;
	private HashMap<String, ArrayList<IMessage>> messages;
	private HashMap<String, IStockCollection> playerStocks;
	private LocalTime startMilestone;
	private HashMap<String, String> idToName;
	private HashMap<String, String> nameToId;

	public StockExchangeManager(IBankController bankController) {
		this.bankController = bankController;
		this.stocks = new StockCollection();
		this.messages = new HashMap<>();
		this.playerStocks = new HashMap<>();
		this.bids = new BidCollection();
		this.rankBoard = new RankCollection();
		this.idToName = new HashMap<>();
		this.nameToId = new HashMap<>();
		try {
			this.start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start() throws RemoteException {
		this.startMilestone = LocalTime.now();
		this.bankController.start();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new StockPriceAdjustmentTask(this), Convention.STOCK_EXCHANGE_SESSION, Convention.STOCK_EXCHANGE_SESSION);
	}
	
	public Duration getCurrentTime() {
		long duration = Duration.between(this.startMilestone, LocalTime.now()).toMillis();
		long currentTime = Convention.GAME_DURATION - duration; 
		return Duration.ofMillis(currentTime);
	}
	
	public void closeSession() {
		this.bids.clear();
	}
	
	public void addMessageByKey(String key, IMessage message) {
		ArrayList<IMessage> messages = this.messages.get(key);
		messages.add(message);
		this.messages.put(key, messages);
	}
	
	public IStock issueStock(String companyName, String stockCode) throws DuplicateStockCodeException, DuplicateCompanyNameException {
		if (this.stocks.hasStockCode(stockCode)) {
			throw new DuplicateStockCodeException(stockCode);
		}
		if (this.stocks.hasCompanyName(companyName)) {
			throw new DuplicateCompanyNameException(companyName);
		}
		Stock stock = new Stock(stockCode, Convention.INITIAL_SHARE_PRICE, companyName);
		this.stocks.addStock(stock, Convention.INITIAL_SHARE_NUMBER);
		
		for (String key: this.messages.keySet()) {
			if (Utility.isPlayerId(key)) {
				this.addMessageByKey(key, new StockMessage(MessageType.IssueStock,
						"New stock was issued: " + stock.getCode(), 
						new StockCollection(stock, Convention.INITIAL_SHARE_NUMBER)));
			}
		}
		
		this.messages.put(Utility.COMPANY_ID_PREFIX + stockCode, new ArrayList<>());
		
		return stock;
	}
	
	public void registerPlayer(String playerId) throws RemoteException, InvalidLoginException, NotFoundAccountException {
		IAccount account = this.bankController.login(playerId);
		String accountId = account.getId();
		this.rankBoard.addPlayerIfAbsent(account.getName(), account.getBalance());
		this.playerStocks.putIfAbsent(accountId, new StockCollection());
		this.messages.putIfAbsent(accountId, new ArrayList<>());
		this.idToName.put(accountId, account.getName());
		this.nameToId.put(account.getName(), accountId);
		for (String key: this.messages.keySet()) {
			this.addMessageByKey(key, new RankMessage(MessageType.UpdateRank,
					"Rank board was updated", this.rankBoard));
		}
	}
	
	public ArrayList<IMessage> retrieveMessages(String key) {
		ArrayList<IMessage> messages = this.messages.get(key);
		this.messages.put(key, new ArrayList<>());
		return messages;
	}
	
	public IStockCollection getStocks() {
		return this.stocks;
	}
	
	public IStockCollection getPlayerStock(String playerId) {
		return this.playerStocks.get(playerId);
	}	
	
	public IRankCollection getRankBoard() {
		return this.rankBoard;
	}
	
	public void adjustStockPrice(HashMap<String, Double> stockPrices) throws RemoteException, NotFoundAccountException {
		this.stocks.updateStockPrice(stockPrices);
		
		HashMap<String, Double> amounts = new HashMap<>();
		for (String playerId: this.playerStocks.keySet()) {
			IStockCollection playerStock = this.playerStocks.get(playerId);
			playerStock.updateStockPrice(stockPrices);
			amounts.put(this.idToName.get(playerId), playerStock.getTotalStockValue() + this.getTotalAmount(playerId));
		}
		this.rankBoard.updateAllAmounts(amounts);
		
		for (String key: this.messages.keySet()) {
			this.addMessageByKey(key, new StockMessage(MessageType.AdjustStockPrice, 
					"All stock prices were adjusted periodically!", this.getStocks()));
			this.addMessageByKey(key, new RankMessage(MessageType.UpdateRank,
					"Rank board was updated", this.rankBoard));
		}
	}
	
	public IBidCollection getBids() {
		return this.bids;
	}
	
	public double getTotalAmount(String playerId) throws RemoteException, NotFoundAccountException {
		double balance = this.bankController.getBalanceById(playerId);
		IStockCollection playerStock = this.getPlayerStock(playerId);
		double stockValue = playerStock.getTotalStockValue();
		return balance + stockValue;
	}
	
	public Boolean isOutOfStockPriceRange(String stockCode, double offerPrice) {
		IStock stock = this.stocks.getStock(stockCode);
		return stock.isOutOfPriceRange(offerPrice);
	}
	
	private void updateStockQuantity(String playerId, String stockCode, int quantity) {
		IStockCollection playerStock = this.playerStocks.get(playerId);
		if (quantity > 0 && !playerStock.hasStockCode(stockCode)) {
			playerStock.addStock(this.stocks.getStock(stockCode), 0);
		}
		playerStock.addQuantity(stockCode, quantity);
		this.playerStocks.put(playerId, playerStock);
	}
	
	public void postBid(BidType type, String stockCode, int quantity, double offerPrice, String offerorId) 
			throws NotFoundStockCodeException, NotFoundAccountException, OutOfStockPriceRangeException, RemoteException, NotEnoughMoneyException, NotEnoughStockQuantityException {
		
		// check if bid is valid
		
		// valid stock code ?
		if (!this.stocks.hasStockCode(stockCode)) {
			throw new NotFoundStockCodeException(stockCode);
		}
		
		// valid account name ?
		if (!this.playerStocks.containsKey(offerorId)) {
			throw new NotFoundAccountException(offerorId);
		}
		
		// offer price is in range ?
		if (this.isOutOfStockPriceRange(stockCode, offerPrice)) {
			throw new OutOfStockPriceRangeException(offerPrice, stockCode);
		}
		IStock stock = this.stocks.getStock(stockCode);
		
		// buyer has enough money in bank account ?
		if (type == BidType.Buy) {
			double offerorBalance = this.bankController.getBalanceById(offerorId);
			if (offerorBalance < offerPrice * quantity) {
				throw new NotEnoughMoneyException();
			}
		} else {
		// seller has enough quantity of stock ?
			IStockCollection offerorStock = this.playerStocks.get(offerorId);
			int offerorStockQuantity = offerorStock.getStockQuantity(stockCode);
			if (offerorStockQuantity < quantity) {
				throw new NotEnoughStockQuantityException(offerorStockQuantity, quantity, stockCode);
			}
		}
		
		// update bid
		Bid bid = new Bid(type, stock, this.idToName.get(offerorId), quantity, offerPrice);
		this.bids.addBid(bid);
		
		// notify all players and companies
		this.addMessageByKey(offerorId, new BidMessage(MessageType.PostBid, "Your bid was succesfully posted"));
		
		for (String key: this.messages.keySet()) {
			if (key.equals(offerorId)) {
				continue;
			}
			this.addMessageByKey(key, new BidMessage(MessageType.UpdateBid,
					"New bid was posted!", this.bids));
		}
	}
	
	public void acceptBid(int bidId, String offereeId) 
			throws NotFoundBidException, BidNotAvailableException, NotFoundAccountException, NotEnoughStockQuantityException, RemoteException, NotEnoughMoneyException, OfferorNotEnoughMoneyException {
		
		IBid bid = this.bids.getBidById(bidId);
		
		if (bid.getStatus() != BidStatus.Available) {
			throw new BidNotAvailableException(bidId);
		}
		if (!this.playerStocks.containsKey(offereeId)) {
			throw new NotFoundAccountException(offereeId);
		}
		
		IStock bidStock = bid.getStock();
		String stockCode = bidStock.getCode();
		String offerorName = bid.getOfferorName();
		String offerorId = this.nameToId.get(offerorName);
		int bidQuantity = bid.getQuantity();
		double bidValue = bid.getValue();
		BidType bidType = bid.getType();
		
		if (bidType == BidType.Buy) {
			IStockCollection acceptorStock = this.playerStocks.get(offereeId);
			int offereeStockQuantity = acceptorStock.getStockQuantity(stockCode);
			if (offereeStockQuantity < bidQuantity) {
				throw new NotEnoughStockQuantityException(offereeStockQuantity, 
						bid.getQuantity(), stockCode);
			}
			double offerorBalance = this.bankController.getBalanceById(offerorName);
			if (offerorBalance < bidValue) {
				throw new OfferorNotEnoughMoneyException(offerorName);
			}
		} else {
			double offereeBalance = this.bankController.getBalanceById(offereeId);
			if (offereeBalance < bidValue) {
				throw new NotEnoughMoneyException();
			}
		}
				
		if (bidType == BidType.Buy) {
			this.bankController.makeTransaction(offerorId, offereeId , bidId, bid.getValue());
			this.updateStockQuantity(offerorId, stockCode, bidQuantity);
			this.updateStockQuantity(offereeId, stockCode, -bidQuantity);
			this.addMessageByKey(offerorId, new BidMessage(MessageType.MatchBid,
					"Number of stock " + stockCode + " was added by " + bidQuantity + " due to bid " + bidId));
			this.addMessageByKey(offerorName, new BidMessage(MessageType.MatchBid,
					"Number of stock " + stockCode + " was reduced by " + bidQuantity + " due to bid " + bidId));
		} else {
			this.bankController.makeTransaction(offereeId, offerorId, bidId, bid.getValue());
			this.updateStockQuantity(offerorId, stockCode, -bidQuantity);
			this.updateStockQuantity(offereeId, stockCode, bidQuantity);
			this.addMessageByKey(offerorId, new BidMessage(MessageType.MatchBid,
					"Number of stock " + stockCode + " was reduced by " + bidQuantity + " due to bid " + bidId));
			this.addMessageByKey(offerorId, new BidMessage(MessageType.MatchBid,
					"Number of stock " + stockCode + " was added by " + bidQuantity + " due to bid " + bidId));
		}
		
		this.rankBoard.updateAmount(offerorId, this.getTotalAmount(offerorId));
		this.rankBoard.updateAmount(offereeId, this.getTotalAmount(offereeId));
		
		
		this.bids.changeBidStatus(bidId, BidStatus.Matched);
		for (String key: this.messages.keySet()) {
			this.addMessageByKey(key, new BidMessage(MessageType.UpdateBid, 
					"New bid was matched", this.bids));
			this.addMessageByKey(key, new RankMessage(MessageType.UpdateRank,
					"Rank board was updated", this.rankBoard));
		}
	}
	
}
