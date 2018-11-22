package stockexchange;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.IBid;
import common.IPlayer;
import common.IPlayerStockController;
import exception.InvalidLoginException;
import exception.NotEnoughMoneyException;
import exception.NotFoundAccountException;

public class PlayerStockController extends UnicastRemoteObject implements IPlayerStockController{

	private StockExchangeManager stockExchangeManager;
	private static final long serialVersionUID = 1L;

	protected PlayerStockController(StockExchangeManager stockExchangeManager) throws RemoteException {
		super();
		this.stockExchangeManager = stockExchangeManager;
	}

	@Override
	public void register(IPlayer player) throws RemoteException, NotFoundAccountException, InvalidLoginException {
		this.stockExchangeManager.registerPlayer(player);
		System.out.println("Register Successfully: " + player.getAccount().getName());
	}

	@Override
	public void postBid(IBid bid) throws RemoteException, NotEnoughMoneyException {
		// TODO Auto-generated method stub
		
	}
	
}
