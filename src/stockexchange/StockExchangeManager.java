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
import common.IBankRemote;
import common.IBid;
import common.IBidCollection;
import common.IMessage;
import common.IRankCollection;
import common.IStock;
import common.IStockCollection;
import common.Message;
import common.MessageType;
import exception.BidNotAvailableException;
import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;
import exception.InvalidLoginException;
import exception.NonPositiveStockQuantityException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.NotFoundStockCodeException;
import exception.OfferorNotEnoughMoneyException;
import exception.OutOfStockPriceRangeException;
import exception.TimeOutException;

public class StockExchangeManager {
	
	private IBankRemote bankController;
	private IStockCollection stocks;
	private IBidCollection bids;
	private IRankCollection rankBoard;
	private HashMap<String, ArrayList<IMessage>> messages;
	private HashMap<String, IStockCollection> ownStocks;
	private LocalTime startMilestone;
	private HashMap<String, String> idToName;
	private HashMap<String, String> nameToId;
	private Timer timer;

	public StockExchangeManager(IBankRemote bankController) {
		this.bankController = bankController;
		this.stocks = new StockCollection();
		this.messages = new HashMap<>();
		this.ownStocks = new HashMap<>();
		this.bids = new BidCollection();
		this.rankBoard = new RankCollection();
		this.idToName = new HashMap<>();
		this.nameToId = new HashMap<>();
	}
	
	public void start() throws RemoteException {
		this.startMilestone = LocalTime.now();
		this.bankController.start();
		for (String key: this.messages.keySet()) {
			this.addMessageByKey(key, new Message(MessageType.Start, "Game start"));
		}
		this.stocks.resetPrice();
		this.rankBoard.resetRank();
		this.bids.reset();
		timer = new Timer();
		timer.scheduleAtFixedRate(new StockPriceAdjustmentTask(this), Convention.STOCK_EXCHANGE_SESSION, Convention.STOCK_EXCHANGE_SESSION);
		timer.schedule(new GameEndingTask(this), Convention.GAME_DURATION);
	}
	
	public void end() throws RemoteException {
		this.startMilestone = null;
		this.bankController.end();
		timer.cancel();
		for (String key: this.messages.keySet()) {
			this.addMessageByKey(key, new Message(MessageType.End, "Game end"));
		}
	}
	
