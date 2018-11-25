package player;

import java.rmi.RemoteException;
import java.util.ArrayList;

import common.IAccount;
import common.IAccountRemote;
import common.IBankMessage;
import common.IBid;
import common.IBidCollection;
import common.IPlayerController;
import common.IPlayerStockRemote;
import common.IStockCollection;
import common.IStockOwner;
import common.Message;
import exception.*;

public abstract class PlayerController implements IPlayerController{

	private static final long serialVersionUID = 1L;
	protected IStockCollection stockList;
	protected IAccount account;
	protected IAccountRemote accountRemote;
	protected IPlayerStockRemote stockRemote;

	@Override
	public abstract void processBid(IBid bid);

	@Override
	public IAccount getAccount() {
		return this.account;
	}

	@Override
	public void buy(String stockCode, int quantity) {
		
	}

	@Override
	public void sell(String stockCode, int quantity, IStockOwner stockOwner) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public double getTotalValue() {
		return this.account.getBalance();
	}
	
	@Override
	public void registerBank(String name, String password) throws ExceedMaximumAccountException, DuplicateLoginNameException, RemoteException  {
		IAccount account = this.accountRemote.register(name, password);
		this.setAccount(account);
	}
	
	@Override
	public void registerStockExchange() throws InvalidLoginException, RemoteException, NotFoundAccountException  {
		this.stockRemote.register(account.getName(), account.getPassword());
	}
	
	@Override
	public void loginBank() throws InvalidLoginException, NotFoundAccountException, RemoteException {
		this.accountRemote.login(this.account.getName(), this.account.getPassword());
	}
	
	@Override 
	public void setAccount(IAccount account) {
		this.account = account;
	}
	
	@Override
	public ArrayList<IBankMessage> retrieveBankMessages() throws RemoteException{
		ArrayList<IBankMessage> messages = this.accountRemote.retrieveMessages();
		if (!messages.isEmpty()) {
			this.account.updateBalance(messages.get(messages.size() - 1).getBalance());
		}
		return messages;
	}
	
	@Override 
	public ArrayList<Message> retrieveStockExchangeMessages() throws RemoteException {
		ArrayList<Message> messages = this.stockRemote.retrieveMessages();
		if (!messages.isEmpty()) {
			
		}
		return messages;
	}

	@Override
	public IStockCollection getAllStocks() throws RemoteException{
		return this.stockRemote.getStocks();
	}

	@Override
	public IBidCollection getAllBids() throws RemoteException {
		return this.stockRemote.getBids();
	}

	@Override
	public IStockCollection getOwnStocks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBids(IBidCollection collection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateStocks(IStockCollection collection) {
		this.stockList = collection;
	}

	@Override
	public void loginBank(String name, String password)
			throws InvalidLoginException, NotFoundAccountException, RemoteException {
		IAccount account = this.accountRemote.login(name, password);
		this.setAccount(account);
	}
	
}
