package bank;

import java.rmi.RemoteException;
import java.rmi.server.*;
import common.*;
import exception.*;

public class AccountController extends UnicastRemoteObject implements IAccountController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String DOMAIN = "account";
	private BankManager bankManager;
	private Account account;

	protected AccountController(BankManager bankManager) throws RemoteException {
		super();
		this.bankManager = bankManager;
	}

	@Override
	public void register(String name, String password) throws ExceedMaximumAccountException, DuplicateLoginNameException {
		if (this.bankManager.checkLoginName(name)) {
			throw new DuplicateLoginNameException(name);
		}
		Account account = new Account(name, password);
		this.bankManager.addAccount(account);
		System.out.println("Register successfully: " + account.getName());
	}

	@Override
	public void login(String name, String password) throws RemoteException, NotFoundAccountException, InvalidLoginException {
		Account account = this.bankManager.getAccountByName(name);
		if (!account.checkPassword(password)) {
			throw new InvalidLoginException();
		}
		this.account = account;
		System.out.println("Login successfully: " + account.getName());
	}

	@Override
	public void deposit(Double amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void withdraw(Double amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IAccount getAccount() {
		return this.account;
	}
	
}
