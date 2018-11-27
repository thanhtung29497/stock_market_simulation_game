package player;

import common.IAccountRemote;
import common.IBid;
import common.IPlayerStockRemote;

public class HumanPlayer extends PlayerController {
	
	private static final long serialVersionUID = 1L;



	public HumanPlayer(IAccountRemote accountController, IPlayerStockRemote stockController) {
		this.accountRemote = accountController; 
		this.stockRemote = stockController;
	}
	
}
