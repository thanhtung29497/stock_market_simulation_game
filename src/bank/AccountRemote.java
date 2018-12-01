package bank;

import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.ArrayList;

import common.*;
import exception.*;

public class AccountRemote extends UnicastRemoteObject implements IAccountRemote {

	private static final long serialVersionUID = 1L;
	public static final String DOMAIN = "account";
	private BankManager bankManager;

	protected AccountRemote(BankManager bankManager) throws RemoteException {
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
	public IAccount login(String name, String password) throws RemoteException, NotFoundAccountException, InvalidLoginException {
		Account account = this.bankManager.getAccountByName(name);
		if (!account.checkPassword(password)) {
			throw new InvalidLoginException();
		}
		System.out.println("Login successfully: " + account.getId());
		return account;
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
	public double getBalance(String accountId) throws NotFoundAccountException {
		return this.bankManager.getAccountById(accountId).getBalance();
	}

	@Override
	public ArrayList<IBankMessage> retrieveMessages(String accountId) throws RemoteException {
		return this.bankManager.retrieveMessages(accountId);
	}

	@Override
	public String registerCompany(String companyName) throws DuplicateLoginNameException, RemoteException, ExceedMaximumAccountException {
		if (this.bankManager.checkLoginName(companyName)) {
			throw new DuplicateLoginNameException(companyName);
		}
		Account account = new Account(companyName);
		this.bankManager.addAccount(account);
		System.out.println("Register successfully: Company " + account.getName());
		return account.getId();
	}

	@Override
	public IAccount registerComputer() throws RemoteException, ExceedMaximumAccountException, DuplicateLoginNameException {
		Account account = new Account();
		if (this.bankManager.checkLoginName(account.getName())) {
			throw new DuplicateLoginNameException(account.getName());
		}
		this.bankManager.addAccount(account);
		System.out.println("Register successfully: " + account.getName());
		return account;
	}	
}
