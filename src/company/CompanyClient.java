package company;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Convention;
import common.ICompanyStockRemote;

public class CompanyClient {
	
	public static void main (String[] args) {
		try {
			Registry registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			
			String[] companyNames = {"1group", "2group", "3group", "4group", "5group", "6group", "7group", "8group", "9group", "10group"};
			for (String name: companyNames) {
				ICompanyStockRemote stockController = (ICompanyStockRemote)registry.lookup(
						Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.COMPANY_STOCK_CONTROLLER_NAME);
				Company company = new Company(name, stockController);
				company.registerStockExchange(name.substring(0, 3).toUpperCase());
			}
			System.in.read();
		} catch (Exception e) {
			System.out.println("Exception:");
			e.printStackTrace();	
		}
	}
}
