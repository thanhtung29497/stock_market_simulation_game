package player;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Convention;
import common.IAccountController;
import common.IPlayerStockController;

public class PlayerClient {
	public static String HOST = "localhost";
	
	public static void main (String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			IAccountController accountController = (IAccountController)registry.lookup(
					Convention.BANK_SERVER_NAME + "/" + Convention.ACCOUNT_CONTROLLER_NAME);
			IPlayerStockController stockController = (IPlayerStockController)registry.lookup(
					Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.PLAYER_STOCK_CONTROLLER_NAME);
			
			HumanPlayer player1 = new HumanPlayer(accountController, stockController);
			player1.registerBank("tung1", "tung");
			player1.loginBank("tung1", "tung");
			
			player1.registerStockExchange();
			
			ComputerPlayer computer1 = new ComputerPlayer(accountController, stockController);

		} catch (Exception e) {
			System.out.println("Exception:");
			e.printStackTrace();	
		}
	}
}
