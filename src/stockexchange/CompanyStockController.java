package stockexchange;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.IBid;
import common.ICompany;
import common.ICompanyStockController;
import common.IStock;
import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;

public class CompanyStockController extends UnicastRemoteObject implements ICompanyStockController {

	private static final long serialVersionUID = 1L;
	private StockExchangeManager stockExchangeManager;
	private ICompany company;
	
	protected CompanyStockController(StockExchangeManager stockExchangeManager) throws RemoteException {
		super();
		this.stockExchangeManager = stockExchangeManager;
	}

	@Override
	public void register(ICompany company) throws RemoteException, DuplicateCompanyNameException {
		this.stockExchangeManager.registerCompany(company);
		this.company = company;
		System.out.println("Register successfully: Company " + company.getName());
	}

	@Override
	public IStock issueStock(String stockCode) throws RemoteException, DuplicateStockCodeException {
		IStock issuedStock = this.stockExchangeManager.issueStock(this.company.getName(), stockCode);
		System.out.println("Issue stock successfully: " + issuedStock.getCode());
		return issuedStock;
	}

	@Override
	public void responseBid(IBid bid) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
}
