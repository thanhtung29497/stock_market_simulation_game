package stockexchange;

import common.IRank;

public class Rank implements IRank {
	
	private static final long serialVersionUID = 1L;
	private String playerName;
	private int rank;
	private double amount;
	
	public Rank(String playerName, int rank, double amount) {
		this.playerName = playerName;
		this.rank = rank;
		this.amount = amount;
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}

	@Override
	public int getRank() {
		return this.rank;
	}

	@Override
	public double getAmount() {
		return this.amount;
	}

	@Override
	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
