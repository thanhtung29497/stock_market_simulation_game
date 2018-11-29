package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exception.NotEnoughMoneyException;
import exception.NotFoundAccountException;

public interface IBankController extends Remote {
	public void start() throws RemoteException;
	public void end() throws RemoteException;
	public IAccount login(String id) throws RemoteException, NotFoundAccountException;
	public double getBalanceById(String id) throws RemoteException, NotFoundAccountException;
	public void makeTransaction(String payerId, String payeeId, int bidId, double money)
		throws RemoteException, NotEnoughMoneyException, NotFoundAccountException;
}
