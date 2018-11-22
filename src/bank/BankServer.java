package bank;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Convention;

public class BankServer {
	private static Registry registry;
	
	public static void main (String[] argv) {
		try {
			BankManager bankManager = new BankManager();
			AccountController accountController = new AccountController(bankManager);
			BankController bankController = new BankController(bankManager);
			
			registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			registry.rebind(Convention.BANK_SERVER_NAME + "/" + Convention.ACCOUNT_CONTROLLER_NAME, accountController);
			registry.rebind(Convention.BANK_SERVER_NAME + "/" + Convention.BANK_CONTROLLER_NAME, bankController);
			
			System.out.println("Bank Server is ready\n");
		} catch (Exception e) {
			System.out.println("Bank Server is failed due to:");
			e.printStackTrace();
		}
	}
}
