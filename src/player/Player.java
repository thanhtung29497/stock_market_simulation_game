package player;

import java.rmi.RemoteException;
import java.util.ArrayList;

import common.IAccount;
import common.IAccountController;
import common.IBid;
import common.IPlayer;
import common.IPlayerStockController;
import common.IStock;
import common.IStockOwner;
import exception.*;

public abstract class Player implements IPlayer{

	private static final long serialVersionUID = 1L;
	protected ArrayList<IStock> stocks = new ArrayList<>();
	protected IAccount account;
	protected IAccountController accountController;
	protected IPlayerStockController stockController;
	
	@Override
	public ArrayList<IStock> getStocks() {
		return this.stocks;
	}

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
		double cashValue = this.account.getBalance();
		double stockValue = 0.0;
		for (IStock stock: this.stocks) {
			stockValue += stock.getPrice() * stock.getQuantity();
		}
		return cashValue + stockValue;
	}
	
	@Override
	public void registerBank(String name, String password) throws ExceedMaximumAccountException, DuplicateLoginNameException, RemoteException  {
		this.accountController.register(name, password);
	}
	
	@Override
	public void registerStockExchange() throws InvalidLoginException, RemoteException, NotFoundAccountException  {
		this.stockController.register(this);
	}
	
	@Override
	public void loginBank(String name, String password) throws InvalidLoginException, NotFoundAccountException, RemoteException {
		this.accountController.login(name, password);
		this.account = this.accountController.getAccount();
	}
	
	@Override 
	public void setAccount(IAccount account) {
		this.account = account;
	}
	
}
