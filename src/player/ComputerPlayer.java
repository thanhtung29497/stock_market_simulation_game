package player;

import java.rmi.RemoteException;

import common.Convention;
import common.IAccountRemote;
import common.IBid;
import common.IPlayerStockRemote;
import common.Utility;
import exception.DuplicateLoginNameException;
import exception.ExceedMaximumAccountException;
import exception.InvalidLoginException;
import exception.NotFoundAccountException;

public class ComputerPlayer extends PlayerController {
	
	private static final long serialVersionUID = 1L;

	public ComputerPlayer(IAccountRemote accountController, IPlayerStockRemote stockController) throws ExceedMaximumAccountException, DuplicateLoginNameException, InvalidLoginException, NotFoundAccountException, RemoteException {
		this.accountRemote = accountController;
		this.stockRemote = stockController;
		String name = Utility.generateComputerName();
		String password = Convention.COMPUTER_PLAYER_PASSWORD;
		
		this.registerBank(name, password);
		this.loginBank();
		this.registerStockExchange();
	}
	
	@Override
	public void processBid(IBid bid) {
		// TODO Auto-generated method stub
		
	}

}
