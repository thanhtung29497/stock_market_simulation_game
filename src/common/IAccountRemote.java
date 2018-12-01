package common;

import java.rmi.*;
import java.util.ArrayList;

import exception.*;

public interface IAccountRemote extends Remote {
	public IAccount registerComputer() throws ExceedMaximumAccountException, DuplicateLoginNameException, RemoteException;
	public IAccount register(String name, String password) throws ExceedMaximumAccountException, DuplicateLoginNameException, RemoteException;
	public String registerCompany(String companyName) throws DuplicateLoginNameException, RemoteException, ExceedMaximumAccountException;
	public IAccount login(String name, String password) throws NotFoundAccountException, InvalidLoginException, RemoteException;
	public void deposit(Double amount) throws RemoteException;
	public void withdraw(Double amount) throws RemoteException;
	public double getBalance(String accountId) throws RemoteException, NotFoundAccountException;
	public ArrayList<IBankMessage> retrieveMessages(String accountId) throws RemoteException;
}
