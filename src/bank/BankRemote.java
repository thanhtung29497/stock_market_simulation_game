package bank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.BidType;
import common.IAccount;
import common.IBankRemote;
import common.IBid;
import exception.NotEnoughMoneyException;
import exception.NotFoundAccountException;
import exception.OfferorNotEnoughMoneyException;
import ui.server.BankServerFrame;

public class BankRemote extends UnicastRemoteObject implements IBankRemote {

	private static final long serialVersionUID = 1L;
	BankManager bankManager;
	private BankServerFrame view;
	
	protected BankRemote(BankManager bankManager, BankServerFrame view) throws RemoteException {
		super();
		this.bankManager = bankManager;
		this.view = view;
	}
	
	@Override
	public void start() throws RemoteException {
		this.bankManager.start();
		this.view.addMessage("Bank server start");
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
	public void makeTransaction(IBid bid, String offereeId)
			throws RemoteException, NotEnoughMoneyException, NotFoundAccountException, OfferorNotEnoughMoneyException {
		
		IAccount payer;
		IAccount payee;
		double money = bid.getValue();
		String stockCode = bid.getStock().getCode();
		double offerPrice = bid.getOfferPrice();
		int quantity = bid.getQuantity();
		
		if (bid.getType() == BidType.Buy) {
			payer = this.bankManager.getAccountByName(bid.getOfferorName());
			payee = this.bankManager.getAccountById(offereeId);
		} else {
			payer = this.bankManager.getAccountById(offereeId);
			payee = this.bankManager.getAccountByName(bid.getOfferorName());
		}

		
		try {
			this.bankManager.subtractBalance(payer.getId(), money, bid.getId());
		} catch (NotEnoughMoneyException e) {
			if (bid.getType() == BidType.Buy) {
				throw new OfferorNotEnoughMoneyException(payer.getName());
			} else {
				throw new NotEnoughMoneyException();
			}
		}
		
		this.bankManager.addBalance(payee.getId(), money, bid.getId());
		
		this.view.addMessage(
				payer.getName() 
				+ " transfer " + String.format("%.2f", money) + "$" 
				+ " to " + payee.getName() 
				+ " to buy " + quantity 
				+ " stock " + stockCode
				+ " with offer price " + String.format("%.2f", offerPrice) + "$");
		
	}

	@Override
	public void end() throws RemoteException {
		this.bankManager.end();
	}

}
