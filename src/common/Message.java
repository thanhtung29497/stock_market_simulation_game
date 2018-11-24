package common;

public abstract class Message implements IMessage {
	
	private static final long serialVersionUID = 1L;
	private String message;
	private MessageType type;
	
	public Message(String message, MessageType type) {
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
