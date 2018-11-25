package ui.player;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import java.awt.EventQueue;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtAcount;
	private JPasswordField pwdPass;
	private PlayerFrameController _controller;
	public LoginFrame(PlayerFrameController controller) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblAcc = new JLabel("Acc");
		sl_contentPane.putConstraint(SpringLayout.EAST, lblAcc, 150, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblAcc, 50, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblAcc, 70, SpringLayout.NORTH, contentPane);
		contentPane.add(lblAcc);
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String acc = txtAcount.getText();
				String pass = new String(pwdPass.getPassword());

				_controller.SignUp(acc, pass);
			}
		});
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnSignUp, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnSignUp, -10, SpringLayout.EAST, contentPane);
		contentPane.add(btnSignUp);

		JButton btnLogin = new JButton("login");
		sl_contentPane.putConstraint(SpringLayout.NORTH, btnLogin, 0, SpringLayout.NORTH, btnSignUp);
		sl_contentPane.putConstraint(SpringLayout.EAST, btnLogin, -10, SpringLayout.WEST, btnSignUp);
		contentPane.add(btnLogin);
		
		txtAcount = new JTextField();
		sl_contentPane.putConstraint(SpringLayout.NORTH, txtAcount, 66, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, txtAcount, 150, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, txtAcount, -50, SpringLayout.EAST, contentPane);
		txtAcount.setText("acount");
		contentPane.add(txtAcount);
		txtAcount.setColumns(10);
		
		JLabel lblPass = new JLabel("Pass");
		sl_contentPane.putConstraint(SpringLayout.WEST, lblPass, 50, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, lblPass, -70, SpringLayout.NORTH, btnSignUp);
		contentPane.add(lblPass);
		
		pwdPass = new JPasswordField();
		sl_contentPane.putConstraint(SpringLayout.EAST, lblPass, 0, SpringLayout.WEST, pwdPass);
		pwdPass.setText("pass");
		sl_contentPane.putConstraint(SpringLayout.WEST, pwdPass, 0, SpringLayout.WEST, txtAcount);
		sl_contentPane.putConstraint(SpringLayout.EAST, pwdPass, 0, SpringLayout.EAST, txtAcount);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pwdPass, 0, SpringLayout.SOUTH, lblPass);
		contentPane.add(pwdPass);
		
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String acc = txtAcount.getText();
				String pass = new String(pwdPass.getPassword());

				_controller.login(acc,pass);

			}
		});
		
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String acc = txtAcount.getText();
				String pass = new String(pwdPass.getPassword());

				_controller.SignUp(acc, pass);

			}
		});
		
		setVisible(true);
		_controller = controller;
	}
	
}
