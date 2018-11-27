package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.Duration;
import java.util.ArrayList;

import exception.*;

public interface IPlayerStockRemote extends Remote {
	public void register(String accountName, String password) throws RemoteException, NotFoundAccountException, InvalidLoginException;
	public void postBid(BidType type, String stockCode, int quantity, double offerPrice) throws RemoteException, NotEnoughMoneyException, NotFoundStockCodeException, OutOfStockPriceRangeException, NotEnoughStockQuantityException;
	public ArrayList<IMessage> retrieveMessages() throws RemoteException;
	public IBidCollection getBids() throws RemoteException;
	public IStockCollection getStocks() throws RemoteException;
	public void acceptBid(int bidId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException;
	public IStockCollection getOwnStocks() throws RemoteException;
	public double getTotalAmount() throws RemoteException, NotFoundAccountException;
	public IRankCollection getRankBoard() throws RemoteException;
	public Duration getCurrentTime() throws RemoteException;
}
