package stockexchange;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.ArrayList;

import common.BidType;
import common.IBidCollection;
import common.IMessage;
import common.IPlayerStockRemote;
import common.IRankCollection;
import common.IStockCollection;
import exception.BidNotAvailableException;
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

public class PlayerStockRemote extends UnicastRemoteObject implements IPlayerStockRemote{

	private StockExchangeManager stockExchangeManager;
	private static final long serialVersionUID = 1L;

	protected PlayerStockRemote(StockExchangeManager stockExchangeManager) throws RemoteException {
		super();
		this.stockExchangeManager = stockExchangeManager;
	}

	@Override
	public void register(String accountId) throws RemoteException, NotFoundAccountException, InvalidLoginException {
		this.stockExchangeManager.registerPlayer(accountId);
		System.out.println("Register Successfully: " + accountId);
	}

	@Override
	public ArrayList<IMessage> retrieveMessages(String accountId) throws RemoteException {
		synchronized (this) {
			return this.stockExchangeManager.retrieveMessages(accountId);
		}
	}

	@Override
	public IBidCollection getBids() throws RemoteException {
		return this.stockExchangeManager.getBids();
	}

	@Override
	public IStockCollection getStocks() throws RemoteException {
		return this.stockExchangeManager.getStocks();
	}

	@Override
	public void postBid(String accountId, BidType type, String stockCode, int quantity, double offerPrice)
			throws RemoteException, NotEnoughMoneyException, NotFoundStockCodeException, OutOfStockPriceRangeException, NotEnoughStockQuantityException, NotFoundAccountException, TimeOutException, NonPositiveStockQuantityException {
		this.stockExchangeManager.postBid(type, stockCode, quantity, offerPrice, accountId);
	}

	@Override
	public void acceptBid(String accountId, int bidId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, NotFoundAccountException, TimeOutException {
		synchronized (this.stockExchangeManager) {
			this.stockExchangeManager.acceptBid(bidId, accountId);
		}
	}

	@Override
	public IStockCollection getOwnStocks(String accountId) throws RemoteException {
		return this.stockExchangeManager.getOwnStock(accountId);
	}

	@Override
	public double getTotalAmount(String accountId) throws RemoteException, NotFoundAccountException {
		return this.stockExchangeManager.getTotalAmount(accountId);
	}

	@Override
	public IRankCollection getRankBoard() throws RemoteException {
		return this.stockExchangeManager.getRankBoard();
	}

	@Override
	public Duration getCurrentTime() throws RemoteException {
		return this.stockExchangeManager.getCurrentTime();
	}
	
}
