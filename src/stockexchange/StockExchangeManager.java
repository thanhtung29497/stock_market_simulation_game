package stockexchange;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import common.Convention;
import common.IAccount;
import common.IBankController;
import common.IStock;
import common.Message;
import common.MessageType;
import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;
import exception.InvalidLoginException;
import exception.NotFoundAccountException;

public class StockExchangeManager {
	
	private IBankController bankController;
	private ArrayList<IStock> stocks;
	private ArrayList<IAccount> accounts;
	private HashMap<String, ArrayList<Message>> messages;
	private static final String PLAYER_PREFIX = "Player";
	private static final String COMPANY_PREFIX = "Company";

	public StockExchangeManager(IBankController bankController) {
		this.bankController = bankController;
		this.stocks = new ArrayList<IStock>();
		this.accounts = new ArrayList<IAccount>();
		this.messages = new HashMap<>();
	}
	
	public Boolean findStockCode(String stockCode) {
		for (IStock stock: this.stocks) {
			if (stock.getCode().equals(stockCode)) {
				return true;
			}
		}
		return false;
	}
	
	public Boolean findCompanyName(String companyName) {
		for (IStock stock: this.stocks) {
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
		Stock stock = new Stock(stockCode, Convention.INITIAL_SHARE_NUMBER, Convention.INITIAL_SHARE_PRICE, companyName);
		this.stocks.add(stock);
		
		for (String key: this.messages.keySet()) {
			if (key.startsWith(StockExchangeManager.PLAYER_PREFIX)) {
				this.addMessageByKey(key, new StockMessage(MessageType.IssueStock,
						"New stock was issued: " + stock.getCode(), 
						new ArrayList<>(Arrays.asList(stock))));
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
	
}
