package bank;

import java.util.TimerTask;

public class AccountAccumulationTask extends TimerTask {

	private BankManager bankManager;
	
	@Override
	public void run() {
		this.bankManager.accumulateAccounts();
		System.out.println("");
	}
	
	public AccountAccumulationTask(BankManager bankManager) {
		this.bankManager = bankManager;
	}

}
