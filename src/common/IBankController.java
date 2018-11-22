package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

import exception.InvalidLoginException;
import exception.NotFoundAccountException;

public interface IBankController extends Remote {
	public IAccount login(String name, String password) throws RemoteException, InvalidLoginException, NotFoundAccountException;
}
