package bank;

import exception.*;

import java.util.ArrayList;
import java.util.Timer;

import common.*;

public class BankManager {
	private ArrayList<Account> accounts;
	public BankManager() {
		accounts = new ArrayList<>();
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
	}
	
	public void accumulateAccounts() {
		this.accounts.forEach(account -> {
			double balance = account.getBalance();
			double interest = balance * Convention.BANK_INTEREST_RATE;
			account.addBalance(interest);
		});
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
