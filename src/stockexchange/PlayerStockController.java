package stockexchange;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import common.IBid;
import common.IPlayerStockController;
import common.Message;
import exception.InvalidLoginException;
import exception.NotEnoughMoneyException;
import exception.NotFoundAccountException;

public class PlayerStockController extends UnicastRemoteObject implements IPlayerStockController{

	private StockExchangeManager stockExchangeManager;
	private static final long serialVersionUID = 1L;
	private String accountName;

	protected PlayerStockController(StockExchangeManager stockExchangeManager) throws RemoteException {
		super();
		this.stockExchangeManager = stockExchangeManager;
	}

	@Override
	public void postBid(IBid bid) throws RemoteException, NotEnoughMoneyException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(String accountName, String password) throws RemoteException, NotFoundAccountException, InvalidLoginException {
		this.stockExchangeManager.registerPlayer(accountName, password);
		this.accountName = accountName;
		System.out.println("Register Successfully: " + accountName);
	}

	@Override
	public ArrayList<Message> retrieveMessages() throws RemoteException {
		return this.stockExchangeManager.retrieveMessages(this.accountName, true);
	}
	
}
