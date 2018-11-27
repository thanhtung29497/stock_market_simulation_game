package player;

import java.rmi.RemoteException;
import java.util.ArrayList;

import common.BidType;
import common.IAccount;
import common.IAccountRemote;
import common.IBankMessage;
import common.IBidCollection;
import common.IMessage;
import common.IPlayerController;
import common.IPlayerStockRemote;
import common.IStockCollection;
import exception.*;

public abstract class PlayerController implements IPlayerController{

	private static final long serialVersionUID = 1L;
	protected IStockCollection stockList;
	protected IAccount account;
	protected IAccountRemote accountRemote;
	protected IPlayerStockRemote stockRemote;
	
	public void updateBalance(double balance) {
		this.account.updateBalance(balance);
	}

	@Override
	public IAccount getAccount() {
		return this.account;
	}
	
	@Override
	public double getTotalAmount() throws RemoteException, NotFoundAccountException {
		return this.stockRemote.getTotalAmount();
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
		return this.accountRemote.retrieveMessages();
	}
	
	@Override 
	public ArrayList<IMessage> retrieveStockExchangeMessages() throws RemoteException {
		return this.stockRemote.retrieveMessages();
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
	public IStockCollection getOwnStocks() throws RemoteException {
		return this.stockRemote.getOwnStocks();
	}

	@Override
	public void loginBank(String name, String password)
			throws InvalidLoginException, NotFoundAccountException, RemoteException {
		IAccount account = this.accountRemote.login(name, password);
		this.setAccount(account);
	}

	@Override
	public void postBid(BidType type, String stockCode, double offerPrice, int quantity) throws RemoteException, NotEnoughMoneyException, NotFoundStockCodeException, OutOfStockPriceRangeException, NotEnoughStockQuantityException {
		this.stockRemote.postBid(type, stockCode, quantity, offerPrice);
	}

	@Override
	public void acceptBid(int bidId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException {
		this.stockRemote.acceptBid(bidId);
	}
	
}
