package player;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import common.BidType;
import common.Convention;
import common.IAccountRemote;
import common.IPlayerStockRemote;

import exception.BidNotAvailableException;
import exception.DuplicateLoginNameException;
import exception.ExceedMaximumAccountException;
import exception.InvalidLoginException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.NotFoundStockCodeException;
import exception.OfferorNotEnoughMoneyException;
import exception.OutOfStockPriceRangeException;
import ui.player.PlayerFrameController;

public class PlayerClient {
	
	private PlayerFrameController viewController;
	private	PlayerController modelController;
	private Registry registry;
	private IAccountRemote accountController;
	private	IPlayerStockRemote stockController;
	
	private PlayerClient() {
		this.viewController = new PlayerFrameController(this);
	}
	
	public void run() {
		this.viewController.start();
	}
	
	private void connectToRegistry() throws RemoteException, NotBoundException {
		this.registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
		this.accountController = (IAccountRemote)registry.lookup(
				Convention.BANK_SERVER_NAME + "/" + Convention.ACCOUNT_CONTROLLER_NAME);
		this.stockController = (IPlayerStockRemote)registry.lookup(
				Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.PLAYER_STOCK_CONTROLLER_NAME);
		this.modelController = new HumanPlayer(this.accountController, this.stockController);
	}
	
	private void registerStockExchangeAndInit() throws RemoteException, InvalidLoginException, NotFoundAccountException {
		this.modelController.registerStockExchange();
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MessageRetrievingTask(viewController, modelController), Convention.RETRIEVE_MESSAGE_PERIOD, Convention.RETRIEVE_MESSAGE_PERIOD);
		
		this.viewController.startTrans(this.modelController.getAllStocks(),
				this.modelController.getAllBids(),
				this.modelController.getInfo(),
				this.modelController.getRankBoard(),
				this.modelController.getOwnStocks());
		
		timer.scheduleAtFixedRate(new TimeUpdatingTask(viewController, modelController), 1000, 1000);
		
//		IStock stock = this.modelController.getAllStocks().toArray().get(0);
//		this.postBid(BidType.Buy, stock.getCode(), stock.getCapPrice(), 5);
	}
	
	public void register(String name, String password) {
		try {
			this.connectToRegistry();
			this.modelController.registerBank(name, password);
			this.modelController.loginBank();
			this.registerStockExchangeAndInit();
			
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			this.viewController.signUpFalse("Failed to connect to server");
		} catch (ExceedMaximumAccountException e) {
			this.viewController.signUpFalse("Exceed of maximum account");
		} catch (DuplicateLoginNameException e) {
			this.viewController.signUpFalse("Duplicate login name");
		} catch (InvalidLoginException | NotFoundAccountException e) {
			this.viewController.signUpFalse("Invalid register");
		}
	}
	
	public void login(String name, String password) {
		try {
			this.connectToRegistry();
			this.modelController.loginBank(name, password);
			this.registerStockExchangeAndInit();
			
		} catch (RemoteException | NotBoundException e) {
			this.viewController.loginFalse("Failed to connect to server");
		} catch (InvalidLoginException | NotFoundAccountException e) {
			this.viewController.loginFalse("Wrong account name or password");
		}
	}
	
	public void postBid(BidType type, String stockCode, double offerPrice, int quantity) {
		try {
			this.modelController.postBid(type, stockCode, offerPrice, quantity);
		} catch (RemoteException e) {
			this.viewController.loginFalse("Failed to connect to server");
		} catch (NotEnoughMoneyException e) {
			this.viewController.loginFalse("Not enough money to make transaction");
		} catch (NotFoundStockCodeException e) {
			this.viewController.loginFalse("Invalid stock code " + stockCode);
		} catch (OutOfStockPriceRangeException e) {
			this.viewController.loginFalse("The price you offer is out of range");
		} catch (NotEnoughStockQuantityException e) {
			this.viewController.loginFalse("Not enough stock to make transaction");
		}
	}
	
	public void acceptBid(int bidId) {
		try {
			this.modelController.acceptBid(bidId);
		} catch (RemoteException e) {
			this.viewController.loginFalse("Failed to connect to server");
		} catch (NotFoundBidException e) {
			this.viewController.loginFalse("Not found bid id " + bidId);
		} catch (BidNotAvailableException e) {
			this.viewController.loginFalse("Bid might be matched");
		} catch (NotEnoughStockQuantityException e) {
			this.viewController.loginFalse("Not enough stock to make transaction");
		} catch (NotEnoughMoneyException e) {
			this.viewController.loginFalse("Not enough money to make transaction");
		} catch (OfferorNotEnoughMoneyException e) {
			this.viewController.loginFalse("Offeror " + e.getOfferorName() + " not enough money to make transaction");
		}
	}
		
	public static void main (String[] args) {
		PlayerClient playerClient = new PlayerClient();
		playerClient.run();
	}
}
