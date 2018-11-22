package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.IAccount;
import common.IBankController;
import exception.InvalidLoginException;
import exception.NotFoundAccountException;

public class BankController extends UnicastRemoteObject implements IBankController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BankManager bankManager;
	
	protected BankController(BankManager bankManager) throws RemoteException {
		super();
		this.bankManager = bankManager;
	}

	@Override
	public IAccount login(String name, String password) throws RemoteException, InvalidLoginException, NotFoundAccountException {
		IAccount account = this.bankManager.getAccountByName(name);
		if (!account.checkPassword(password)) {
			throw new InvalidLoginException();
		}
		return account;
	}

}
