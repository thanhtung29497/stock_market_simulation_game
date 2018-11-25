package ui.player;


import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import ui.table.StockBoard;

import javax.swing.JLabel;
import javax.swing.SpringLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTable table_1;

	/**
	 * Create the frame.
	 */
	public PlayerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblTockboard = new JLabel("Stock Board");
		lblTockboard.setFont(lblTockboard.getFont().deriveFont(lblTockboard.getFont().getStyle() | Font.BOLD));
		lblTockboard.setHorizontalAlignment(SwingConstants.CENTER);
		lblTockboard.setBorder(LineBorder.createBlackLineBorder());
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblTockboard, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblTockboard, 230, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblTockboard, -200, SpringLayout.EAST, contentPane);
		contentPane.add(lblTockboard);
		
		JPanel pl_StockBoard = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, pl_StockBoard, 0, SpringLayout.SOUTH, lblTockboard);
		sl_contentPane.putConstraint(SpringLayout.WEST, pl_StockBoard, 0, SpringLayout.WEST, lblTockboard);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pl_StockBoard, -310, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, pl_StockBoard, 0, SpringLayout.EAST, lblTockboard);
		pl_StockBoard.setBorder(new LineBorder(new Color(0, 0, 0)));
		pl_StockBoard.setBackground(Color.WHITE);
		contentPane.add(pl_StockBoard);
		
		JLabel lblBidBoard = new JLabel("Bid Board");
		lblBidBoard.setFont(lblBidBoard.getFont().deriveFont(lblBidBoard.getFont().getStyle() | Font.BOLD));
		lblBidBoard.setHorizontalAlignment(SwingConstants.CENTER);
		lblBidBoard.setBorder(LineBorder.createBlackLineBorder());
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBidBoard, 10, SpringLayout.SOUTH, pl_StockBoard);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBidBoard, 0, SpringLayout.WEST, lblTockboard);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblBidBoard, 0, SpringLayout.EAST, lblTockboard);
		contentPane.add(lblBidBoard);
		
		JPanel Pl_BidBroad = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, Pl_BidBroad, 0, SpringLayout.SOUTH, lblBidBoard);
		sl_contentPane.putConstraint(SpringLayout.WEST, Pl_BidBroad, 0, SpringLayout.WEST, pl_StockBoard);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, Pl_BidBroad, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, Pl_BidBroad, 0, SpringLayout.EAST, pl_StockBoard);
		Pl_BidBroad.setBorder(new LineBorder(new Color(0, 0, 0)));
		Pl_BidBroad.setBackground(Color.WHITE);
		contentPane.add(Pl_BidBroad);
		
		JPanel pl_PlayerInfo = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, pl_PlayerInfo, 0, SpringLayout.NORTH, lblTockboard);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pl_PlayerInfo, 150, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, pl_PlayerInfo, -10, SpringLayout.WEST, pl_StockBoard);
		pl_PlayerInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		pl_PlayerInfo.setBackground(Color.WHITE);
		sl_contentPane.putConstraint(SpringLayout.WEST, pl_PlayerInfo, 10, SpringLayout.WEST, contentPane);
		contentPane.add(pl_PlayerInfo);
		
		JPanel pl_BankMessage = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, pl_BankMessage, 10, SpringLayout.SOUTH, pl_PlayerInfo);
		sl_contentPane.putConstraint(SpringLayout.WEST, pl_BankMessage, 0, SpringLayout.WEST, pl_PlayerInfo);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pl_BankMessage, 200, SpringLayout.SOUTH, pl_PlayerInfo);
		sl_contentPane.putConstraint(SpringLayout.EAST, pl_BankMessage, 0, SpringLayout.EAST, pl_PlayerInfo);
		pl_BankMessage.setBorder(new LineBorder(new Color(0, 0, 0)));
		pl_BankMessage.setBackground(Color.WHITE);
		contentPane.add(pl_BankMessage);
		
		JPanel pl_StockMessage = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, pl_StockMessage, 10, SpringLayout.SOUTH, pl_BankMessage);
		sl_contentPane.putConstraint(SpringLayout.WEST, pl_StockMessage, 0, SpringLayout.WEST, pl_PlayerInfo);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pl_StockMessage, 0, SpringLayout.SOUTH, Pl_BidBroad);
		Pl_BidBroad.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		Pl_BidBroad.add(scrollPane_1, BorderLayout.CENTER);
		
		table_1 = new JTable() ;
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				{"12.2"}
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		) );
		table_1.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(table_1);
		sl_contentPane.putConstraint(SpringLayout.EAST, pl_StockMessage, 0, SpringLayout.EAST, pl_PlayerInfo);
		pl_StockMessage.setBorder(new LineBorder(new Color(0, 0, 0)));
		pl_StockMessage.setBackground(Color.WHITE);
		contentPane.add(pl_StockMessage);
		
		JPanel pl_Rank = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, pl_Rank, 0, SpringLayout.NORTH, lblTockboard);
		sl_contentPane.putConstraint(SpringLayout.WEST, pl_Rank, 10, SpringLayout.EAST, pl_StockBoard);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pl_Rank, 210, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, pl_Rank, -10, SpringLayout.EAST, contentPane);
		pl_Rank.setBorder(new LineBorder(new Color(0, 0, 0)));
		pl_Rank.setBackground(Color.WHITE);
		contentPane.add(pl_Rank);
		
		JPanel pl_Transaction = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.NORTH, pl_Transaction, 10, SpringLayout.SOUTH, pl_Rank);
		sl_contentPane.putConstraint(SpringLayout.WEST, pl_Transaction, 10, SpringLayout.EAST, pl_StockBoard);
		pl_StockBoard.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pl_StockBoard.add(scrollPane, BorderLayout.CENTER);
		
		table = new StockBoard(
					new DefaultTableModel(
						new Object[][] {
							{},
						},
						new String[] {
								"CK","Trần","Sàn","TC","KL2","Giá 2","KL1","Giá 1","Giá","KL","+/-","KL1","Giá 1","KL2","Giá 2"
						}
					) {
						boolean[] columnEditables = new boolean[] {
								false, false, false, false, false
						};
						public boolean isCellEditable(int row, int column) {
							return columnEditables[column];
						}
					});
		for(int i=0;i<12;i++)
			table.getColumnModel().getColumn(i).setResizable(false);
		
		table.setBackground(Color.black);
		scrollPane.setViewportView(table);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pl_Transaction, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, pl_Transaction, -10, SpringLayout.EAST, contentPane);
		pl_Transaction.setBorder(new LineBorder(new Color(0, 0, 0)));
		pl_Transaction.setBackground(Color.WHITE);
		contentPane.add(pl_Transaction);
		
		setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerFrame frame = new PlayerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
