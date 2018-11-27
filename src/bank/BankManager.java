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
	
	private void addMessage(String name, IBankMessage message) {
		ArrayList<IBankMessage> messages = this.getMessagesByName(name);
		messages.add(message);
		this.messageMap.put(name, messages);
	}
	
	public void accumulateAccounts() {
		this.accounts.forEach(account -> {
			double balance = account.getBalance();
			double interest = balance * Convention.BANK_INTEREST_RATE;
			account.updateBalance(balance + interest);
			this.addMessage(account.getName(), new BankMessage(MessageType.AddBalance, 
					"Increased periodically by " + Convention.BANK_INTEREST_RATE * 100 + "% interest", account.getBalance()));
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
	
	public void addBalance(String name, double amount, int bidId) throws NotFoundAccountException {
		IAccount account = this.getAccountByName(name);
		account.updateBalance(account.getBalance() + amount);
		this.addMessage(name, new BankMessage(MessageType.AddBalance, 
				"Increased by " + amount + " due to bid " + bidId, account.getBalance()));
	}
	
	public void subtractBalance(String name, double amount, int bidId) throws NotFoundAccountException {
		IAccount account = this.getAccountByName(name);
		account.updateBalance(account.getBalance() - amount);
		this.addMessage(name, new BankMessage(MessageType.AddBalance, 
				"Decreased by " + amount + " due to bid " + bidId, account.getBalance()));
	}
}
