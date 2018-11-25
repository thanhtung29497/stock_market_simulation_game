package player;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import bank.MessageRetrievingTask;
import common.Convention;
import common.ErrorType;
import common.IAccount;
import common.IAccountRemote;
import common.IPlayerStockRemote;
import exception.DuplicateLoginNameException;
import exception.ExceedMaximumAccountException;
import exception.InvalidLoginException;
import exception.NotFoundAccountException;
import ui.player.PlayerFrameController;

public class PlayerClient {
	
	private PlayerFrameController viewController;
	private	PlayerController modelController;
	private Registry registry;
	private IAccountRemote accountController;
	private	IPlayerStockRemote stockController;
	
	private PlayerClient() {
		this.viewController = new PlayerFrameController(this);
		try {
			this.connectToRegistry();
			this.modelController = new HumanPlayer(this.accountController, this.stockController);
		} catch (RemoteException | NotBoundException e) {
			this.viewController.showError(ErrorType.FailedToConnectRegistry);
		}
	}
	
	public void run() {
		this.viewController.showLogin();
	}
	
	private void connectToRegistry() throws RemoteException, NotBoundException {
		this.registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
		this.accountController = (IAccountRemote)registry.lookup(
				Convention.BANK_SERVER_NAME + "/" + Convention.ACCOUNT_CONTROLLER_NAME);
		this.stockController = (IPlayerStockRemote)registry.lookup(
				Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.PLAYER_STOCK_CONTROLLER_NAME);
	}
	
	public void register(String name, String password) {
		try {
			this.modelController.registerBank(name, password);
		} catch (RemoteException e) {
			this.viewController.showError(ErrorType.FailedToConnectServer);
		} catch (ExceedMaximumAccountException e) {
			this.viewController.showError(ErrorType.ExceedMaximumAccount);
		} catch (DuplicateLoginNameException e) {
			this.viewController.showError(ErrorType.DuplicateLoginName);
		}
	}
	
	public void loginWithRegisteredName() {
		IAccount account = this.modelController.getAccount();
		if (account == null) {
			this.viewController.showError(ErrorType.NotRegisteredYet);
		}
		this.login(account.getName(), account.getPassword());
	}
	
	public void login(String name, String password) {
		try {
			this.modelController.loginBank(name, password);
			this.modelController.registerStockExchange();
			
			// Login successfully
			// Add periodical retrieving
			Timer messageTimer = new Timer();
			messageTimer.scheduleAtFixedRate(new MessageRetrievingTask(viewController, modelController), 0, Convention.RETRIEVE_MESSAGE_PERIOD);
			
			// Display
			this.viewController.showMainView(this.modelController.getAllStocks(), 
					this.modelController.getAllBids(),
					this.modelController.getAccount());
			
		} catch (RemoteException e) {
			this.viewController.showError(ErrorType.FailedToConnectServer);
		} catch (InvalidLoginException | NotFoundAccountException e) {
			this.viewController.showError(ErrorType.InvalidLogin);
		}
	}
		
	public static void main (String[] args) {
		PlayerClient playerClient = new PlayerClient();
		playerClient.run();
	}
}
