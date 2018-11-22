package bank;

import common.*;

public class Account implements IAccount {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private double balance;
	
	public Account(String name, String password) {
		this.name = name;
		this.password = password;
		this.balance = Convention.INITIAL_BALANCE;
	}

	@Override
	public void changeName(String name) {
		this.name = name;
	}

	@Override
	public void changePassword(String password) {
		this.password = password;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Boolean checkPassword(String password) {
		return this.password.equals(password);
	}

	@Override
	public void addBalance(double balance) {
		this.balance += balance;
	}

	@Override
	public double getBalance() {
		return balance;
	}

	@Override
	public String getPassword() {
		return this.password;
	}
	
}
