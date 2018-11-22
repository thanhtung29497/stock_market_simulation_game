package player;

import java.rmi.RemoteException;

import common.Convention;
import common.IAccountController;
import common.IBid;
import common.IPlayerStockController;
import common.Utility;
import exception.DuplicateLoginNameException;
import exception.ExceedMaximumAccountException;
import exception.InvalidLoginException;
import exception.NotFoundAccountException;

public class ComputerPlayer extends Player {
	
	private static final long serialVersionUID = 1L;

	public ComputerPlayer(IAccountController accountController, IPlayerStockController stockController) throws ExceedMaximumAccountException, DuplicateLoginNameException, InvalidLoginException, NotFoundAccountException, RemoteException {
		this.accountController = accountController;
		this.stockController = stockController;
		String name = Utility.generateComputerName();
		String password = Convention.COMPUTER_PLAYER_PASSWORD;
		
		this.registerBank(name, password);
		this.loginBank(name, password);
		this.registerStockExchange();
	}
	
	@Override
	public void processBid(IBid bid) {
		// TODO Auto-generated method stub
		
	}

}
