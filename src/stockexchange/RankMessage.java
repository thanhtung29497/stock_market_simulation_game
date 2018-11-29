package stockexchange;

import common.IRankCollection;
import common.IRankMessage;
import common.Message;
import common.MessageType;

public class RankMessage extends Message implements IRankMessage  {

	private static final long serialVersionUID = 1L;
	private IRankCollection ranks;

	public RankMessage(MessageType type, String message, IRankCollection ranks) {
		super(type, message);
		this.ranks = ranks;
	}

	@Override
	public IRankCollection getRankBoard() {
		return this.ranks;
	}

}
