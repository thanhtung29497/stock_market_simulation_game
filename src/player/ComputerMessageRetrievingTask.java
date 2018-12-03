package player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import common.BidStatus;
import common.BidType;
import common.Convention;
import common.IBankMessage;
import common.IBid;
import common.IBidCollection;
import common.IBidMessage;
import common.IMessage;
import common.IStock;
import common.IStockCollection;
import common.MessageType;
import exception.BidNotAvailableException;
import exception.NonPositiveStockQuantityException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.NotFoundStockCodeException;
import exception.OfferorNotEnoughMoneyException;
import exception.OutOfStockPriceRangeException;
import exception.TimeOutException;
import ui.bot.ComputerPlayerFrame;

public class ComputerMessageRetrievingTask extends TimerTask {

	private ComputerPlayer modelController;
	private final int ACCEPT_BID_PERCENT = 5;
	private final int POST_BID_PERCENT = 50;
	private Random random;
	private ComputerPlayerFrame viewController;
	
	private Boolean doesAcceptBid(IBid bid) throws RemoteException {
		if (bid.getStatus() == BidStatus.Matched || this.modelController.getCurrentTime().isZero()) {
			return false;
		}
		if (bid.getType() == BidType.Sell && this.modelController.getInfo().getBalance() < bid.getValue()) {
			return false;
		}
		if (bid.getType() == BidType.Buy 
				&& this.modelController.getOwnStocks().getStockQuantity(bid.getStock().getCode()) < bid.getQuantity()) {
			return false;
		}
		
		return this.random.nextInt(100) < this.ACCEPT_BID_PERCENT;
	}
	
	private void postBidRandomly() throws RemoteException, NotEnoughMoneyException, NotFoundStockCodeException, OutOfStockPriceRangeException, NotEnoughStockQuantityException, NotFoundAccountException, TimeOutException, NonPositiveStockQuantityException {
		if (this.random.nextInt(100) < this.POST_BID_PERCENT && !this.modelController.getCurrentTime().isZero()) {
			IStockCollection stocks = this.modelController.getAllStocks();
			IStockCollection ownStocks = this.modelController.getOwnStocks();
			
			if (stocks.size() == 0) {
				return;
			}
			int stockCodeRandom = this.random.nextInt(stocks.size());
			IStock stock = stocks.toArray().get(stockCodeRandom);
			String stockCode = stock.getCode();
			
			Boolean bidTypeRandom = this.random.nextBoolean();
			BidType bidType = bidTypeRandom ? BidType.Buy : BidType.Sell;
			
			int maxStockQuantity;
			if (bidType == BidType.Buy) {
				maxStockQuantity = ((Double)(this.modelController.info.getBalance() / stock.getPrice())).intValue();
			} else {
				maxStockQuantity = ownStocks.getStockQuantity(stockCode);
				if (maxStockQuantity < 0) {
					System.out.println("Error");
				}
			}
			
			int stockQuantity = this.random.nextInt(maxStockQuantity % 20 + 1);
			if (stockQuantity == 0) return;
		
			Double priceRange = stock.getPrice() * Convention.STOCK_PERCENT_RANGE * 2;
			double offerPrice = stock.getFloorPrice() + this.random.nextInt(priceRange.intValue());
			
			this.modelController.postBid(bidType, stockCode, offerPrice, stockQuantity);
		}
	}
	
	private void updateMoney() throws RemoteException, NotFoundAccountException {
		ArrayList<IBankMessage> messages = this.modelController.retrieveBankMessages();
		
		if (!messages.isEmpty()) {
			IBankMessage lastMessage = messages.get(messages.size() - 1);
			this.modelController.info.setBalance(lastMessage.getBalance());
			
			this.viewController.addMessage(this.modelController.getInfo().getName() + ": " 
					+ lastMessage.getMessage() 
					+ " (" + String.format("%.2f", lastMessage.getBalance()) + ")");
		}
	}
	
	private void updateStock() throws RemoteException, NotFoundBidException, BidNotAvailableException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, NotFoundAccountException, TimeOutException {
		ArrayList<IMessage> messages = this.modelController.retrieveStockExchangeMessages();
		for (IMessage message: messages) {
			if (message.getType() == MessageType.UpdateStock) {
				
				IStockCollection stocks = this.modelController.getOwnStocks();
				
			} else if (message.getType() == MessageType.UpdateBid) {
				
				IBidCollection bids = ((IBidMessage)message).getBids();
				if (this.random.nextInt(100) >= this.ACCEPT_BID_PERCENT) {
					continue;
				}
				for (IBid bid: bids.getAllTopBids(5)) {
					if (this.doesAcceptBid(bid)) {
						this.modelController.acceptBid(bid.getId());
					}
				}
				
			} else if (message.getType() == MessageType.MatchBid) {
				
				this.viewController.addMessage(this.modelController.getInfo().getName() + ": " 
						+ message.getMessage());
			}
		}
	}
	
	@Override
	public void run() {
		try {
			this.updateMoney();
			this.updateStock();
			this.postBidRandomly();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotFoundAccountException e) {
			e.printStackTrace();
		} catch (NotFoundBidException e) {
			e.printStackTrace();
		} catch (BidNotAvailableException e) {
			e.printStackTrace();
		} catch (NotEnoughStockQuantityException e) {
			e.printStackTrace();
		} catch (NotEnoughMoneyException e) {
			e.printStackTrace();
		} catch (OfferorNotEnoughMoneyException e) {
			e.printStackTrace();
		} catch (TimeOutException e) {
			e.printStackTrace();
		} catch (NotFoundStockCodeException e) {
			e.printStackTrace();
		} catch (OutOfStockPriceRangeException e) {
			e.printStackTrace();
		} catch (NonPositiveStockQuantityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public ComputerMessageRetrievingTask(ComputerPlayer modelController, ComputerPlayerFrame viewController) {
		this.modelController = modelController;
		this.viewController = viewController;
		this.random = new Random();
	}

}
