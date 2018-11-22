package common;

import java.io.Serializable;

public interface IAccount extends Serializable{
	public void changeName(String name);
	public void changePassword(String password);
	public String getName();
	public Boolean checkPassword(String password);
	public void addBalance(double balance);
	public double getBalance();
	public String getPassword();
}
