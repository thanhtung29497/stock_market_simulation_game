package player;

import java.rmi.RemoteException;
import java.time.Duration;
import java.util.TimerTask;

import common.Convention;
import ui.player.PlayerFrameController;

public class TimeUpdatingTask extends TimerTask {
	
	private PlayerFrameController viewController;
	private PlayerController modelController;
	private Duration currentTime;
	private int countDownNumber = Convention.RETRIEVE_TIME_NUMBER_PERIOD;

	@Override
	public void run() {
		try {
			this.countDownNumber--;
			if (this.countDownNumber == 0) {
				this.currentTime = this.modelController.getCurrentTime();
				this.countDownNumber = Convention.RETRIEVE_TIME_NUMBER_PERIOD;
			} else if (this.currentTime.getSeconds() > 0){
				this.currentTime = this.currentTime.minusSeconds(1);
			}
			this.viewController.setTime((int)(this.currentTime.toMinutes() % 60), (int)(this.currentTime.getSeconds() % 60));
		} catch (RemoteException e) {
			this.viewController.ShowMessage("Server error", "Failed to connect to server");
		}
	}
	
	public TimeUpdatingTask(PlayerFrameController viewController, PlayerController modelController) {
		this.viewController = viewController;
		this.modelController = modelController;
		try {
			this.currentTime = this.modelController.getCurrentTime();
			this.viewController.setTime((int)(this.currentTime.toMinutes() % 60), (int)(this.currentTime.getSeconds() % 60));
		} catch (RemoteException e) {
			this.viewController.ShowMessage("Server error", "Failed to connect to server");
		} catch (Exception e) {
			System.out.println("Unexpected Error");
			e.printStackTrace();
		}
	}

}
