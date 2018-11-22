package company;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Convention;
import common.ICompanyStockController;

public class CompanyClient {
	
	public static void main (String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			ICompanyStockController stockController = (ICompanyStockController)registry.lookup(
					Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.COMPANY_STOCK_CONTROLLER_NAME);
			
			Company company1 = new Company("TUNG", stockController);
			company1.registerStockExchange();
			company1.issueStock("TNG");
		} catch (Exception e) {
			System.out.println("Exception:");
			e.printStackTrace();	
		}
	}
}
