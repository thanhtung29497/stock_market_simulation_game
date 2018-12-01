package common;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.ArrayList;

import exception.BidNotAvailableException;
import exception.DuplicateLoginNameException;
import exception.ExceedMaximumAccountException;
import exception.InvalidLoginException;
import exception.NonPostitiveStockQuantityException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.NotFoundStockCodeException;
import exception.OfferorNotEnoughMoneyException;
import exception.OutOfStockPriceRangeException;
import exception.TimeOutException;

public interface IPlayerController extends IStockOwner {
	public IPlayerInfo getInfo();
	public IStockCollection getAllStocks() throws RemoteException;
	public IBidCollection getAllBids() throws RemoteException;
	public IStockCollection getOwnStocks() throws RemoteException;
	public void postBid(BidType type, String stockCode, double offerPrice, int quantity) throws RemoteException, NotEnoughMoneyException, NotFoundStockCodeException, OutOfStockPriceRangeException, NotEnoughStockQuantityException, NotFoundAccountException, TimeOutException, NonPostitiveStockQuantityException;
	public void acceptBid(int bidId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, NotFoundAccountException, TimeOutException;
	public double getTotalAmount() throws RemoteException, NotFoundAccountException;
	public void registerBank(String name, String password) throws ExceedMaximumAccountException, DuplicateLoginNameException, RemoteException;
	public void loginBank() throws InvalidLoginException, NotFoundAccountException, RemoteException;
	public void loginBank(String name, String password) throws InvalidLoginException, NotFoundAccountException, RemoteException;
	public void registerStockExchange() throws InvalidLoginException, RemoteException, NotFoundAccountException;
	public ArrayList<IBankMessage> retrieveBankMessages() throws RemoteException;
	public ArrayList<IMessage> retrieveStockExchangeMessages() throws RemoteException;
	public IRankCollection getRankBoard() throws RemoteException;
	public Duration getCurrentTime() throws RemoteException;
}
