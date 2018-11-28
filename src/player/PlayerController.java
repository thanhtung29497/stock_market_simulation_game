package player;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.ArrayList;

import common.BidType;
import common.IAccount;
import common.IAccountRemote;
import common.IBankMessage;
import common.IBidCollection;
import common.IMessage;
import common.IPlayerController;
import common.IPlayerInfo;
import common.IPlayerStockRemote;
import common.IRankCollection;
import common.IStockCollection;
import exception.*;

public abstract class PlayerController implements IPlayerController{

	private static final long serialVersionUID = 1L;
	protected IStockCollection stockList;
	protected IPlayerInfo info = new PlayerInfo();
	protected IAccountRemote accountRemote;
	protected IPlayerStockRemote stockRemote;

	@Override
	public IPlayerInfo getInfo() {
		return this.info;
	}
	
	@Override
	public double getTotalAmount() throws RemoteException, NotFoundAccountException {
		return this.stockRemote.getTotalAmount(this.info.getId());
	}
	
	@Override
	public void registerBank(String name, String password) throws ExceedMaximumAccountException, DuplicateLoginNameException, RemoteException  {
		IAccount account = this.accountRemote.register(name, password);
		this.info.init(account);
	}
	
	@Override
	public void registerStockExchange() throws InvalidLoginException, RemoteException, NotFoundAccountException  {
		this.stockRemote.register(this.info.getId());
		this.info.setMoney(this.stockRemote.getTotalAmount(this.info.getId()));
	}
	
	@Override
	public void loginBank() throws InvalidLoginException, NotFoundAccountException, RemoteException {
		this.accountRemote.login(this.info.getName(), this.info.getPassword());
	}
	
	@Override
	public ArrayList<IBankMessage> retrieveBankMessages() throws RemoteException{
		return this.accountRemote.retrieveMessages(this.info.getId());
	}
	
	@Override 
	public ArrayList<IMessage> retrieveStockExchangeMessages() throws RemoteException {
		return this.stockRemote.retrieveMessages(this.info.getId());
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
		return this.stockRemote.getOwnStocks(this.info.getId());
	}

	@Override
	public void loginBank(String name, String password)
			throws InvalidLoginException, NotFoundAccountException, RemoteException {
		IAccount account = this.accountRemote.login(name, password);
		this.info.init(account);
	}

	@Override
	public void postBid(BidType type, String stockCode, double offerPrice, int quantity) throws RemoteException, NotEnoughMoneyException, NotFoundStockCodeException, OutOfStockPriceRangeException, NotEnoughStockQuantityException, NotFoundAccountException {
		this.stockRemote.postBid(this.info.getId(), type, stockCode, quantity, offerPrice);
	}

	@Override
	public void acceptBid(int bidId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, NotFoundAccountException {
		this.stockRemote.acceptBid(this.info.getId(), bidId);
	}

	@Override
	public IRankCollection getRankBoard() throws RemoteException {
		IRankCollection rankCollection = this.stockRemote.getRankBoard();
		this.info.setRank(rankCollection.getRankByName(this.info.getName()));
		return rankCollection;
	}
	
	@Override
	public Duration getCurrentTime() throws RemoteException {
		return this.stockRemote.getCurrentTime();
	}
	
}
