package ui.player;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import java.util.ArrayList;

import common.IBankMessage;
import common.IBidCollection;
import common.IStockCollection;
import common.IMessage;
import common.IPlayerInfo;
import common.IRank;
import common.IRankCollection;
import player.PlayerClient;

public class PlayerFrameController {
	
	private PlayerClient _client=null;
	private LoginFrame _loginFrame=null;
	private PlayerFrame _playerFrame=null;
	
	public void addBankMessages(ArrayList<IBankMessage> messages) {
		if(_playerFrame==null) return;
		messages.forEach(message -> {
			_playerFrame.addBankMessage(message.getMessage() + ": " + message.getBalance());
		});
	}
	
	public void addStockExchangeMessages(ArrayList<IMessage> messages) {
		if(_playerFrame==null)return;
		messages.forEach(message -> {
			_playerFrame.addStockMessage(message.getMessage());
		});
	}
	
	public PlayerFrameController(PlayerClient client) {
		_client = client;
	}
	
	public void start() {
		_loginFrame = new LoginFrame(this);
	}
	
	public void startTrans(IStockCollection stocks, IBidCollection bids, IPlayerInfo playerInfo, IRankCollection ranks, IStockCollection ownStock) {
		_loginFrame.dispose();
		_playerFrame = new PlayerFrame();
		UpdateStocksAndBids(stocks,bids);
		setMoney(playerInfo.getMoney());
	}
	public void UpdateStocksAndBids(IStockCollection stocks,IBidCollection bids) {
		_playerFrame.showStocks(stocks,bids);
		_playerFrame.showBid(bids);
	}
	
	public void updateRank(IRankCollection ranks) {
		_playerFrame.updateRank(ranks);
		
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
