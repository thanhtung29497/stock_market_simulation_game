package player;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Timer;

import common.Convention;
import common.IAccountRemote;
import common.IPlayerStockRemote;
import exception.DuplicateLoginNameException;
import exception.ExceedMaximumAccountException;
import exception.InvalidLoginException;
import exception.NotFoundAccountException;
import ui.bot.ComputerPlayerFrame;

public class ComputerPlayerClient {
	
	private Registry registry;
	private IAccountRemote accountController;
	private IPlayerStockRemote stockController;
	private HashMap<String, ComputerPlayer> players;
	private ComputerPlayerFrame view;
	
	private ComputerPlayerClient() {
		this.players = new HashMap<>();
	}
	
	public void addNewPlayer() {
		try {
			if (this.registry == null) {
				this.connectToRegistry();
			}
			
			ComputerPlayer player = new ComputerPlayer(this.accountController, this.stockController);
			player.registerBank();
			player.loginBank();
			player.registerStockExchange();
			
			this.view.addMessage(player.getInfo().getName() + ": " + " register successfully");
			
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new ComputerMessageRetrievingTask(player, this.view), 0, Convention.COMPUTER_RETRIEVE_MESSAGE_PERIOD);
			this.players.put(player.getInfo().getId(), player);
			
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		} catch (ExceedMaximumAccountException e) {
			e.printStackTrace();
		} catch (DuplicateLoginNameException e) {
			e.printStackTrace();
		} catch (InvalidLoginException e) {
			e.printStackTrace();
		} catch (NotFoundAccountException e) {
			e.printStackTrace();
		}
	}
	
	private void connectToRegistry() throws RemoteException, NotBoundException {
		this.registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
		this.accountController = (IAccountRemote)registry.lookup(
				Convention.URL + "/" + Convention.BANK_SERVER_NAME + "/" + Convention.ACCOUNT_CONTROLLER_NAME);
		this.stockController = (IPlayerStockRemote)registry.lookup(
				Convention.URL + "/" + Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.PLAYER_STOCK_CONTROLLER_NAME);
		
	}
	
	private void run() {
		this.view = new ComputerPlayerFrame(this);
		this.view.setVisible(true);
			
	}
	
	public static void main (String[] args) {
		try {
			ComputerPlayerClient playerClient = new ComputerPlayerClient();
			playerClient.run();
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
