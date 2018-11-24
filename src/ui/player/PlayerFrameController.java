package ui.player;

import java.util.ArrayList;

import common.IBankMessage;
import common.Message;
import player.HumanPlayer;

public class PlayerFrameController {
	
	private HumanPlayer player;
	
	public void addBankMessages(ArrayList<IBankMessage> messages) {
		messages.forEach(message -> {
			System.out.println(message.getMessage() + ": " + message.getBalance());
		});
	}
	
	public void addStockExchangeMessages(ArrayList<Message> messages) {
		messages.forEach(message -> {
			System.out.println(message.getMessage());
		});
	}
	
	public PlayerFrameController(HumanPlayer player) {
		this.player = player;
	}
	
	public HumanPlayer getPlayer() {
		return this.player;
	}
}
