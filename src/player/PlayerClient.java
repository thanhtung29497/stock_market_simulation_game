package player;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import bank.MessageRetrievingTask;
import common.Convention;
import common.IAccountController;
import common.IPlayerStockController;
import ui.player.PlayerFrameController;

public class PlayerClient {
	
	private PlayerFrameController _FrameController = null;
	private PlayerFrameController _FrameControler;
	public static void main (String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			IAccountController accountController = (IAccountController)registry.lookup(
					Convention.BANK_SERVER_NAME + "/" + Convention.ACCOUNT_CONTROLLER_NAME);
			IPlayerStockController stockController = (IPlayerStockController)registry.lookup(
					Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.PLAYER_STOCK_CONTROLLER_NAME);
			
			HumanPlayer player1 = new HumanPlayer(accountController, stockController);
			player1.registerBank("tung2", "tung");
			player1.loginBank();
			
			player1.registerStockExchange();
			
//			ComputerPlayer computer1 = new ComputerPlayer(accountController, stockController);
			
			PlayerFrameController playerFrameController = new PlayerFrameController(null);
			
			Timer messageTimer = new Timer();
			messageTimer.scheduleAtFixedRate(new MessageRetrievingTask(playerFrameController), 0, Convention.RETRIEVE_MESSAGE_PERIOD);

		} catch (Exception e) {
			System.out.println("Exception:");
			e.printStackTrace();	
		}
	}
	
	public void login(String Acc,String pass) {
		//Goij 1 trong 3
		_FrameController.startTrans();
		_FrameController.loginFalse("Tai khoan hoac mk khong dung");
		_FrameController.loginFalse("Khong ket noi dc voi may chu");
		
	}
	
	public void signUp(String Acc,String pass) {
		//Goij 1 trong 3
		_FrameController.startTrans();
		_FrameController.signUpFalse("Tai khoan hoac mk khong dung");
		_FrameController.signUpFalse("Khong ket noi dc voi may chu");
		
	}
}
