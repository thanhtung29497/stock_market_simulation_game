package company;

import java.rmi.RemoteException;
import java.util.ArrayList;

import common.IAccountRemote;
import common.ICompanyController;
import common.ICompanyStockRemote;
import common.IMessage;
import common.IStock;
import common.IStockCollection;
import exception.BidNotAvailableException;
import exception.DuplicateCompanyNameException;
import exception.DuplicateLoginNameException;
import exception.DuplicateStockCodeException;
import exception.ExceedMaximumAccountException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.OfferorNotEnoughMoneyException;
import exception.TimeOutException;

public class CompanyController implements ICompanyController {

	private static final long serialVersionUID = 1L;
	private String name;
	private IStock stock;
	private String id;
	private ICompanyStockRemote stockController;
	private IAccountRemote accountController;
	
	public CompanyController(String name, IAccountRemote accountController, ICompanyStockRemote stockController) {
		this.name = name;
		this.accountController = accountController;
		this.stockController = stockController;
	}
	
	@Override
	public void registerStockExchange(String stockCode) throws RemoteException, DuplicateCompanyNameException, DuplicateStockCodeException, NotFoundAccountException {
		IStock stock = this.stockController.register(this.id, stockCode);
		this.setStock(stock);
	}
	
	public String getStockCode() {
		return this.stock.getCode();
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public Boolean hasStock() {
		return this.stock != null;
	}

	@Override
	public void setStock(IStock stock) {
		this.stock = stock;
	}

	@Override
	public void registerBank() throws RemoteException, DuplicateLoginNameException, ExceedMaximumAccountException {
		this.id = this.accountController.registerCompany(this.name);
	}

	@Override
	public ArrayList<IMessage> retrieveMessage() throws RemoteException {
		return this.stockController.retrieveMessage(this.id);
	}

	@Override
	public int getStockQuantity() throws RemoteException {
		return this.stockController.getStockQuantity(this.id, this.stock.getCode());
	}

	@Override
	public void acceptBid(int bidId) throws RemoteException, NotFoundBidException, BidNotAvailableException, NotFoundAccountException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, TimeOutException {
		this.stockController.acceptBid(bidId, this.id);
	}

	@Override
	public double getBalance() throws RemoteException, NotFoundAccountException {
		return this.accountController.getBalance(this.id);
	}

	@Override
	public IStockCollection getCompanyStocks() throws RemoteException {
		return this.stockController.getCompanyStocks();
	}

}
