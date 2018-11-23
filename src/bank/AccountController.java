package bank;

import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.ArrayList;

import common.*;
import exception.*;

public class AccountController extends UnicastRemoteObject implements IAccountController {

	private static final long serialVersionUID = 1L;
	public static final String DOMAIN = "account";
	private BankManager bankManager;
	private Account account;

	protected AccountController(BankManager bankManager) throws RemoteException {
		super();
		this.bankManager = bankManager;
	}

	@Override
	public IAccount register(String name, String password) throws ExceedMaximumAccountException, DuplicateLoginNameException {
		if (this.bankManager.checkLoginName(name)) {
			throw new DuplicateLoginNameException(name);
		}
		Account account = new Account(name, password);
		this.bankManager.addAccount(account);
		System.out.println("Register successfully: " + account.getName());
		return account;
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

	@Override
	public ArrayList<IBankMessage> retrieveMessages() throws RemoteException {
		String name = this.account.getName();
		return this.bankManager.retrieveMessages(name);
	}
	
}
