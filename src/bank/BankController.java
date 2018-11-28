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
	public void start() throws RemoteException {
		this.bankManager.start();
	}

	@Override
	public IAccount login(String id) throws RemoteException, NotFoundAccountException {
		IAccount account = this.bankManager.getAccountById(id);
		return account;
	}

	@Override
	public double getBalanceById(String id) throws RemoteException, NotFoundAccountException {
		IAccount account = this.bankManager.getAccountById(id);
		return account.getBalance();
	}

	@Override
	public void makeTransaction(String payerId, String payeeId, int bidId, double money)
			throws RemoteException, NotEnoughMoneyException, NotFoundAccountException {
		IAccount payer = this.bankManager.getAccountById(payerId);
		
		if (payer.getBalance() < money) {
			throw new NotEnoughMoneyException();
		}
		
		this.bankManager.addBalance(payeeId, money, bidId);
		this.bankManager.subtractBalance(payerId, money, bidId);
		
	}

}
