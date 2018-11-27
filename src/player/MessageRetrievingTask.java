package player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.TimerTask;

import common.IBankMessage;
import common.IBidMessage;
import common.IMessage;
import common.IStockMessage;
import common.MessageType;
import exception.NotFoundAccountException;
import ui.player.PlayerFrameController;

public class MessageRetrievingTask extends TimerTask {
	private PlayerFrameController viewController;
	private PlayerController modelController;
	
	private void testPrintBankMessage(ArrayList<IBankMessage> messages) {
		messages.forEach(message -> {
			System.out.println("Bank: " + message.getMessage() + ": " + message.getBalance());
		});
	}
	
	private void testPrintStockMessage(ArrayList<IMessage> messages) {
		messages.forEach(message -> {
			System.out.println("Stock: " + message.getMessage());
		});
	}

	private void retrieveBankMessages() {
		ArrayList<IBankMessage> bankMessages;
		try {
			bankMessages = this.modelController.retrieveBankMessages();
			if (!bankMessages.isEmpty()) {
				this.viewController.setMoney(this.modelController.getTotalAmount());
				this.testPrintBankMessage(bankMessages);
				this.viewController.addBankMessages(bankMessages);
			}
		} catch (RemoteException e) {
			this.viewController.loginFalse("Failed to connect to server");
		} catch (NotFoundAccountException e) {
			this.viewController.loginFalse("Something went wrong with your account");
		}
	}
	
	private void retrieveStockMessages() {
		try {
			ArrayList<IMessage> stockExchangeMessages = this.modelController.retrieveStockExchangeMessages();
			if (!stockExchangeMessages.isEmpty()) {
				for (IMessage message: stockExchangeMessages) {
					if (message.getType() == MessageType.IssueStock 
							|| message.getType() == MessageType.AdjustStockPrice) {
						this.viewController.updateStocks(((IStockMessage)message).getStocks());
					} else if (message.getType() == MessageType.UpdateBid) {
						this.viewController.updateBids(((IBidMessage)message).getBids());
					}
				}
				this.testPrintStockMessage(stockExchangeMessages);
				this.viewController.addStockExchangeMessages(stockExchangeMessages);
			}
		} catch(RemoteException e) {
			this.viewController.loginFalse("Failed to connect to server");
		}
	}
	
	@Override
	public void run() {
		try {
			this.retrieveBankMessages();
			this.retrieveStockMessages();
		} catch (Exception e) {
			System.out.println("Unexpected error:");
			e.printStackTrace();
		}
	}
	
	public MessageRetrievingTask(PlayerFrameController viewController, PlayerController modelController) {
		this.viewController = viewController;
		this.modelController = modelController;
	}	
}
