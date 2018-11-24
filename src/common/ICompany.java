package common;

import java.rmi.RemoteException;

import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;

public interface ICompany extends IStockOwner {
	public void registerStockExchange(String stockCode) throws RemoteException, DuplicateCompanyNameException, DuplicateStockCodeException;
	public String getName();
	public String getStockCode();
	public Boolean hasStock();
	public void setStock(IStock stock);
}
