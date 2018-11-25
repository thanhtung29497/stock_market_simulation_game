package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;

public interface ICompanyStockRemote extends Remote {
	public IStock register(String companyName, String stockCode) throws RemoteException, DuplicateCompanyNameException, DuplicateStockCodeException;
	public void responseBid(IBid bid) throws RemoteException;
	public ArrayList<Message> retrieveMessage() throws RemoteException;
}
