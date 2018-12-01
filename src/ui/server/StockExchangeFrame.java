package ui.server;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import stockexchange.StockExchangeManager;
import ui.table.StockBoard;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import common.BidType;
import common.IBid;
import common.IBidCollection;
import common.IRank;
import common.IRankCollection;
import common.IStock;
import common.IStockCollection;

import javax.swing.border.LineBorder;
import java.awt.Color;

public class StockExchangeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private StockExchangeManager manager;
	private JTextField tfInitialStockPrice;
	private JTextField tfDuration;
	private JTextField tfSessionPeried;
	private JTextField tfImitialBalance;
	private JTable tableRank;
	private StockBoard table;
	private JLabel lblTime;
	/**
	 * Create the frame.
	 */
	public StockExchangeFrame(StockExchangeManager stockExchangeManager) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel, 330, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel, 200, SpringLayout.WEST, contentPane);
		contentPane.add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JButton btnStart = new JButton("Start");
		sl_panel.putConstraint(SpringLayout.SOUTH, btnStart, -5, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnStart, -5, SpringLayout.EAST, panel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnStart, -5, SpringLayout.SOUTH, contentPane);
		panel.add(btnStart);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					stockExchangeManager.start();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(contentPane, "Failed to connect to server", "Error", ERROR);
				}
				btnStop.setEnabled(false);
				btnStart.setEnabled(true);
			}
		});
		btnStop.setEnabled(false);
		sl_panel.putConstraint(SpringLayout.SOUTH, btnStop, -5, SpringLayout.SOUTH, panel);
		sl_panel.putConstraint(SpringLayout.EAST, btnStop, -5, SpringLayout.WEST, btnStart);
		panel.add(btnStop);
		
		JLabel lblDuration = new JLabel("Duration");
		sl_panel.putConstraint(SpringLayout.NORTH, lblDuration, 30, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblDuration, 10, SpringLayout.WEST, panel);
		panel.add(lblDuration);
		
		JLabel lblSesionPeried = new JLabel("Sesion peried");
		sl_panel.putConstraint(SpringLayout.WEST, lblSesionPeried, 0, SpringLayout.WEST, lblDuration);
		panel.add(lblSesionPeried);
		
		JLabel lblImitialBalance = new JLabel("Imitial Balance");
		sl_panel.putConstraint(SpringLayout.WEST, lblImitialBalance, 0, SpringLayout.WEST, lblDuration);
		panel.add(lblImitialBalance);
		
		JLabel lblInitialStockPrice = new JLabel("Initial Stock Price");
		sl_panel.putConstraint(SpringLayout.WEST, lblInitialStockPrice, 0, SpringLayout.WEST, lblDuration);
		panel.add(lblInitialStockPrice);
		
		tfInitialStockPrice = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, tfInitialStockPrice, 5, SpringLayout.SOUTH, lblInitialStockPrice);
		panel.add(tfInitialStockPrice);
		tfInitialStockPrice.setColumns(10);
		
		tfDuration = new JTextField();
		sl_panel.putConstraint(SpringLayout.WEST, tfInitialStockPrice, 0, SpringLayout.WEST, tfDuration);
		sl_panel.putConstraint(SpringLayout.NORTH, lblSesionPeried, 15, SpringLayout.SOUTH, tfDuration);
		sl_panel.putConstraint(SpringLayout.NORTH, tfDuration, 5, SpringLayout.SOUTH, lblDuration);
		sl_panel.putConstraint(SpringLayout.WEST, tfDuration, 30, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, tfDuration, -5, SpringLayout.EAST, panel);
		panel.add(tfDuration);
		tfDuration.setColumns(10);
		
		tfSessionPeried = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, lblImitialBalance, 15, SpringLayout.SOUTH, tfSessionPeried);
		sl_panel.putConstraint(SpringLayout.NORTH, tfSessionPeried, 5, SpringLayout.SOUTH, lblSesionPeried);
		sl_panel.putConstraint(SpringLayout.WEST, tfSessionPeried, 0, SpringLayout.WEST, tfDuration);
		sl_panel.putConstraint(SpringLayout.EAST, tfSessionPeried, 0, SpringLayout.EAST, tfDuration);
		panel.add(tfSessionPeried);
		tfSessionPeried.setColumns(10);
		
		tfImitialBalance = new JTextField();
		sl_panel.putConstraint(SpringLayout.EAST, tfInitialStockPrice, 0, SpringLayout.EAST, tfImitialBalance);
		sl_panel.putConstraint(SpringLayout.NORTH, lblInitialStockPrice, 15, SpringLayout.SOUTH, tfImitialBalance);
		sl_panel.putConstraint(SpringLayout.NORTH, tfImitialBalance, 5, SpringLayout.SOUTH, lblImitialBalance);
		sl_panel.putConstraint(SpringLayout.WEST, tfImitialBalance, 0, SpringLayout.WEST, tfSessionPeried);
		sl_panel.putConstraint(SpringLayout.EAST, tfImitialBalance, 0, SpringLayout.EAST, tfSessionPeried);
		panel.add(tfImitialBalance);
		tfImitialBalance.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_1, 5, SpringLayout.SOUTH, panel);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, panel);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_1, -5, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_1, 0, SpringLayout.EAST, panel);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, panel_2, 0, SpringLayout.NORTH, panel);
		
		lblTime = new JLabel("00:00");
		sl_panel.putConstraint(SpringLayout.NORTH, lblTime, 15, SpringLayout.SOUTH, tfInitialStockPrice);
		sl_panel.putConstraint(SpringLayout.WEST, lblTime, 30, SpringLayout.WEST, panel);
		sl_panel.putConstraint(SpringLayout.EAST, lblTime, -30, SpringLayout.EAST, panel);
		panel.add(lblTime);
		sl_contentPane.putConstraint(SpringLayout.WEST, panel_2, 5, SpringLayout.EAST, panel_1);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, panel_2, 0, SpringLayout.SOUTH, panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		tableRank = new JTable();
		tableRank.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Rank","Name","Money"
			}
		));
		scrollPane.setViewportView(tableRank);
		sl_contentPane.putConstraint(SpringLayout.EAST, panel_2, -5, SpringLayout.EAST, contentPane);
		contentPane.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		
		table = new StockBoard();
		table.getColumnModel().getColumn(4).setMinWidth(0);
		table.getColumnModel().getColumn(4).setWidth(0);
		table.getColumnModel().getColumn(4).setMaxWidth(0);
		for(int i=0;i<15;i++)
			table.getColumnModel().getColumn(i).setResizable(false);
		
		table.setBackground(Color.black);
		scrollPane_1.setViewportView(table);
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					stockExchangeManager.start();
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(contentPane, "Failed to connect to server", "Error", ERROR);
				}
				btnStop.setEnabled(true);
				btnStart.setEnabled(false);
			}
		});
	}
	
	public void showStocks(IStockCollection stocks,IBidCollection bids) {
		table.showStocks(stocks,bids);
	}
	public void updateRank(IRankCollection ranks) {
		DefaultTableModel md = (DefaultTableModel) tableRank.getModel();
		while(md.getRowCount()>0)
			md.removeRow(0);
		for (IRank rank: ranks.getRankBoard()) {
			md.addRow(new Object[] {rank.getRank(),rank.getPlayerName(),String.format("%.2f",rank.getAmount())});
		}
		md.fireTableDataChanged();
	}
	public void showTime(int min,int sec) {
		lblTime.setText(String.format("%d:%02d",min,sec));
	}
}
