package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.IAccount;
import common.IBankController;
import exception.InvalidLoginException;
import exception.NotEnoughMoneyException;
import exception.NotFoundAccountException;

public class BankController extends UnicastRemoteObject implements IBankController {

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

	@Override
	public double getBalanceByName(String name) throws RemoteException, NotFoundAccountException {
		IAccount account = this.bankManager.getAccountByName(name);
		return account.getBalance();
	}

	@Override
	public void makeTransaction(String payerName, String payeeName, int bidId, double money)
			throws RemoteException, NotEnoughMoneyException, NotFoundAccountException {
		IAccount payer = this.bankManager.getAccountByName(payerName);
		
		if (payer.getBalance() < money) {
			throw new NotEnoughMoneyException();
		}
		
		this.bankManager.addBalance(payeeName, money, bidId);
		this.bankManager.subtractBalance(payerName, money, bidId);
		
	}

}
