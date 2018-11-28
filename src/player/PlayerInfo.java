package player;

import common.IAccount;
import common.IPlayerInfo;

public class PlayerInfo implements IPlayerInfo {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private double money;
	private double balance;
	private int rank;
	private String id;
	
	public PlayerInfo() {
		
	}
	
	public PlayerInfo(String name, double money, double balance, int rank) {
		this.name = name;
		this.money = money;
		this.balance = balance;
		this.rank = rank;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public double getBalance() {
		return this.balance;
	}

	@Override
	public double getMoney() {
		return this.money;
	}

	@Override
	public int getRank() {
		return this.rank;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public void setMoney(double money) {
		this.money = money;
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void init(IAccount account) {
		this.setId(account.getId());
		this.setName(account.getId());
		this.setBalance(account.getBalance());
		this.setPassword(account.getPassword());
	}
}
