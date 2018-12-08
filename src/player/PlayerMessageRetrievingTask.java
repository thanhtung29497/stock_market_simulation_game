package player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.TimerTask;

import common.IBankMessage;
import common.IBidCollection;
import common.IMessage;
import common.IRankCollection;
import common.IStock;
import common.IStockCollection;
import common.MessageType;
import exception.NotFoundAccountException;
import ui.player.PlayerFrameController;

public class PlayerMessageRetrievingTask extends TimerTask {
	private PlayerFrameController viewController;
	private PlayerController modelController;
	private PlayerClient client;

	private void retrieveBankMessages() {
		ArrayList<IBankMessage> bankMessages;
		try {
			bankMessages = this.modelController.retrieveBankMessages();
			if (!bankMessages.isEmpty()) {
				IBankMessage lastMessage = bankMessages.get(bankMessages.size() - 1);
				this.viewController.setBalance(lastMessage.getBalance());
				this.viewController.setMoney(this.modelController.getTotalAmount());
				this.viewController.addBankMessages(bankMessages);
			}
		} catch (RemoteException e) {
			this.viewController.showMessage("Error","Failed to connect to server");
		} catch (NotFoundAccountException e) {
			this.viewController.showMessage("Error","Something went wrong with your account");
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
				IStockCollection stocks = this.modelController.getAllStocks();
				IStockCollection ownStocks = this.modelController.getOwnStocks();
				IBidCollection bids = this.modelController.getAllBids();
				IRankCollection ranks = this.modelController.getRankBoard();
				
				this.viewController.updateStocksAndBids(
						this.combineStockBoardAndOwnStocks(stocks, ownStocks), bids);
				this.viewController.updateRank(ranks);
				this.viewController.setRank(this.modelController.getInfo().getRank());					
				this.viewController.setMoney(this.modelController.getTotalAmount());
				
				
				stockExchangeMessages.removeIf(message -> message.getType() == MessageType.UpdateRank
						|| message.getType() == MessageType.UpdateBid);
				for (IMessage message: stockExchangeMessages) {
					if (message.getType() == MessageType.PostBid 
							|| message.getType() == MessageType.MatchBid) {
						this.viewController.showMessage("Notification", message.getMessage());
					}
				}
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
	
	public PlayerMessageRetrievingTask(PlayerFrameController viewController, PlayerController modelController, PlayerClient client) {
		this.viewController = viewController;
		this.modelController = modelController;
		this.client = client;
	}	
}