	public Duration getCurrentTime() {
		if (this.startMilestone == null) {
			return Duration.ZERO;
		}
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
	
	public IStock issueStock(String companyId, String stockCode) throws DuplicateStockCodeException, DuplicateCompanyNameException, RemoteException, NotFoundAccountException {
		if (this.stocks.hasStockCode(stockCode)) {
			throw new DuplicateStockCodeException(stockCode);
		}
		
		IAccount account = this.bankController.login(companyId);
		String companyName = account.getName();
		if (this.stocks.hasCompanyName(companyName)) {
			throw new DuplicateCompanyNameException(companyName);
		}
		
		this.idToName.put(companyId, companyName);
		this.nameToId.put(companyName, companyId);
		
		Stock stock = new Stock(stockCode, Convention.INITIAL_SHARE_PRICE, companyName);
		this.stocks.addStock(stock, 0);
		this.ownStocks.put(companyId, new StockCollection(stock, Convention.INITIAL_SHARE_NUMBER));
		this.messages.put(companyId, new ArrayList<>());
		
		for (String key: this.messages.keySet()) {
			this.addMessageByKey(key, new StockMessage(MessageType.UpdateStock,
				"New stock was issued: " + stock.getCode(), this.stocks));
		}
		
		return stock;
	}
	
	public IStockCollection getCompanyStocks() {
		IStockCollection newStocks = new StockCollection();
		for (IStock stock: this.stocks.toArray()) {
			String stockCode = stock.getCode();
			String companyName = this.stocks.getStock(stockCode).getCompanyName();
			String companyId = this.nameToId.get(companyName);
			int quantity = this.getStockQuantityOfCompany(companyId, stockCode);
			newStocks.addStock(stock, quantity);
		}
		
		return newStocks;
	}
	
	public void registerPlayer(String playerId) throws RemoteException, InvalidLoginException, NotFoundAccountException {
		IAccount account = this.bankController.login(playerId);
		String accountId = account.getId();
		this.rankBoard.addPlayerIfAbsent(account.getName(), account.getBalance());
		this.ownStocks.putIfAbsent(accountId, new StockCollection());
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
	
	public IStockCollection getOwnStock(String playerId) {
		return this.ownStocks.get(playerId);
	}	
	
	public IRankCollection getRankBoard() {
		return this.rankBoard;
	}
	
	public void adjustStockPrice(HashMap<String, Double> stockPrices) throws RemoteException, NotFoundAccountException {
		this.stocks.updateStockPrice(stockPrices);
		
		HashMap<String, Double> amounts = new HashMap<>();
		for (String playerId: this.ownStocks.keySet()) {
			IStockCollection playerStock = this.ownStocks.get(playerId);
			playerStock.updateStockPrice(stockPrices);
			amounts.put(this.idToName.get(playerId), this.getTotalAmount(playerId));
		}
		this.rankBoard.updateAllAmounts(amounts);
		
		for (String key: this.messages.keySet()) {
			this.addMessageByKey(key, new StockMessage(MessageType.UpdateStock, 
					"Session close, adjust stock price", this.stocks));
			this.addMessageByKey(key, new RankMessage(MessageType.UpdateRank,
					"Rank board was updated", this.rankBoard));
		}
	}
	
	public IBidCollection getBids() {
		return this.bids;
	}
	
	public double getTotalAmount(String playerId) throws RemoteException, NotFoundAccountException {
		double balance = this.bankController.getBalanceById(playerId);
		IStockCollection playerStock = this.getOwnStock(playerId);
		double stockValue = playerStock.getTotalStockValue();
		return balance + stockValue;
	}
	
	public int getStockQuantityOfCompany(String companyId, String stockCode) {
		IStockCollection companyStock = this.getOwnStock(companyId);
		return companyStock.getStockQuantity(stockCode);
	}
	
	public Boolean isOutOfStockPriceRange(String stockCode, double offerPrice) {
		IStock stock = this.stocks.getStock(stockCode);
		return stock.isOutOfPriceRange(offerPrice);
	}
	
	private void updateStockQuantity(String playerId, String stockCode, int quantity) {
		IStockCollection playerStock = this.ownStocks.get(playerId);
		if (quantity > 0 && !playerStock.hasStockCode(stockCode)) {
			playerStock.addStock(this.stocks.getStock(stockCode), 0);
		}
		playerStock.addQuantity(stockCode, quantity);
		this.ownStocks.put(playerId, playerStock);
	}
	
	public void postBid(BidType type, String stockCode, int quantity, double offerPrice, String offerorId) 
			throws NotFoundStockCodeException, NotFoundAccountException, OutOfStockPriceRangeException, RemoteException, NotEnoughMoneyException, NotEnoughStockQuantityException, TimeOutException, NonPositiveStockQuantityException {
		
		if (this.startMilestone == null) {
			throw new TimeOutException();
		}
		
		// check if bid is valid
		
		// valid stock code ?
		if (!this.stocks.hasStockCode(stockCode)) {
			throw new NotFoundStockCodeException(stockCode);
		}
		
		// stock quantity > 0
		if (quantity <= 0) {
			throw new NonPositiveStockQuantityException();
		}
		
		// valid account name ?
		if (!this.ownStocks.containsKey(offerorId)) {
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
			IStockCollection offerorStock = this.ownStocks.get(offerorId);
			int offerorStockQuantity = offerorStock.getStockQuantity(stockCode);
			if (offerorStockQuantity < quantity) {
				throw new NotEnoughStockQuantityException(offerorStockQuantity, quantity, stockCode);
			}
		}
		
		// update bid
		Bid bid = new Bid(type, stock, this.idToName.get(offerorId), quantity, offerPrice);
		this.bids.addBid(bid);
		
		// notify all players and companies
		this.addMessageByKey(offerorId, new Message(MessageType.PostBid, "Your bid was succesfully posted"));
		
		for (String key: this.messages.keySet()) {
			this.addMessageByKey(key, new BidMessage(MessageType.UpdateBid,
					"New bid was posted!", this.bids));
		}
	}
	
	public synchronized void acceptBid(int bidId, String offereeId) 
			throws NotFoundBidException, BidNotAvailableException, NotFoundAccountException, NotEnoughStockQuantityException, RemoteException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, TimeOutException {
		
		if (this.startMilestone == null) {
			throw new TimeOutException();
		}
		
		IBid bid = this.bids.getBidById(bidId);
		
		if (bid.getStatus() != BidStatus.Available) {
			throw new BidNotAvailableException(bidId);
		}
		if (!this.ownStocks.containsKey(offereeId)) {
			throw new NotFoundAccountException(offereeId);
		}
		
		IStock bidStock = bid.getStock();
		String stockCode = bidStock.getCode();
		String offerorName = bid.getOfferorName();
		String offerorId = this.nameToId.get(offerorName);
		int bidQuantity = bid.getQuantity();
		BidType bidType = bid.getType();
		
		if (bidType == BidType.Buy) {
			IStockCollection offereeStock = this.ownStocks.get(offereeId);
			int offereeStockQuantity = offereeStock.getStockQuantity(stockCode);
			if (offereeStockQuantity < bidQuantity) {
				throw new NotEnoughStockQuantityException(offereeStockQuantity, 
						bid.getQuantity(), stockCode);
			}
		} else {
			IStockCollection offerorStock = this.ownStocks.get(offerorId);
			int offerorStockQuantity = offerorStock.getStockQuantity(stockCode);
			if (offerorStockQuantity < bidQuantity) {
				throw new NotEnoughStockQuantityException(offerorStockQuantity, 
						bid.getQuantity(), stockCode);
			}
		}
				
		this.bankController.makeTransaction(bid, offereeId);
		if (bidType == BidType.Buy) {
			
			this.updateStockQuantity(offerorId, stockCode, bidQuantity);
			this.updateStockQuantity(offereeId, stockCode, -bidQuantity);
			this.addMessageByKey(offerorId, new Message(MessageType.MatchBid,
					"Number of stock " + stockCode + " was added by " + bidQuantity + " due to bid " + bidId));
			this.addMessageByKey(offereeId, new Message(MessageType.MatchBid,
					"Number of stock " + stockCode + " was reduced by " + bidQuantity + " due to bid " + bidId));
		} else {
			
			this.updateStockQuantity(offerorId, stockCode, -bidQuantity);
			this.updateStockQuantity(offereeId, stockCode, bidQuantity);
			this.addMessageByKey(offerorId, new Message(MessageType.MatchBid,
					"Number of stock " + stockCode + " was reduced by " + bidQuantity + " due to bid " + bidId));
			this.addMessageByKey(offereeId, new Message(MessageType.MatchBid,
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
