package player;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import bank.MessageRetrievingTask;
import common.Convention;
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
	
	public void register(String name, String password) {
		try {
			this.connectToRegistry();
			this.modelController.registerBank(name, password);
			this.modelController.loginBank();
			this.modelController.registerStockExchange();
			
			Timer messageTimer = new Timer();
			messageTimer.scheduleAtFixedRate(new MessageRetrievingTask(viewController, modelController), 0, Convention.RETRIEVE_MESSAGE_PERIOD);
			
			// Display
			this.viewController.startTrans(this.modelController.getAllStocks(),
					this.modelController.getAllBids(),
					this.modelController.getAccount());
			
		} catch (RemoteException | NotBoundException e) {
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
			this.modelController.loginBank(name, password);
			this.modelController.registerStockExchange();
			
			Timer messageTimer = new Timer();
			messageTimer.scheduleAtFixedRate(new MessageRetrievingTask(viewController, modelController), 0, Convention.RETRIEVE_MESSAGE_PERIOD);
			
			this.viewController.startTrans(this.modelController.getAllStocks(),
					this.modelController.getAllBids(),
					this.modelController.getAccount());
			
		} catch (RemoteException e) {
			this.viewController.loginFalse("Failed to connect to server");
		} catch (InvalidLoginException | NotFoundAccountException e) {
			this.viewController.loginFalse("Wrong account name or password");
		}
	}
		
	public static void main (String[] args) {
		PlayerClient playerClient = new PlayerClient();
		playerClient.run();
	}
}
