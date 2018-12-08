package bank;

import exception.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import common.*;

public class BankManager {
	
	private ArrayList<Account> accounts;
	private HashMap<String, ArrayList<IBankMessage>> messageMap;
	private Timer timer;

	public BankManager() {
		this.accounts = new ArrayList<>();
		this.messageMap = new HashMap<>();
	}
	
	public void start() {
		AccountAccumulationTask accountAccumulationTask = new AccountAccumulationTask(this);
		for (Account account: accounts) {
			account.updateBalance(Convention.INITIAL_BALANCE);
		}
		timer = new Timer(true);
		timer.scheduleAtFixedRate(accountAccumulationTask, Convention.ACCOUNT_ACCUMULATION_PERIOD, Convention.ACCOUNT_ACCUMULATION_PERIOD);
	}
	
	public void end() {
		this.timer.cancel();
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
		this.messageMap.put(account.getId(), new ArrayList<>());
	}
	
	public ArrayList<IBankMessage> getMessagesById(String id) {
		return this.messageMap.get(id);
	}
	
	private void addMessage(String id, IBankMessage message) {
		ArrayList<IBankMessage> messages = this.getMessagesById(id);
		messages.add(message);
		this.messageMap.put(id, messages);
	}
	
	public void accumulateAccounts() {
		this.accounts.forEach(account -> {
			double balance = account.getBalance();
			double interest = balance * Convention.BANK_INTEREST_RATE;
			account.updateBalance(balance + interest);
			this.addMessage(account.getId(), new BankMessage(MessageType.UpdateBalance, 
					"Increased periodically by " + Convention.BANK_INTEREST_RATE * 100 + "% interest", account.getBalance()));
		});
	}
	
	public ArrayList<IBankMessage> retrieveMessages(String id) {
		ArrayList<IBankMessage> messages = this.getMessagesById(id);
		this.messageMap.put(id, new ArrayList<>());
		return messages;
	}
	
	public Account getAccountById(String id) throws NotFoundAccountException {
		for (Account account: this.accounts) {
			if (account.getId().equals(id)) {
				return account;
			}
		}
		throw new NotFoundAccountException(id);
	}
	
	public Account getAccountByName(String name) throws NotFoundAccountException {
		for (Account account: this.accounts) {
			if (account.getName().equals(name)) {
				return account;
			}
		}
		throw new NotFoundAccountException(name);
	}
	
	public synchronized void addBalance(String id, double amount, int bidId) throws NotFoundAccountException {
		IAccount account = this.getAccountById(id);
		account.updateBalance(account.getBalance() + amount);
		this.addMessage(account.getId(), new BankMessage(MessageType.UpdateBalance, 
				"Increased by " + String.format("%.2f", amount) + " due to bid " + bidId, account.getBalance()));
	}
	
	public synchronized void subtractBalance(String id, double amount, int bidId) throws NotFoundAccountException, NotEnoughMoneyException {
		IAccount account = this.getAccountById(id);
		if (account.getBalance() < amount) {
			throw new NotEnoughMoneyException();
		}
		account.updateBalance(account.getBalance() - amount);
		this.addMessage(account.getId(), new BankMessage(MessageType.UpdateBalance, 
				"Decreased by " + String.format("%.2f", amount) + " due to bid " + bidId, account.getBalance()));
	}
}
