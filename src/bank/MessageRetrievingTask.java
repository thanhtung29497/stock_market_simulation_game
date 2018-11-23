package bank;

import java.util.ArrayList;
import java.util.TimerTask;

import common.IBankMessage;
import player.HumanPlayer;
import ui.player.PlayerFrameController;

public class MessageRetrievingTask extends TimerTask {
	private PlayerFrameController controller;
	
	@Override
	public void run() {
		try {
			HumanPlayer player = this.controller.getPlayer();
			ArrayList<IBankMessage> messages = player.retrieveBankMessages();
			if (!messages.isEmpty()) {
				this.controller.addBankMessages(messages);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public MessageRetrievingTask(PlayerFrameController controller) {
		this.controller = controller;
	}	
}
