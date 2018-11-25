package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import exception.*;

public interface IPlayerStockRemote extends Remote {
	public void register(String accountName, String password) throws RemoteException, NotFoundAccountException, InvalidLoginException;
	public void postBid(IBid bid) throws RemoteException, NotEnoughMoneyException;
	public ArrayList<Message> retrieveMessages() throws RemoteException;
}
