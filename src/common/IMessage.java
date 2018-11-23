package common;

import java.io.Serializable;

public interface IMessage extends Serializable {
	public String getMessage();
	public MessageType getType();
}
