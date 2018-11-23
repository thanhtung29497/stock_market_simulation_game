package bank;

import exception.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import common.*;

public class BankManager {
	
	private ArrayList<Account> accounts;
	private HashMap<String, ArrayList<IBankMessage>> messageMap;

	public BankManager() {
		this.accounts = new ArrayList<>();
		this.messageMap = new HashMap<>();
		AccountAccumulationTask accountAccumulationTask = new AccountAccumulationTask(this);
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(accountAccumulationTask, 0, Convention.ACCOUNT_ACCUMULATION_PERIOD);
	}
	
	public Boolean checkLoginName(String name) {
		for (Account account: accounts) {
			if (account.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public void addAccount(Account account) throws ExceedMaximumAccountException {
		if (this.accounts.size() == Convention.MAXIMUM_ACCOUNT_NUMBER) {
			throw new ExceedMaximumAccountException();
		}
		this.accounts.add(account);
		this.messageMap.put(account.getName(), new ArrayList<>());
	}
	
	public ArrayList<IBankMessage> getMessagesByName(String name) {
		return this.messageMap.get(name);
	}
	
	public void accumulateAccounts() {
		this.accounts.forEach(account -> {
			double balance = account.getBalance();
			double interest = balance * Convention.BANK_INTEREST_RATE;
			account.updateBalance(balance + interest);
			ArrayList<IBankMessage> messages = this.getMessagesByName(account.getName());
			messages.add(new BankMessage(MessageType.AddBalance, "Your account was increased periodically by 1% interest!", account.getBalance()));
			this.messageMap.put(account.getName(), messages);
		});
	}
	
	public ArrayList<IBankMessage> retrieveMessages(String name) {
		ArrayList<IBankMessage> messages = this.getMessagesByName(name);
		this.messageMap.put(name, new ArrayList<>());
		return messages;
	}
	
	public Account getAccountByName(String name) throws NotFoundAccountException {
		for (Account account: this.accounts) {
			if (account.getName().equals(name)) {
				return account;
			}
		}
		throw new NotFoundAccountException(name);
	}
}
