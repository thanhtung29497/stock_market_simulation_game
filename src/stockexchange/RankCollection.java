package stockexchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import common.Convention;
import common.IRank;
import common.IRankCollection;

public class RankCollection implements IRankCollection {
	
	private interface SerializableComparator<T> extends Comparator<T>, Serializable {};
	private Comparator<IRank> rankComparator = (SerializableComparator<IRank>)((IRank rank0, IRank rank1)
			-> {
				if (rank0.getAmount() < rank1.getAmount()) {
					return 1;
				} else if (rank0.getAmount() > rank1.getAmount()) {
					return -1;
				} else if (rank0.getPlayerName().compareTo(rank1.getPlayerName()) > 0) {
					return 1;
				} else {
					return -1;
				}
			});

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
		this.ranks.sort(this.rankComparator);
		int currentRank = 0;
		for (IRank rank: this.ranks) {
			rank.setRank(++currentRank);
		}
	}
	
	private Boolean hasPlayer(String playerName) {
		for (IRank rank: this.ranks) {
			if (rank.getPlayerName().equals(playerName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void addPlayerIfAbsent(String playerName, double amount) {
		if (this.hasPlayer(playerName)) {
			return;
		}
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

	@Override
	public void resetRank() {
		for (IRank rank: this.ranks) {
			rank.setAmount(Convention.INITIAL_BALANCE);
		}
		this.sort();
	}	
}
