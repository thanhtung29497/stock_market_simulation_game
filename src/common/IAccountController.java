package common;

import java.rmi.*;
import exception.*;

public interface IAccountController extends Remote {
	public void register(String name, String password) throws ExceedMaximumAccountException, DuplicateLoginNameException, RemoteException;
	public void login(String name, String password) throws NotFoundAccountException, InvalidLoginException, RemoteException;
	public void deposit(Double amount) throws RemoteException;
	public void withdraw(Double amount) throws RemoteException;
	public IAccount getAccount() throws RemoteException;
}
