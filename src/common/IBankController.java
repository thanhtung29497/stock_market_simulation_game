package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exception.InvalidLoginException;
import exception.NotEnoughMoneyException;
import exception.NotFoundAccountException;

public interface IBankController extends Remote {
	public IAccount login(String name, String password) throws RemoteException, InvalidLoginException, NotFoundAccountException;
	public double getBalanceByName(String name) throws RemoteException, NotFoundAccountException;
	public void makeTransaction(String payerName, String payeeName, int bidId, double money)
		throws RemoteException, NotEnoughMoneyException, NotFoundAccountException;
}
