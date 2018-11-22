package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;

public interface ICompanyStockController extends Remote {
	public void register(ICompany company) throws RemoteException, DuplicateCompanyNameException;
	public IStock issueStock(String stockCode) throws RemoteException, DuplicateStockCodeException;
	public void responseBid(IBid bid) throws RemoteException;
}
