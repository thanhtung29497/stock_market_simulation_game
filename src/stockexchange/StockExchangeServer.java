package stockexchange;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Convention;
import common.IBankController;

public class StockExchangeServer {
	
	private static Registry registry;
	
	public static void main (String[] argv) {
		try {
			registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			IBankController bankController = (IBankController)registry.lookup(Convention.BANK_SERVER_NAME + "/" + Convention.BANK_CONTROLLER_NAME);
			
			StockExchangeManager stockExchangeManager = new StockExchangeManager(bankController);
			PlayerStockRemote playerStockController = new PlayerStockRemote(stockExchangeManager);
			registry.rebind(Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.PLAYER_STOCK_CONTROLLER_NAME, playerStockController);
			CompanyStockController companyStockController = new CompanyStockController(stockExchangeManager);
			registry.rebind(Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.COMPANY_STOCK_CONTROLLER_NAME, companyStockController);
			
			System.out.println("Stock Exchange Server is ready\n");
		} catch (Exception e) {
			System.out.println("Stock Exchange Server is failed due to:");
			e.printStackTrace();
		}
	}
}
