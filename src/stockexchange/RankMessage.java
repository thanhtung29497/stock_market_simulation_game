package stockexchange;

import java.util.ArrayList;

import common.IRank;
import common.IRankCollection;
import common.IRankMessage;
import common.Message;
import common.MessageType;

public class RankMessage extends Message implements IRankMessage  {

	private static final long serialVersionUID = 1L;
	private IRankCollection ranks;

	public RankMessage(MessageType type, String message, IRankCollection ranks) {
		super(message, type);
		this.ranks = ranks;
	}

	@Override
	public ArrayList<IRank> getRankBoard() {
		return this.ranks.getRankBoard();
	}

}
