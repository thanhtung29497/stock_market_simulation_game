package stockexchange;

import java.rmi.RemoteException;
import java.util.TimerTask;

public class GameEndingTask extends TimerTask {

	private StockExchangeManager manager;
	
	@Override
	public void run() {
		try {
			this.manager.end();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public GameEndingTask(StockExchangeManager manager) {
		this.manager = manager;
	}

}
