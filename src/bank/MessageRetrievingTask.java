package bank;

import java.util.ArrayList;
import java.util.TimerTask;

import common.IBankMessage;
import common.Message;
import player.PlayerController;
import ui.player.PlayerFrameController;

public class MessageRetrievingTask extends TimerTask {
	private PlayerFrameController viewController;
	private PlayerController modelController;

	@Override
	public void run() {
		try {
			
			//	Retrieve and display messages from Bank server
			ArrayList<IBankMessage> bankMessages = this.modelController.retrieveBankMessages();
			if (!bankMessages.isEmpty()) {
				this.viewController.addBankMessages(bankMessages);
			}
			
			//	Retrieve and display messages from Stock Exchange server
			ArrayList<Message> stockExchangeMessages = this.modelController.retrieveStockExchangeMessages();
			if (!stockExchangeMessages.isEmpty()) {
				this.viewController.addStockExchangeMessages(stockExchangeMessages);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public MessageRetrievingTask(PlayerFrameController viewController, PlayerController modelController) {
		this.viewController = viewController;
		this.modelController = modelController;
	}	
}
