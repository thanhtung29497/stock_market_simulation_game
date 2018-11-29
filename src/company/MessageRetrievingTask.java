package company;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

import common.BidStatus;
import common.BidType;
import common.IBid;
import common.IBidMessage;
import common.ICompanyController;
import common.IMessage;
import common.MessageType;
import exception.BidNotAvailableException;
import exception.NotEnoughMoneyException;
import exception.NotEnoughStockQuantityException;
import exception.NotFoundAccountException;
import exception.NotFoundBidException;
import exception.OfferorNotEnoughMoneyException;
import exception.TimeOutException;

public class MessageRetrievingTask extends TimerTask {

	private ICompanyController controller;
	private final int ACCEPT_BID_PERCENT = 50;
	
	private Boolean doesAcceptBid(IBid bid) {
		if (bid.getType() != BidType.Buy || bid.getStatus() != BidStatus.Available
				|| bid.getQuantity() > this.controller.getStockQuantity()) {
			return false;
		}
		
		Random random = new Random();
		int number = random.nextInt(100) + 1;
		return number <= this.ACCEPT_BID_PERCENT;
	}
	
	@Override
	public void run() {
		try {
			ArrayList<IMessage> messages = this.controller.retrieveMessage();
			for (IMessage message: messages) {
				if (message.getType() == MessageType.UpdateBid) {
					ArrayList<IBid> bids = ((IBidMessage)message).getBids().getBidsByStockCode(this.controller.getStockCode());
					for (IBid bid: bids) {
						if (this.doesAcceptBid(bid)) {
							this.controller.acceptBid(bid.getId());
						}
					}
				} else if (message.getType() == MessageType.AdjustStockPrice) {
					
				}
			}
		} catch (RemoteException e) {
			System.out.println("Server failed when retrieving messages");
		} catch (NotFoundBidException e) {
			System.out.println("Wrong bid id");
		} catch (BidNotAvailableException e) {
			System.out.println("Bid is not available");
		} catch (NotFoundAccountException e) {
			System.out.println("Something wrong with account");
		} catch (NotEnoughStockQuantityException e) {
			System.out.println("Not enough stock");
		} catch (NotEnoughMoneyException e) {
			System.out.println("Not enough money");
		} catch (OfferorNotEnoughMoneyException e) {
			System.out.println("Offeror " + e.getOfferorName() + " not enough money");
		} catch (TimeOutException e) {
			System.out.println("Time out!");
		}
	}
	
	public MessageRetrievingTask(ICompanyController controller) {
		this.controller = controller;
	}

}