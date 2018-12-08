package bank;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Convention;
import ui.server.BankServerFrame;

public class BankServer {
	private static Registry registry;
	private BankServerFrame frame;
	
	public BankServer() {
		try {
			this.frame = new BankServerFrame();
			System.setProperty("java.rmi.server.hostname", Convention.BANK_HOST_NAME);
			BankManager bankManager = new BankManager();
			AccountRemote accountController = new AccountRemote(bankManager, this.frame);
			BankRemote bankController = new BankRemote(bankManager, this.frame);
		
			registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			registry.rebind(
					Convention.BANK_URL + "/" +
					Convention.ACCOUNT_CONTROLLER_NAME, accountController);
			registry.rebind(
					Convention.BANK_URL + "/" + 
					Convention.BANK_CONTROLLER_NAME, bankController);
			
			frame.addMessage("Bank server is ready");
		} catch (Exception e) {
			this.frame.showError("Bank server is failed due to:\n" + e.getStackTrace());
			System.out.println("Bank Server is failed due to:");
			e.printStackTrace();
		}
	}
	
	public static void main (String[] argv) {
		new BankServer();
	}
}
