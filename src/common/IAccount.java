package common;

import java.io.Serializable;
import java.rmi.Remote;

public interface IAccount extends Remote, Serializable{
	public void changeName(String name);
	public void changePassword(String password);
	public String getName();
	public Boolean checkPassword(String password);
	public double getBalance();
	public String getPassword();
	public void updateBalance(double balance);
	public int getId();
}
