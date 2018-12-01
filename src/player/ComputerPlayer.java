package player;

import java.rmi.RemoteException;

import common.Convention;
import common.IAccount;
import common.IAccountRemote;
import common.IPlayerStockRemote;
import common.Utility;
import exception.DuplicateLoginNameException;
import exception.ExceedMaximumAccountException;
import exception.InvalidLoginException;
import exception.NotFoundAccountException;

public class ComputerPlayer extends PlayerController {
	
	private static final long serialVersionUID = 1L;

	public ComputerPlayer(IAccountRemote accountController, IPlayerStockRemote stockController) {
		this.accountRemote = accountController;
		this.stockRemote = stockController;
	}
	
	public void registerBank() throws ExceedMaximumAccountException, DuplicateLoginNameException, RemoteException {
		IAccount account = this.accountRemote.registerComputer();
		this.info.init(account);
	}

}
