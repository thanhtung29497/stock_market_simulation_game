package company;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Convention;
import common.ICompanyStockRemote;

public class CompanyClient {
	
	public static void main (String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			ICompanyStockRemote stockController = (ICompanyStockRemote)registry.lookup(
					Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.COMPANY_STOCK_CONTROLLER_NAME);
			
			Company company1 = new Company("Cengroup", stockController);
			company1.registerStockExchange("CEN");
			System.in.read();
		} catch (Exception e) {
			System.out.println("Exception:");
			e.printStackTrace();	
		}
	}
}
