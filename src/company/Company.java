package company;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

import common.IBid;
import common.ICompany;
import common.ICompanyStockController;
import common.IStock;
import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;

public class Company implements ICompany {

	private static final long serialVersionUID = 1L;
	private String name;
	private IStock stock;
	private ICompanyStockController stockController;
	
	public Company(String name, ICompanyStockController stockController) {
		this.name = name;
		this.stockController = stockController;
	}
	
	@Override
	public ArrayList<IStock> getStocks() {
		return new ArrayList<IStock>(Arrays.asList(this.stock));
	}
	
	@Override
	public void registerStockExchange(String stockCode) throws RemoteException, DuplicateCompanyNameException, DuplicateStockCodeException {
		IStock stock = this.stockController.register(this.name, stockCode);
		this.setStock(stock);
	}
	
	public String getStockCode() {
		return this.stock.getCode();
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public void processBid(IBid bid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean hasStock() {
		return this.stock != null;
	}

	@Override
	public void setStock(IStock stock) {
		this.stock = stock;
	}

}
