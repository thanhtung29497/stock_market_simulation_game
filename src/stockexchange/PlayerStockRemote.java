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
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.NotFoundStockCodeException;
import exception.OfferorNotEnoughMoneyException;
import exception.OutOfStockPriceRangeException;

public class PlayerStockRemote extends UnicastRemoteObject implements IPlayerStockRemote{

	private StockExchangeManager stockExchangeManager;
	private static final long serialVersionUID = 1L;
	private String accountName;

	protected PlayerStockRemote(StockExchangeManager stockExchangeManager) throws RemoteException {
		super();
		this.stockExchangeManager = stockExchangeManager;
	}

	@Override
	public void register(String accountName, String password) throws RemoteException, NotFoundAccountException, InvalidLoginException {
		this.stockExchangeManager.registerPlayer(accountName, password);
		this.accountName = accountName;
		System.out.println("Register Successfully: " + accountName);
	}

	@Override
	public ArrayList<IMessage> retrieveMessages() throws RemoteException {
		synchronized (this) {
			return this.stockExchangeManager.retrieveMessages(this.accountName, true);
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
	public void postBid(BidType type, String stockCode, int quantity, double offerPrice)
			throws RemoteException, NotEnoughMoneyException, NotFoundStockCodeException, OutOfStockPriceRangeException, NotEnoughStockQuantityException {
		try {
			this.stockExchangeManager.postBid(type, stockCode, quantity, offerPrice, this.accountName);
		} catch (NotFoundAccountException e) {
			// can't happen
		}
	}

	@Override
	public void acceptBid(int bidId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException {
		try {
			this.stockExchangeManager.acceptBid(bidId, this.accountName);
		} catch (NotFoundAccountException e) {
			// can't happen
		}
	}

	@Override
	public IStockCollection getOwnStocks() throws RemoteException {
		return this.stockExchangeManager.getPlayerStock(this.accountName);
	}

	@Override
	public double getTotalAmount() throws RemoteException, NotFoundAccountException {
		return this.stockExchangeManager.getTotalAmount(this.accountName);
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
