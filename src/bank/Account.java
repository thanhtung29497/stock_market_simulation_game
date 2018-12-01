package bank;

import common.*;

public class Account implements IAccount {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private double balance;
	private final String id;
	
	public Account(String name, String password) {
		this.name = name;
		this.password = password;
		this.balance = Convention.INITIAL_BALANCE;
		this.id = Utility.generateAccountId();
	}
	
	public Account() {
		this.name = Utility.generateComputerName();
		this.password = Convention.COMPUTER_PLAYER_PASSWORD;
		this.balance = Convention.INITIAL_BALANCE;
		this.id = this.name;
	}
	
	public Account(String name) {
		this.name = name;
		this.password = "";
		this.balance = 0;
		this.id = Utility.generateCompanyId();
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
	public double getBalance() {
		return balance;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void updateBalance(double balance) {
		this.balance = balance;
		
	}

	@Override
	public String getId() {
		return this.id;
	}
	
}
