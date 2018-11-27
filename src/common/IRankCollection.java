package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public interface IRankCollection extends Serializable{
	public int getRankByName(String name);
	public ArrayList<IRank> getRankBoard();
	public void addPlayer(String playerName, double amount);
	public void updateAmount(String playerName, double amount);
	public void updateAllAmounts(HashMap<String, Double> amounts);
}
