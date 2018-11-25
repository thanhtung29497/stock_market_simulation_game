package stockexchange;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import common.Convention;
import common.IAccount;
import common.IBankController;
import common.IBidCollection;
import common.IStock;
import common.IStockCollection;
import common.Message;
import common.MessageType;
import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;
import exception.InvalidLoginException;
import exception.NotFoundAccountException;

public class StockExchangeManager {
	
	private IBankController bankController;
	private IStockCollection stocks;
	private IBidCollection bids;
	private ArrayList<IAccount> accounts;
	private HashMap<String, ArrayList<Message>> messages;
	private static final String PLAYER_PREFIX = "Player";
	private static final String COMPANY_PREFIX = "Company";

	public StockExchangeManager(IBankController bankController) {
		this.bankController = bankController;
		this.stocks = new StockCollection();
		this.accounts = new ArrayList<IAccount>();
		this.messages = new HashMap<>();
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new StockPriceAdjustmentTask(this), 0, Convention.STOCK_PRICE_ADJUSTMENT_PERIOD);
	}
	
	public Boolean findStockCode(String stockCode) {
		return this.stocks.hasStockCode(stockCode);
	}
	
	public Boolean findCompanyName(String companyName) {
		for (IStock stock: this.stocks.toArray()) {
			if (stock.getCompanyName().equals(companyName)) {
				return true;
			}
		}
		return false;
	}
	
	public void addMessageByKey(String key, Message message) {
		ArrayList<Message> messages = this.messages.get(key);
		messages.add(message);
		this.messages.put(key, messages);
	}
	
	public IStock issueStock(String companyName, String stockCode) throws DuplicateStockCodeException, DuplicateCompanyNameException {
		if (this.findStockCode(stockCode)) {
			throw new DuplicateStockCodeException(stockCode);
		}
		if (this.findCompanyName(companyName)) {
			throw new DuplicateCompanyNameException(companyName);
		}
		Stock stock = new Stock(stockCode, Convention.INITIAL_SHARE_PRICE, companyName);
		this.stocks.addStock(stock, Convention.INITIAL_SHARE_NUMBER);
		
		for (String key: this.messages.keySet()) {
			if (key.startsWith(StockExchangeManager.PLAYER_PREFIX)) {
				this.addMessageByKey(key, new StockMessage(MessageType.IssueStock,
						"New stock was issued: " + stock.getCode(), 
						new StockCollection(stock, Convention.INITIAL_SHARE_NUMBER)));
			}
		}
		
		this.messages.put(StockExchangeManager.COMPANY_PREFIX + companyName, 
				new ArrayList<>());
		
		return stock;
	}
	
	public void registerPlayer(String accountName, String password) throws RemoteException, InvalidLoginException, NotFoundAccountException {
		IAccount account = this.bankController.login(accountName, password);
		this.accounts.add(account);
		this.messages.put(StockExchangeManager.PLAYER_PREFIX + account.getName(), new ArrayList<>());
	}
	
	public ArrayList<Message> retrieveMessages(String key, Boolean isPlayer) {
		if (isPlayer) {
			key = StockExchangeManager.PLAYER_PREFIX + key;
		} else {
			key = StockExchangeManager.COMPANY_PREFIX + key;
		}
		
		ArrayList<Message> messages = this.messages.get(key);
		this.messages.put(key, new ArrayList<>());
		return messages;
	}
	
	public IStockCollection getStocks() {
		return this.stocks;
	}
	
	public void adjustStockPrice(HashMap<String, Double> stockPrices) {
		this.stocks.updateStockPrice(stockPrices);
		for (String key: this.messages.keySet()) {
			this.addMessageByKey(key, new StockMessage(MessageType.AdjustStockPrice, 
					"All stock prices were adjusted periodically!", this.getStocks()));
		}
	}
	
	public IBidCollection getBids() {
		return this.bids;
	}
	
}
