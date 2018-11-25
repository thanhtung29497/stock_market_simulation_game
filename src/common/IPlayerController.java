package common;

import java.rmi.RemoteException;
import java.util.ArrayList;

import exception.DuplicateLoginNameException;
import exception.ExceedMaximumAccountException;
import exception.InvalidLoginException;
import exception.NotFoundAccountException;

public interface IPlayerController extends IStockOwner {
	public IAccount getAccount();
	public IStockCollection getAllStocks();
	public IBidCollection getAllBids();
	public IStockCollection getOwnStocks();
	public void updateBids(IBidCollection collection);
	public void updateStocks(IStockCollection collection);
	public void buy(String stockCode, int quantity);
	public void sell(String stockCode, int quantity, IStockOwner stockOwner);
	public double getTotalValue();
	public void registerBank(String name, String password) throws ExceedMaximumAccountException, DuplicateLoginNameException, RemoteException;
	public void loginBank() throws InvalidLoginException, NotFoundAccountException, RemoteException;
	public void loginBank(String name, String password) throws InvalidLoginException, NotFoundAccountException, RemoteException;
	public void setAccount(IAccount account);
	public void registerStockExchange() throws InvalidLoginException, RemoteException, NotFoundAccountException;
	public ArrayList<IBankMessage> retrieveBankMessages() throws RemoteException;
	public ArrayList<Message> retrieveStockExchangeMessages() throws RemoteException;
}
