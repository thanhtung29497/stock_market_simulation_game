package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exception.NotEnoughMoneyException;
import exception.NotFoundAccountException;
import exception.OfferorNotEnoughMoneyException;

public interface IBankRemote extends Remote {
	public void start() throws RemoteException;
	public void end() throws RemoteException;
	public IAccount login(String id) throws RemoteException, NotFoundAccountException;
	public double getBalanceById(String id) throws RemoteException, NotFoundAccountException;
	public void makeTransaction(IBid bid, String offereeId)
		throws RemoteException, NotEnoughMoneyException, NotFoundAccountException, OfferorNotEnoughMoneyException;
}
