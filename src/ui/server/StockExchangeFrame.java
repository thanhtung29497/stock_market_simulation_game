package ui.server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import stockexchange.StockExchangeManager;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class StockExchangeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private StockExchangeManager manager;

	/**
	 * Create the frame.
	 */
	public StockExchangeFrame(StockExchangeManager stockExchangeManager) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JButton btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					stockExchangeManager.start();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(contentPane, "Failed to connect to server", "Error", ERROR);
				}
			}
		});
		contentPane.add(btnStart, BorderLayout.CENTER);
	}

}
