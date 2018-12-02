package company;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import common.Convention;
import common.IAccountRemote;
import common.ICompanyStockRemote;
import exception.DuplicateCompanyNameException;
import exception.DuplicateLoginNameException;
import exception.DuplicateStockCodeException;
import exception.ExceedMaximumAccountException;

public class CompanyClient {
	
	public static void main (String[] args) {
		try { 
			Registry registry;
			registry = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			
			String[] companyNames = {"1group", "2group", "3group", "4group", "5group", "6group", "7group", "8group", "9group", "10group"};
			for (String name: companyNames) {
				ICompanyStockRemote stockController = (ICompanyStockRemote)registry.lookup(
						Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.COMPANY_STOCK_CONTROLLER_NAME);
				IAccountRemote accountController = (IAccountRemote)registry.lookup(
						Convention.BANK_SERVER_NAME + "/" + Convention.ACCOUNT_CONTROLLER_NAME);
				CompanyController companyController = new CompanyController(name, accountController, stockController);
				
				companyController.registerBank();
				companyController.registerStockExchange(name.substring(0, 3).toUpperCase());
				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new MessageRetrievingTask(companyController), 0, Convention.COMPANY_RETRIEVE_MESSAGE_PERIOD);
				
				
			}
			System.in.read();
		} catch (RemoteException | NotBoundException e) {
			System.out.println("Server error");
			e.printStackTrace();
		} catch (DuplicateLoginNameException e) {
			System.out.println("Company name does exist");
		} catch (ExceedMaximumAccountException e) {
			System.out.println("Maximum account");
		} catch (DuplicateCompanyNameException e) {
			System.out.println("");
		} catch (DuplicateStockCodeException e) {
			System.out.println("Duplicate stock code");
		} catch (Exception e) {
			System.out.println("Unexpected error");
			e.printStackTrace();
		}
	}
}
