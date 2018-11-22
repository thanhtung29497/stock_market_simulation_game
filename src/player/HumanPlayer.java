package player;

import common.IAccountController;
import common.IBid;
import common.IPlayerStockController;

public class HumanPlayer extends Player {
	
	private static final long serialVersionUID = 1L;



	public HumanPlayer(IAccountController accountController, IPlayerStockController stockController) {
		this.accountController = accountController; 
		this.stockController = stockController;
	}
	
	
	
	@Override
	public void processBid(IBid bid) {
		// TODO Auto-generated method stub
		
	}
	
}
