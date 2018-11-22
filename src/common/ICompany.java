package common;

import java.rmi.RemoteException;

import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;

public interface ICompany extends IStockOwner {
	public void registerStockExchange() throws RemoteException, DuplicateCompanyNameException;
	public void issueStock(String stockCode) throws DuplicateStockCodeException, RemoteException;
	public String getName();
	public String getStockCode();
	public Boolean hasStock();
	public void setStock(IStock stock);
}
