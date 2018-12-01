package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import exception.BidNotAvailableException;
import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.OfferorNotEnoughMoneyException;
import exception.TimeOutException;

public interface ICompanyStockRemote extends Remote {
	public IStock register(String companyId, String stockCode) throws RemoteException, DuplicateCompanyNameException, DuplicateStockCodeException, NotFoundAccountException;
	public void acceptBid(int bidId, String companyId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotFoundAccountException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, TimeOutException;
	public ArrayList<IMessage> retrieveMessage(String companyId) throws RemoteException;
	public int getStockQuantity(String companyId, String stockCode) throws RemoteException;
}
