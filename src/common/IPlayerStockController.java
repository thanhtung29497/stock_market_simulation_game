package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import exception.*;

public interface IPlayerStockController extends Remote {
	public void register(IPlayer player) throws RemoteException, NotFoundAccountException, InvalidLoginException;
	public void postBid(IBid bid) throws RemoteException, NotEnoughMoneyException;
}
