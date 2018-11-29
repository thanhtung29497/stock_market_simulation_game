package common;

public class Message implements IMessage {
	
	private static final long serialVersionUID = 1L;
	private String message;
	private MessageType type;
	
	public Message(MessageType type, String message) {
		this.message = message;
		this.type = type;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public MessageType getType() {
		return this.type;
	}
	
}
