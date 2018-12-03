package common;

import java.rmi.RemoteException;
import java.util.ArrayList;

import exception.BidNotAvailableException;
import exception.DuplicateCompanyNameException;
import exception.DuplicateLoginNameException;
import exception.DuplicateStockCodeException;
import exception.ExceedMaximumAccountException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.OfferorNotEnoughMoneyException;
import exception.TimeOutException;

public interface ICompanyController extends IStockOwner {
	public void registerStockExchange(String stockCode) throws RemoteException, DuplicateCompanyNameException, DuplicateStockCodeException, NotFoundAccountException;
	public void registerBank() throws RemoteException, DuplicateLoginNameException, ExceedMaximumAccountException;
	public ArrayList<IMessage> retrieveMessage() throws RemoteException;
	public String getName();
	public String getStockCode();
	public void acceptBid(int bidId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotFoundAccountException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, TimeOutException;
	public Boolean hasStock();
	public void setStock(IStock stock);
	public int getStockQuantity() throws RemoteException;
	public double getBalance() throws RemoteException, NotFoundAccountException;
	public IStockCollection getCompanyStocks() throws RemoteException;
}
