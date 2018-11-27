package common;

import java.io.Serializable;

public interface IRank extends Serializable{
	public String getPlayerName();
	public int getRank();
	public double getAmount();
	public void setRank(int rank);
	public void setAmount(double amount);
}
