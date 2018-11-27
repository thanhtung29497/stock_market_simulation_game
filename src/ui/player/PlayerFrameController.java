package ui.player;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import java.util.ArrayList;

import common.BidType;
import common.IAccount;
import common.IBankMessage;
import common.IBidCollection;
import common.IMessage;
import common.IStockCollection;
import player.PlayerClient;

public class PlayerFrameController {
	
	private PlayerClient _client;
	private LoginFrame _loginFrame;
	private PlayerFrame _playerFrame;
	
	public void addBankMessages(ArrayList<IBankMessage> messages) {
		messages.forEach(message -> {
			_playerFrame.addBankMessage(message.getMessage() + ": " + message.getBalance());
		});
	}
	
	public void addStockExchangeMessages(ArrayList<IMessage> messages) {
		messages.forEach(message -> {
			_playerFrame.addStockMessage(message.getMessage());
		});
	}
	
	public void updateStocks(IStockCollection stocks) {
		
	}
	
	public void updateBids(IBidCollection bids) {
		
	}
	
	public PlayerFrameController(PlayerClient client) {
		_client = client;
	}
	
	public void start() {
		_loginFrame = new LoginFrame(this);
	}
	
	public void startTrans(IStockCollection stocks, IBidCollection bids, IAccount account) {
		_loginFrame.dispose();
		_playerFrame = new PlayerFrame();
	}
	
	public void loginFalse(String Msg) {
		JOptionPane.showMessageDialog(_loginFrame, Msg, "Login false",JOptionPane.OK_OPTION );
	}
	
	public void signUpFalse(String Msg) {
		JOptionPane.showMessageDialog(_loginFrame, Msg, "SignUp false",JOptionPane.OK_OPTION );
	}
	
	public void login(String acc,String pass) {
		_client.login(acc, pass);
	}
	
	public void SignUp(String acc,String pass) {
		_client.register(acc, pass);
	}
	
	public void setRank(int rank) {
		_playerFrame.showRank(rank);
	}
	public void setTime(int min,int sec) {
		_playerFrame.showTime(min, sec);
	}
	public void setMoney(double money) {
		_playerFrame.showMoney(money);
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerFrameController frame = new PlayerFrameController(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
