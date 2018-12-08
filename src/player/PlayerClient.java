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
import exception.NonPositiveStockQuantityException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.NotFoundStockCodeException;
import exception.OfferorNotEnoughMoneyException;
import exception.OutOfStockPriceRangeException;
import exception.TimeOutException;
import ui.player.PlayerFrameController;

public class PlayerClient {
	
	private PlayerFrameController viewController;
	private	PlayerController modelController;
	private Registry registry;
	private IAccountRemote accountController;
	private	IPlayerStockRemote stockController;
	private Timer timer;
	private Boolean timeOut;
	
	private PlayerClient() {
		this.viewController = new PlayerFrameController(this);
		this.timeOut = true;
	}
	
	public Boolean isTimeOut() {
		return this.timeOut;
	}
	
	public void run() {
		this.viewController.start();
	}
	
	private void connectToRegistry() throws RemoteException, NotBoundException {
		this.registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
		this.accountController = (IAccountRemote)registry.lookup(
				Convention.URL + "/" + Convention.BANK_SERVER_NAME + "/" + Convention.ACCOUNT_CONTROLLER_NAME);
		this.stockController = (IPlayerStockRemote)registry.lookup(
				Convention.URL + "/" + Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.PLAYER_STOCK_CONTROLLER_NAME);
		this.modelController = new HumanPlayer(this.accountController, this.stockController);
	}
	
	private void registerStockExchangeAndInit() throws RemoteException, InvalidLoginException, NotFoundAccountException {
		this.modelController.registerStockExchange();
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new PlayerMessageRetrievingTask(viewController, modelController, this), Convention.PLAYER_RETRIEVE_MESSAGE_PERIOD, Convention.PLAYER_RETRIEVE_MESSAGE_PERIOD);
	}
	
	private void initView() throws RemoteException {
		this.viewController.startTrans(this.modelController.getAllStocks(),
				this.modelController.getAllBids(),
				this.modelController.getInfo(),
				this.modelController.getRankBoard(),
				this.modelController.getOwnStocks());
	}

	public void startGame() {
		this.timeOut = false;
		this.timer = new Timer();
		timer.scheduleAtFixedRate(new TimeUpdatingTask(viewController, modelController), 1000, 1000);
		try {
			this.viewController.updateStocksAndBids(this.modelController.getAllStocks(), this.modelController.getAllBids());
			this.viewController.updateRank(this.modelController.getRankBoard());
		} catch (RemoteException e) {
			this.viewController.showMessage("Server error", "Failed to connect to server");
		}
	}
	
	public void endGame() {
		this.timeOut = true;
		this.viewController.showMessage("Notification", "Game end! Your rank is " + this.modelController.getInfo().getRank());
		timer.cancel();
	}
	
	public void register(String name, String password) {
		try {
			this.connectToRegistry();
			this.modelController.registerBank(name, password);
			this.modelController.loginBank();
			this.registerStockExchangeAndInit();
			this.initView();
			
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			this.viewController.showMessage("Server error", "Failed to connect to server");
		} catch (ExceedMaximumAccountException e) {
			this.viewController.showMessage("Account error", "Exceed of maximum account");
		} catch (DuplicateLoginNameException e) {
			this.viewController.showMessage("Login error", "Duplicate login name");
		} catch (InvalidLoginException | NotFoundAccountException e) {
			this.viewController.showMessage("Login error", "Invalid register");
		}
	}
	
	public void login(String name, String password) {
		try {
			this.connectToRegistry();
			this.modelController.loginBank(name, password);
			this.registerStockExchangeAndInit();
			this.initView();
			
		} catch (RemoteException | NotBoundException e) {
			this.viewController.showMessage("Server error", "Failed to connect to server");
		} catch (InvalidLoginException | NotFoundAccountException e) {
			this.viewController.showMessage("Login error", "Wrong account name or password");
		}
	}
	
	public void postBid(BidType type, String stockCode, double offerPrice, int quantity) {
		try {
			this.modelController.postBid(type, stockCode, offerPrice, quantity);
		} catch (RemoteException e) {
			this.viewController.showMessage("Server error", "Failed to connect to server");
		} catch (NotEnoughMoneyException e) {
			this.viewController.showMessage("Error", "Not enough money to make transaction");
		} catch (NotFoundStockCodeException e) {
			this.viewController.showMessage("Invalid bid", "Invalid stock code " + stockCode);
		} catch (OutOfStockPriceRangeException e) {
			this.viewController.showMessage("Invalid bid", "The price you offer is out of range");
		} catch (NotEnoughStockQuantityException e) {
			this.viewController.showMessage("Invalid bid", "Not enough stock to make transaction");
		} catch (NotFoundAccountException e) {
			this.viewController.showMessage("Account error", "Something went wrong with your account");
		} catch (TimeOutException e) {
			this.viewController.showMessage("Warning", "Time out");
		} catch (NonPositiveStockQuantityException e) {
			this.viewController.showMessage("Invalid bid", "The quantity of stock must be positive");
		}
	}
	
	public void acceptBid(int bidId) {
		try {
			this.modelController.acceptBid(bidId);
		} catch (RemoteException e) {
			this.viewController.showMessage("Server error", "Failed to connect to server");
		} catch (NotFoundBidException e) {
			this.viewController.showMessage("Invalid bid", "Not found bid id " + bidId);
		} catch (BidNotAvailableException e) {
			this.viewController.showMessage("Invalid bid", "Bid might be matched");
		} catch (NotEnoughStockQuantityException e) {
			this.viewController.showMessage("Invalid bid", "Not enough stock to make transaction");
		} catch (NotEnoughMoneyException e) {
			this.viewController.showMessage("Invalid bid", "Not enough money to make transaction");
		} catch (OfferorNotEnoughMoneyException e) {
			this.viewController.showMessage("Invalid bid", "Offeror " + e.getOfferorName() + " not enough money to make transaction");
		} catch (NotFoundAccountException e) {
			this.viewController.showMessage("Account error", "Something went wrong with your account");
		} catch (TimeOutException e) {
			this.viewController.showMessage("Warning", "Time is out");
		}
	}
		
	public static void main (String[] args) {
		PlayerClient playerClient = new PlayerClient();
		playerClient.run();
	}
}
