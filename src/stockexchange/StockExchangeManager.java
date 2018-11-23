package stockexchange;

import java.rmi.RemoteException;
import java.util.ArrayList;

import common.Convention;
import common.IAccount;
import common.IBankController;
import common.ICompany;
import common.IPlayer;
import common.IStock;
import exception.DuplicateCompanyNameException;
import exception.DuplicateStockCodeException;
import exception.InvalidLoginException;
import exception.NotFoundAccountException;

public class StockExchangeManager {
	
	private IBankController bankController;
	private ArrayList<ICompany> companies;
	private ArrayList<IPlayer> players;
	
	public StockExchangeManager(IBankController bankController) {
		this.bankController = bankController;
		this.companies = new ArrayList<ICompany>();
		this.players = new ArrayList<IPlayer>();
	}
	
	public Boolean findStockCode(String stockCode) {
		for (ICompany company: this.companies) {
			if (company.hasStock() && company.getStockCode().equals(stockCode)) {
				return true;
			}
		}
		return false;
	}
	
	public IStock issueStock(String companyName, String stockCode) throws DuplicateStockCodeException {
		if (this.findStockCode(stockCode)) {
			throw new DuplicateStockCodeException(stockCode);
		}
		Stock stock = new Stock(stockCode, Convention.INITIAL_SHARE_NUMBER, Convention.INITIAL_SHARE_PRICE);
		for (ICompany company: this.companies) {
			if (company.getName().equals(companyName)) {
				company.setStock(stock);
			}
		}
		return stock;
	}
	
	public void registerPlayer(IPlayer player) throws RemoteException, InvalidLoginException, NotFoundAccountException {
		IAccount account = this.bankController.login(player.getAccount().getName(), player.getAccount().getPassword());
		player.setAccount(account);
		this.players.add(player);
	}
	
	public void registerCompany(ICompany registeringCompany) throws DuplicateCompanyNameException {
		String name = registeringCompany.getName();
		for (ICompany company: this.companies) {
			if (company.getName().equals(name)) {
				throw new DuplicateCompanyNameException(name);
			}
		}
		this.companies.add(registeringCompany);
	}
	
}
