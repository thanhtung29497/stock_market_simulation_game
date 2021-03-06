package player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.TimerTask;

import common.IBankMessage;
import common.IBidMessage;
import common.IMessage;
import common.IRankCollection;
import common.IRankMessage;
import common.IStock;
import common.IStockCollection;
import common.IStockMessage;
import common.MessageType;
import exception.NotFoundAccountException;
import ui.player.PlayerFrameController;

public class MessageRetrievingTask extends TimerTask {
	private PlayerFrameController viewController;
	private PlayerController modelController;
	private PlayerClient client;
	
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
				IBankMessage lastMessage = bankMessages.get(bankMessages.size() - 1);
				this.viewController.setBalance(lastMessage.getBalance());
				this.viewController.setMoney(this.modelController.getTotalAmount());
				this.testPrintBankMessage(bankMessages);
				this.viewController.addBankMessages(bankMessages);
			}
		} catch (RemoteException e) {
			this.viewController.showMessage("login false","Failed to connect to server");
		} catch (NotFoundAccountException e) {
			this.viewController.showMessage("login false","Something went wrong with your account");
		}
	}
	
	private void checkGameStatus() {
		try {
			if (this.modelController.getCurrentTime().isZero()) {
				if (!this.client.isTimeOut()) {
					this.client.endGame();
				}
			} else if (this.client.isTimeOut()) {
				this.client.startGame();
			}
		} catch (RemoteException e) {
			this.viewController.showMessage("Server error", "Failed to connect to server");
		}
	}
	
	private IStockCollection combineStockBoardAndOwnStocks(IStockCollection stocks, IStockCollection ownStocks) {
		for (IStock stock: ownStocks.toArray()) {
			String stockCode = stock.getCode();
			int quantity = ownStocks.getStockQuantity(stockCode);
			stocks.addQuantity(stockCode, quantity);
		}
		return stocks;
	}
	
	private void retrieveStockMessages() {
		try {
			ArrayList<IMessage> stockExchangeMessages = this.modelController.retrieveStockExchangeMessages();			
			if (!stockExchangeMessages.isEmpty()) {
				for (IMessage message: stockExchangeMessages) {
					if (message.getType() == MessageType.UpdateStock) {
						
						IStockCollection stocks = ((IStockMessage)message).getStocks();
						IStockCollection ownStocks = this.modelController.getOwnStocks();
						this.viewController.updateStocksAndBids(this.combineStockBoardAndOwnStocks(stocks, ownStocks), 
								this.modelController.getAllBids());
						
					} else if (message.getType() == MessageType.UpdateBid) {
						
						IStockCollection stocks = this.modelController.getAllStocks();
						IStockCollection ownStocks = this.modelController.getOwnStocks();
						this.viewController.updateStocksAndBids(this.combineStockBoardAndOwnStocks(stocks, ownStocks), 
								((IBidMessage)message).getBids());
						
					} else if (message.getType() == MessageType.UpdateRank) {
						
						IRankCollection ranks = ((IRankMessage)message).getRankBoard();
						this.modelController.info.setRank(ranks.getRankByName(this.modelController.getInfo().getName()));
						this.viewController.updateRank(ranks);
						this.viewController.setRank(this.modelController.getInfo().getRank());
					}
					
					this.viewController.setMoney(this.modelController.getTotalAmount());
				}
				
				// this.testPrintStockMessage(stockExchangeMessages);
				
				stockExchangeMessages.removeIf(message -> message.getType() == MessageType.UpdateRank
						|| message.getType() == MessageType.UpdateBid);
				this.viewController.addStockExchangeMessages(stockExchangeMessages);
			}
		} catch(RemoteException e) {
			this.viewController.showMessage("Server error", "Failed to connect to server");
		} catch (NotFoundAccountException e) {
			this.viewController.showMessage("Error", "Something went wrong with your account");
		}
	}
	
	@Override
	public void run() {
		try {
			this.checkGameStatus();
			this.retrieveBankMessages();
			this.retrieveStockMessages();
		} catch (Exception e) {
			System.out.println("Unexpected error:");
			e.printStackTrace();
		}
	}
	
	public MessageRetrievingTask(PlayerFrameController viewController, PlayerController modelController, PlayerClient client) {
		this.viewController = viewController;
		this.modelController = modelController;
		this.client = client;
	}	
}
