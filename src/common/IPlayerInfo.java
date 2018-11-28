package common;

import java.io.Serializable;

public interface IPlayerInfo extends Serializable{
	public String getName();
	public double getBalance();
	public double getMoney();
	public int getRank();
	public String getId();
	public String getPassword();
	public void setName(String name);
	public void setBalance(double balance);
	public void setMoney(double money);
	public void setRank(int rank);
	public void setPassword(String password);
	public void setId(String id);
	public void init(IAccount account);
}
