package bank;

import common.IBankMessage;
import common.Message;
import common.MessageType;

public class BankMessage extends Message implements IBankMessage {
	
	private static final long serialVersionUID = 1L;
	private double balance;

	public BankMessage(MessageType type, String message, double balance) {
		super(message, type);
		this.balance = balance;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
}
