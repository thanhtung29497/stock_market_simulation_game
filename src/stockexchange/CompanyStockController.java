package stockexchange;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import common.IBid;
import common.ICompanyStockRemote;
import common.IStock;
import common.Message;
import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;

public class CompanyStockController extends UnicastRemoteObject implements ICompanyStockRemote {

	private static final long serialVersionUID = 1L;
	private StockExchangeManager stockExchangeManager;
	private IStock stock;
	
	protected CompanyStockController(StockExchangeManager stockExchangeManager) throws RemoteException {
		super();
		this.stockExchangeManager = stockExchangeManager;
	}

	@Override
	public IStock register(String companyName, String stockCode) throws RemoteException, DuplicateCompanyNameException, DuplicateStockCodeException {
		IStock stock = this.stockExchangeManager.issueStock(companyName, stockCode);
		this.stock = stock;
		System.out.println("Register successfully: Company " + companyName);
		return stock;
	}

	@Override
	public void responseBid(IBid bid) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Message> retrieveMessage() throws RemoteException {
		return this.stockExchangeManager.retrieveMessages(stock.getCompanyName(), false);
	}
	
}
