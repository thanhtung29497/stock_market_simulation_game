package ui.player;

import java.util.ArrayList;

import common.IBankMessage;
import common.IStock;
import common.Message;
import common.MessageType;
import player.HumanPlayer;
import common.IStockMessage;

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
			if (message.getType().equals(MessageType.AdjustStockPrice)) {
				for (IStock stock: ((IStockMessage) message).getStocks().toArray()) {
					System.out.println(stock.getCode() + ": " + stock.getPrice());
				}
			}
		});
	}
	
	public PlayerFrameController(HumanPlayer player) {
		this.player = player;
	}
	
	public HumanPlayer getPlayer() {
		return this.player;
	}
}
