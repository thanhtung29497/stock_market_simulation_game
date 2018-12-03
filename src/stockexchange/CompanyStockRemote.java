package stockexchange;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import common.ICompanyStockRemote;
import common.IMessage;
import common.IStock;
import common.IStockCollection;
import exception.BidNotAvailableException;
import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.OfferorNotEnoughMoneyException;
import exception.TimeOutException;

public class CompanyStockRemote extends UnicastRemoteObject implements ICompanyStockRemote {

	private static final long serialVersionUID = 1L;
	private StockExchangeManager stockExchangeManager;
	
	protected CompanyStockRemote(StockExchangeManager stockExchangeManager) throws RemoteException {
		super();
		this.stockExchangeManager = stockExchangeManager;
	}

	@Override
	public IStock register(String companyId, String stockCode) throws RemoteException, DuplicateCompanyNameException, DuplicateStockCodeException, NotFoundAccountException {
		IStock stock = this.stockExchangeManager.issueStock(companyId, stockCode);
		System.out.println("Register successfully: Company " + companyId);
		return stock;
	}

	@Override
	public void acceptBid(int bidId, String companyId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotFoundAccountException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, TimeOutException {
		synchronized (this.stockExchangeManager) {
			this.stockExchangeManager.acceptBid(bidId, companyId);
		}
	}

	@Override
	public ArrayList<IMessage> retrieveMessage(String companyId) throws RemoteException {
		return this.stockExchangeManager.retrieveMessages(companyId);
	}

	@Override
	public int getStockQuantity(String companyId, String stockCode) throws RemoteException {
		return this.stockExchangeManager.getStockQuantityOfCompany(companyId, stockCode);
	}

	@Override
	public IStockCollection getCompanyStocks() throws RemoteException {
		return this.stockExchangeManager.getCompanyStocks();
	}
	
}
