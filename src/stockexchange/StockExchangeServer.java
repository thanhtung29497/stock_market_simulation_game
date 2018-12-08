package stockexchange;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import common.Convention;
import common.IBankRemote;
import ui.server.StockExchangeFrame;

public class StockExchangeServer {
	
	private Registry stockExchangeRegistry;
	private Registry bankExchangeRegistry;
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
			frame = new StockExchangeFrame(this);
			frame.setVisible(true);
			System.setProperty("java.rmi.server.hostname", Convention.STOCK_EXCHANGE_HOST_NAME);
			try {
				this.stockExchangeRegistry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			} catch (RemoteException e) {
				this.stockExchangeRegistry = LocateRegistry.getRegistry(Convention.BANK_HOST_NAME, Registry.REGISTRY_PORT);
			} finally  {
			
				bankExchangeRegistry = LocateRegistry.getRegistry(Convention.BANK_HOST_NAME, Registry.REGISTRY_PORT);
				IBankRemote bankController = (IBankRemote)bankExchangeRegistry.lookup(
						Convention.BANK_URL + "/" + Convention.BANK_CONTROLLER_NAME);
				this.manager = new StockExchangeManager(bankController);
				PlayerStockRemote playerStockController = new PlayerStockRemote(manager);
				stockExchangeRegistry.rebind(
						Convention.STOCK_EXCHANGE_URL + "/" + 
						Convention.PLAYER_STOCK_CONTROLLER_NAME, playerStockController);
				CompanyStockRemote companyStockController = new CompanyStockRemote(manager);
				stockExchangeRegistry.rebind(
						Convention.STOCK_EXCHANGE_URL + "/" + 
						Convention.COMPANY_STOCK_CONTROLLER_NAME, companyStockController);
				
				System.out.println("Stock Exchange Server is ready\n");
			
			
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new ViewUpdatingTask(manager, frame), 0, Convention.STOCK_SERVER_UPDATING_TASK);
			} 
		} catch (Exception e) {
			this.frame.ShowMessage("Error", e.getMessage() + e.getStackTrace());
		}
	}
	
	public static void main (String[] argv) {
		new StockExchangeServer();
	}
}
