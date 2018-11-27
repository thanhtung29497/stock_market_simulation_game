package stockexchange;

import common.IBidCollection;
import common.IBidMessage;
import common.Message;
import common.MessageType;

public class BidMessage extends Message implements IBidMessage {
	
	private static final long serialVersionUID = 1L;
	private IBidCollection bids;

	public BidMessage(MessageType type, String message, IBidCollection bids) {
		super(message, type);
		this.bids = bids;
	}
	
	public BidMessage(MessageType type, String message) {
		super(message, type);
		this.bids = new BidCollection();
	}

	@Override
	public IBidCollection getBids() {
		return this.bids;
	}
	
}
