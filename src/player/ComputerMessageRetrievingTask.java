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

public class ComputerMessageRetrievingTask extends TimerTask {

	private ComputerPlayer modelController;
	private final int ACCEPT_BID_PERCENT = 10;
	private final int POST_BID_PERCENT = 50;
	private Random random;
	
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
			}
			
			int stockQuantity = this.random.nextInt(maxStockQuantity % 20 + 1);
			if (stockQuantity == 0) return;
			
			Double priceRange = stock.getPrice() * Convention.STOCK_PERCENT_RANGE * 2;
			double offerPrice = stock.getFloorPrice() + this.random.nextInt(priceRange.intValue());
			
			this.modelController.postBid(bidType, stockCode, offerPrice, stockQuantity);
		}
	}
	
	private void updateMoney() throws RemoteException, NotFoundAccountException {
		double money = this.modelController.getTotalAmount();
		ArrayList<IBankMessage> messages = this.modelController.retrieveBankMessages();
		if (!messages.isEmpty()) {
			IBankMessage lastMessage = messages.get(messages.size() - 1);
			this.modelController.info.setBalance(lastMessage.getBalance());
		}
	}
	
	private void updateStock() throws RemoteException, NotFoundBidException, BidNotAvailableException, NotEnoughStockQuantityException, NotEnoughMoneyException, OfferorNotEnoughMoneyException, NotFoundAccountException, TimeOutException {
		ArrayList<IMessage> messages = this.modelController.retrieveStockExchangeMessages();
		for (IMessage message: messages) {
			if (message.getType() == MessageType.UpdateStock) {
				IStockCollection stocks = this.modelController.getOwnStocks();
				System.out.println(this.modelController.getInfo().getId());
				stocks.toArray().forEach(stock -> {
					System.out.println(stock.getCode() + ": " + stock.getPrice() + " " + stocks.getStockQuantity(stock.getCode()));
				});
			} else if (message.getType() == MessageType.UpdateBid) {
				IBidCollection bids = ((IBidMessage)message).getBids();
				for (IBid bid: bids.getAllBids()) {
					if (this.doesAcceptBid(bid)) {
						this.modelController.acceptBid(bid.getId());
					}
				}
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
		}

	}
	
	public ComputerMessageRetrievingTask(ComputerPlayer modelController) {
		this.modelController = modelController;
		this.random = new Random();
	}

}
