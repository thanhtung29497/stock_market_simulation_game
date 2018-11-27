package stockexchange;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import common.IRank;
import common.IRankCollection;

public class RankCollection implements IRankCollection {
	
	private class RankComparator implements Comparator<IRank> {

		@Override
		public int compare(IRank rank0, IRank rank1) {
			if (rank0.getAmount() > rank1.getAmount()) {
				return 1;
			}
			return 0;
		}
	}

	private static final long serialVersionUID = 1L;
	private ArrayList<IRank> ranks;
	
	public RankCollection() {
		this.ranks = new ArrayList<>();
	}

	@Override
	public int getRankByName(String name) {
		for (IRank rank: this.ranks) {
			if (rank.getPlayerName().equals(name)) {
				return rank.getRank();
			}
		}
		return 0;
	}

	@Override
	public ArrayList<IRank> getRankBoard() {
		return this.ranks;
	}
	
	private void sort() {
		this.ranks.sort(new RankComparator());
		int currentRank = 0;
		for (IRank rank: this.ranks) {
			rank.setRank(++currentRank);
		}
	}

	@Override
	public void addPlayer(String playerName, double amount) {
		this.ranks.add(new Rank(playerName, 0, amount));
		this.sort();
	}

	@Override
	public void updateAmount(String playerName, double amount) {
		for (IRank rank: this.ranks) {
			if (rank.getPlayerName().equals(playerName)) {
				rank.setAmount(amount);
			}
		}
		this.sort();
	}

	@Override
	public void updateAllAmounts(HashMap<String, Double> amounts) {
		for (IRank rank: this.ranks) {
			double amount = amounts.get(rank.getPlayerName());
			rank.setAmount(amount);
		}
		this.sort();
	}	
}
