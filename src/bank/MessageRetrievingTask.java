package bank;

import java.util.ArrayList;
import java.util.TimerTask;

import common.IBankMessage;
import common.Message;
import player.HumanPlayer;
import ui.player.PlayerFrameController;

public class MessageRetrievingTask extends TimerTask {
	private PlayerFrameController controller;

	@Override
	public void run() {
		try {
			HumanPlayer player = this.controller.getPlayer();
			
			//	Retrieve and display messages from Bank server
			ArrayList<IBankMessage> bankMessages = player.retrieveBankMessages();
			if (!bankMessages.isEmpty()) {
				this.controller.addBankMessages(bankMessages);
			}
			
			//	Retrieve and display messages from Stock Exchange server
			ArrayList<Message> stockExchangeMessages = player.retrieveStockExchangeMessages();
			if (!stockExchangeMessages.isEmpty()) {
				this.controller.addStockExchangeMessages(stockExchangeMessages);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public MessageRetrievingTask(PlayerFrameController controller) {
		this.controller = controller;
	}	
}
