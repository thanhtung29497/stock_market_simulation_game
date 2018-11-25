package ui.player;

import java.util.ArrayList;

import common.ErrorType;
import common.IAccount;
import common.IBankMessage;
import common.IBidCollection;
import common.IStock;
import common.IStockCollection;
import common.Message;
import common.MessageType;
import player.PlayerClient;
import common.IStockMessage;

public class PlayerFrameController {
	
	private PlayerClient playerClient;
	
	public PlayerFrameController(PlayerClient playerClient) {
		this.playerClient = playerClient;
	}
	
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
	
	public void updateStocks(IStockCollection stocks) {
		
	}
	
	public void updateBids(IBidCollection bids) {
		
	}
	
	public void showLogin() {
		this.playerClient.register("test", "test");
		this.playerClient.loginWithRegisteredName();
	}
	
	public void showError(ErrorType errorType) {
		
	}
	
	public void showMainView(IStockCollection stocks, IBidCollection bids, IAccount account) {
		
	}
}
