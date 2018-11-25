package ui.player;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import java.util.ArrayList;

import common.IAccount;
import common.IBankMessage;
import common.IBidCollection;
import common.IStockCollection;
import common.Message;
import player.PlayerClient;

public class PlayerFrameController {
	
	private PlayerClient _client;
	private LoginFrame _loginFrame;
	private PlayerFrame _playerFrame;
	
	public void addBankMessages(ArrayList<IBankMessage> messages) {
		messages.forEach(message -> {
			System.out.println(message.getMessage() + ": " + message.getBalance());
		});
	}
	
	public void addStockExchangeMessages(ArrayList<Message> messages) {
		messages.forEach(message -> {
			System.out.println(message.getMessage());
		});
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
