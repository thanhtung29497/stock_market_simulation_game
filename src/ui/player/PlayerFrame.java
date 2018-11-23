package ui.player;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import common.IPlayer;

import javax.swing.JLabel;

public class PlayerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public PlayerFrame(IPlayer player) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPlayer = new JLabel("player");
		lblPlayer.setBounds(187, 89, 66, 15);
		contentPane.add(lblPlayer);
		LoginFrame fr=new LoginFrame(player);
		fr.setVisible(true);
	}

}
