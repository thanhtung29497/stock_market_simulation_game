package common;

import java.rmi.*;
import java.util.ArrayList;

import exception.*;

public interface IAccountRemote extends Remote {
	public IAccount register(String name, String password) throws ExceedMaximumAccountException, DuplicateLoginNameException, RemoteException;
	public IAccount login(String name, String password) throws NotFoundAccountException, InvalidLoginException, RemoteException;
	public void deposit(Double amount) throws RemoteException;
	public void withdraw(Double amount) throws RemoteException;
	public IAccount getAccount() throws RemoteException;
	public ArrayList<IBankMessage> retrieveMessages(String accountName) throws RemoteException;
}
