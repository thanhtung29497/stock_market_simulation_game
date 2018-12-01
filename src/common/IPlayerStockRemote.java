package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Duration;
import java.util.ArrayList;

import exception.*;

public interface IPlayerStockRemote extends Remote {
	public void register(String accountId) throws RemoteException, NotFoundAccountException, InvalidLoginException;
	public void postBid(String accountId, BidType type, String stockCode, int quantity, double offerPrice) throws RemoteException, NotEnoughMoneyException, NotFoundStockCodeException, OutOfStockPriceRangeException, NotEnoughStockQuantityException, NotFoundAccountException, TimeOutException, NonPositiveStockQuantityException;
	public ArrayList<IMessage> retrieveMessages(String accountId) throws RemoteException;
	public IBidCollection getBids() throws RemoteException;
	public IStockCollection getStocks() throws RemoteException;
	public void acceptBid(String accountId, int bidId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, NotFoundAccountException, TimeOutException;
	public IStockCollection getOwnStocks(String accountId) throws RemoteException;
	public double getTotalAmount(String accountId) throws RemoteException, NotFoundAccountException;
	public IRankCollection getRankBoard() throws RemoteException;
	public Duration getCurrentTime() throws RemoteException;
}
