package company;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

import common.Convention;
import common.IAccountRemote;
import common.ICompanyStockRemote;
import common.IStockCollection;
import common.Utility;
import exception.DuplicateCompanyNameException;
import exception.DuplicateLoginNameException;
import exception.DuplicateStockCodeException;
import exception.ExceedMaximumAccountException;
import exception.NotFoundAccountException;
import ui.bot.CompanyFrame;

public class CompanyClient {
	
	private Registry registry;
	private CompanyFrame view;
	private ICompanyStockRemote stockRemote;
	private IAccountRemote accountRemote;
	
	public CompanyClient() {
		try {
			System.setProperty("java.rmi.server.hostname", Convention.HOST_NAME);
			this.view = new CompanyFrame(this);
			this.view.setVisible(true);
			
			this.registry = LocateRegistry.getRegistry(Convention.HOST_NAME, Registry.REGISTRY_PORT);
			this.stockRemote = (ICompanyStockRemote)registry.lookup(
					Convention.URL + "/" + Convention.STOCK_EXCHANGE_SERVER_NAME + "/" + Convention.COMPANY_STOCK_CONTROLLER_NAME);
			this.accountRemote = (IAccountRemote)registry.lookup(
					Convention.URL + "/" + Convention.BANK_SERVER_NAME + "/" + Convention.ACCOUNT_CONTROLLER_NAME);
			
			IStockCollection stocks = this.stockRemote.getCompanyStocks();
			this.view.updateCompanyTable(stocks);
			
		} catch (RemoteException | NotBoundException e) {
			this.view.showMessage("Error", e.getMessage() + "\n" + e.getStackTrace());
		}
	}
	
	public void addCompany() {
		try {
			String name = Utility.generateCompanyName();
			String stockCode = Utility.generateStockCode();
			
			CompanyController companyController = new CompanyController(name, 
				this.accountRemote, this.stockRemote);
			companyController.registerBank();
			companyController.registerStockExchange(stockCode);
			
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new MessageRetrievingTask(companyController, this.view), 0, Convention.COMPANY_RETRIEVE_MESSAGE_PERIOD);
			
		} catch (RemoteException e) {
			this.view.showMessage("Error", "Failed to connect to server");
		} catch (DuplicateLoginNameException | DuplicateCompanyNameException e) {
			this.view.showMessage("Error", "Duplicate company name");
		} catch (ExceedMaximumAccountException e) {
			this.view.showMessage("Error", "Exceed maximum account");
		} catch (DuplicateStockCodeException e) {
			this.view.showMessage("Error", "Duplicate stock code");
		} catch (NotFoundAccountException e) {
			this.view.showMessage("Error", "Something went wrong with company account");
		}
		
	}
	
	public static void main (String[] args) {
		new CompanyClient();
	}
}
