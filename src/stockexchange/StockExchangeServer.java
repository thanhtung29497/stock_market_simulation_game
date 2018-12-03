package stockexchange;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import common.Convention;
import common.IBankRemote;
import ui.server.StockExchangeFrame;

public class StockExchangeServer {
	
	private static Registry registry;
	private StockExchangeManager manager;
	private StockExchangeFrame frame;
	
	public void start(int gameDuration, int sessionPeriod, int initialShareNumber, double initialStockPrice) throws RemoteException {
		Convention.GAME_DURATION = gameDuration * 60000;
		Convention.STOCK_EXCHANGE_SESSION = sessionPeriod * 1000;
		Convention.INITIAL_SHARE_NUMBER = initialShareNumber;
		Convention.INITIAL_SHARE_PRICE = initialStockPrice;
		this.manager.start();
	}
	
	public void stop() throws RemoteException {
		this.manager.end();
	}
	
	public StockExchangeServer() {
		try {
			System.setProperty("java.rmi.server.hostname", Convention.HOST_NAME);
			registry = LocateRegistry.getRegistry(Convention.HOST_NAME, Registry.REGISTRY_PORT);
			IBankRemote bankController = (IBankRemote)registry.lookup(
					Convention.URL + "/" + Convention.BANK_SERVER_NAME + "/" + Convention.BANK_CONTROLLER_NAME);
			
			this.manager = new StockExchangeManager(bankController);
			PlayerStockRemote playerStockController = new PlayerStockRemote(manager);
			registry.rebind(
					Convention.URL + "/" +
					Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + 
					Convention.PLAYER_STOCK_CONTROLLER_NAME, playerStockController);
			CompanyStockRemote companyStockController = new CompanyStockRemote(manager);
			registry.rebind(
					Convention.URL + "/" + 
					Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + 
					Convention.COMPANY_STOCK_CONTROLLER_NAME, companyStockController);
			
			System.out.println("Stock Exchange Server is ready\n");
			frame = new StockExchangeFrame(this);
			frame.setVisible(true);
			
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new ViewUpdatingTask(manager, frame), 0, Convention.STOCK_SERVER_UPDATING_TASK);
		} catch (Exception e) {
			System.out.println("Stock Exchange Server is failed due to:");
			e.printStackTrace();
		}
	}
	
	public static void main (String[] argv) {
		new StockExchangeServer();
	}
}
