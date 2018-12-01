package ui.player;


import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ui.table.BidBoard;
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
import javax.swing.table.TableModel;

import common.BidStatus;
import common.BidType;
import common.IBid;
import common.IBidCollection;
import common.IRank;
import common.IRankCollection;
import common.IStock;
import common.IStockCollection;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.List;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class PlayerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tablStock;
	private JTable tablBid;
	Vector<String> BankMsgData ;
	Vector<String> StockMsgData ;
	JList list_BankMs;
	JList list_StockMessage;
	JLabel labelTime;
	JLabel labelMoney;
	JLabel lblBalance;
	JLabel lblRank;
	JComboBox<String> cbbCode;
	private JTable table_rank;
	private JTextField textField;
	/**
	 * Create the frame.
	 */
	
	void dataInit() {
		BankMsgData = new Vector<String>();
		StockMsgData = new Vector<String>();
	}
	public PlayerFrame(PlayerFrameController _controller) {
		dataInit();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,900);
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
		
		JLabel lblBankMessage = new JLabel("Bank Message");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblBankMessage, 10, SpringLayout.SOUTH, pl_PlayerInfo);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblBankMessage, 0, SpringLayout.WEST, pl_PlayerInfo);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblBankMessage, 0, SpringLayout.EAST, pl_PlayerInfo);
		contentPane.add(lblBankMessage);
		
		JScrollPane pl_BankMessage = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, pl_BankMessage, 0, SpringLayout.SOUTH, lblBankMessage);
		sl_contentPane.putConstraint(SpringLayout.WEST, pl_BankMessage, 0, SpringLayout.WEST, pl_PlayerInfo);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pl_BankMessage, 200, SpringLayout.SOUTH, pl_PlayerInfo);
		sl_contentPane.putConstraint(SpringLayout.EAST, pl_BankMessage, 0, SpringLayout.EAST, pl_PlayerInfo);
		pl_BankMessage.setBorder(new LineBorder(new Color(0, 0, 0)));
		pl_BankMessage.setBackground(Color.WHITE);
		contentPane.add(pl_BankMessage);
		
		JScrollPane pl_StockMessage = new JScrollPane();
		
		list_BankMs = new JList();
		//list_BankMs.setCellRenderer(new ListCellRenderer());
		pl_BankMessage.setViewportView(list_BankMs);
		
		sl_contentPane.putConstraint(SpringLayout.WEST, pl_StockMessage, 0, SpringLayout.WEST, pl_PlayerInfo);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pl_StockMessage, 0, SpringLayout.SOUTH, Pl_BidBroad);
		Pl_BidBroad.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		Pl_BidBroad.add(scrollPane_1, BorderLayout.CENTER);
	 
		tablBid=new BidBoard(new DefaultTableModel(
			new Object[][] {
				{},
			},
			new String[] {
				"ID","Type","Stock code","Price","Quantity","Status"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		} );
		tablBid.setRowSelectionAllowed(true);
		tablBid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getClickCount()==2) {
					int id = Integer.parseInt((String)tablBid.getValueAt(tablBid.getSelectedRow(),0));
					int conf = JOptionPane.showConfirmDialog(null,
							String.format("Accept Bid id(%d)?",id));
					if(conf == 0) {
						_controller.acceptBid(id);
					}
				}
					System.out.printf("click roif %d",tablBid.getSelectedRow() );
			}
		});
		tablBid.getColumnModel().getColumn(0).setResizable(false);
		tablBid.getColumnModel().getColumn(1).setResizable(false);
		tablBid.getColumnModel().getColumn(2).setResizable(false);
		tablBid.getColumnModel().getColumn(3).setResizable(false);
		tablBid.getColumnModel().getColumn(4).setResizable(false);
		tablBid.getColumnModel().getColumn(5).setResizable(false);
		tablBid.getTableHeader().setReorderingAllowed(false);
		scrollPane_1.setViewportView(tablBid);
		sl_contentPane.putConstraint(SpringLayout.EAST, pl_StockMessage, 0, SpringLayout.EAST, pl_PlayerInfo);
		pl_StockMessage.setBorder(new LineBorder(new Color(0, 0, 0)));
		pl_StockMessage.setBackground(Color.WHITE);
		contentPane.add(pl_StockMessage);
		
		JPanel pl_Rank = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.WEST, pl_Rank, 10, SpringLayout.EAST, pl_StockBoard);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pl_Rank, 210, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, pl_Rank, -10, SpringLayout.EAST, contentPane);
		pl_Rank.setBorder(new LineBorder(new Color(0, 0, 0)));
		pl_Rank.setBackground(Color.WHITE);
		contentPane.add(pl_Rank);
		
		JPanel pl_Transaction = new JPanel();
		sl_contentPane.putConstraint(SpringLayout.WEST, pl_Transaction, 10, SpringLayout.EAST, pl_StockBoard);
		pl_StockBoard.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		pl_StockBoard.add(scrollPane, BorderLayout.CENTER);
		
		tablStock = new StockBoard(
					new DefaultTableModel(
						new Object[][] {
							{},
						},
						new String[] {
								"CK","Trần","Sàn","TC","Sở hữu","KL2","Giá 2","KL1","Giá 1","Giá","KL","KL1","Giá 1","KL2","Giá 2"
						}
					) {
				
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public boolean isCellEditable(int row, int column) {
							return false;
						}
					});
		for(int i=0;i<12;i++)
			tablStock.getColumnModel().getColumn(i).setResizable(false);
		
		tablStock.setBackground(Color.black);
		scrollPane.setViewportView(tablStock);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, pl_Transaction, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, pl_Transaction, -10, SpringLayout.EAST, contentPane);
		pl_Transaction.setBorder(new LineBorder(new Color(0, 0, 0)));
		pl_Transaction.setBackground(Color.WHITE);
		contentPane.add(pl_Transaction);
		SpringLayout sl_pl_Transaction = new SpringLayout();
		pl_Transaction.setLayout(sl_pl_Transaction);
		
		JLabel lblType = new JLabel("Type");
		sl_pl_Transaction.putConstraint(SpringLayout.NORTH, lblType, 20, SpringLayout.NORTH, pl_Transaction);
		sl_pl_Transaction.putConstraint(SpringLayout.WEST, lblType, 0, SpringLayout.WEST, pl_Transaction);
		pl_Transaction.add(lblType);
		
		JComboBox cbbType = new JComboBox();
		sl_pl_Transaction.putConstraint(SpringLayout.EAST, cbbType, -10, SpringLayout.EAST, pl_Transaction);
		cbbType.setModel(new DefaultComboBoxModel(new String[] {"Sell", "Buy"}));
		sl_pl_Transaction.putConstraint(SpringLayout.NORTH, cbbType, 0, SpringLayout.NORTH, lblType);
		sl_pl_Transaction.putConstraint(SpringLayout.WEST, cbbType, 42, SpringLayout.EAST, lblType);
		pl_Transaction.add(cbbType);
		
		cbbCode = new JComboBox<>();
		sl_pl_Transaction.putConstraint(SpringLayout.WEST, cbbCode, 0, SpringLayout.WEST, cbbType);
		sl_pl_Transaction.putConstraint(SpringLayout.EAST, cbbCode, 0, SpringLayout.EAST, cbbType);
		pl_Transaction.add(cbbCode);
		
		JLabel lblCode = new JLabel("Code");
		sl_pl_Transaction.putConstraint(SpringLayout.NORTH, lblCode, 50, SpringLayout.SOUTH, lblType);
		sl_pl_Transaction.putConstraint(SpringLayout.NORTH, cbbCode, 0, SpringLayout.NORTH, lblCode);
		sl_pl_Transaction.putConstraint(SpringLayout.WEST, lblCode, 0, SpringLayout.WEST, lblType);
		pl_Transaction.add(lblCode);
		
		JLabel lblPrice = new JLabel("Price");
		sl_pl_Transaction.putConstraint(SpringLayout.NORTH, lblPrice, 50, SpringLayout.SOUTH, lblCode);
		sl_pl_Transaction.putConstraint(SpringLayout.WEST, lblPrice, 0, SpringLayout.WEST, lblType);
		pl_Transaction.add(lblPrice);
		
		JLabel lblQuantity = new JLabel("Quantity");
		sl_pl_Transaction.putConstraint(SpringLayout.NORTH, lblQuantity, 50, SpringLayout.SOUTH, lblPrice);
		sl_pl_Transaction.putConstraint(SpringLayout.WEST, lblQuantity, 0, SpringLayout.WEST, lblType);
		pl_Transaction.add(lblQuantity);
		
		JSpinner spinner = new JSpinner();
		sl_pl_Transaction.putConstraint(SpringLayout.NORTH, spinner, 0, SpringLayout.NORTH, lblQuantity);
		sl_pl_Transaction.putConstraint(SpringLayout.WEST, spinner, 0, SpringLayout.WEST, cbbType);
		sl_pl_Transaction.putConstraint(SpringLayout.EAST, spinner, 0, SpringLayout.EAST, cbbCode);
		pl_Transaction.add(spinner);
		
		textField = new JTextField();
		sl_pl_Transaction.putConstraint(SpringLayout.NORTH, textField, 0, SpringLayout.NORTH, lblPrice);
		sl_pl_Transaction.putConstraint(SpringLayout.WEST, textField, 0, SpringLayout.WEST, cbbType);
		sl_pl_Transaction.putConstraint(SpringLayout.EAST, textField, 0, SpringLayout.EAST, cbbType);
		pl_Transaction.add(textField);
		textField.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String code = (String)cbbCode.getSelectedItem();
				_controller.postBid((cbbType.getSelectedIndex()==0)?BidType.Sell:BidType.Buy, 
						(String)cbbCode.getSelectedItem(),
						Float.parseFloat(textField.getText()) ,
						(int)spinner.getValue());
			}
		});
		sl_pl_Transaction.putConstraint(SpringLayout.WEST, btnSend, 0, SpringLayout.WEST, textField);
		sl_pl_Transaction.putConstraint(SpringLayout.SOUTH, btnSend, -10, SpringLayout.SOUTH, pl_Transaction);
		pl_Transaction.add(btnSend);
		
		JLabel lblTrans = new JLabel("Transaction");
		sl_contentPane.putConstraint(SpringLayout.NORTH, pl_Transaction, 0, SpringLayout.SOUTH, lblTrans);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblTrans, 10, SpringLayout.SOUTH, pl_Rank);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblTrans, 0, SpringLayout.WEST, pl_Rank);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblTrans, 0, SpringLayout.EAST, pl_Rank);
		contentPane.add(lblTrans);
		
		
	
		JLabel lblSockEnc = new JLabel("Stock Exchange Message");
		sl_contentPane.putConstraint(SpringLayout.NORTH, pl_StockMessage, 10, SpringLayout.SOUTH, lblSockEnc);
		
		list_StockMessage = new JList();
		list_StockMessage.setCellRenderer(new ListCellRenderer());
		pl_StockMessage.setViewportView(list_StockMessage);
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblSockEnc, 10, SpringLayout.SOUTH, pl_BankMessage);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblSockEnc, 0, SpringLayout.WEST, pl_PlayerInfo);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblSockEnc, 0, SpringLayout.EAST, pl_PlayerInfo);
		SpringLayout sl_pl_PlayerInfo = new SpringLayout();
		pl_PlayerInfo.setLayout(sl_pl_PlayerInfo);
		
		JLabel lblMoney = new JLabel("Money:");
		sl_pl_PlayerInfo.putConstraint(SpringLayout.NORTH, lblMoney, 20, SpringLayout.NORTH, pl_PlayerInfo);
		sl_pl_PlayerInfo.putConstraint(SpringLayout.WEST, lblMoney, 10, SpringLayout.WEST, pl_PlayerInfo);
		pl_PlayerInfo.add(lblMoney);
		
		JLabel lblTime = new JLabel("Time");
		sl_pl_PlayerInfo.putConstraint(SpringLayout.WEST, lblTime, 0, SpringLayout.WEST, lblMoney);
		pl_PlayerInfo.add(lblTime);
		
		JLabel lblrank = new JLabel("[Rank]:");
		sl_pl_PlayerInfo.putConstraint(SpringLayout.NORTH, lblrank, 20, SpringLayout.SOUTH, lblTime);
		sl_pl_PlayerInfo.putConstraint(SpringLayout.WEST, lblrank, 0, SpringLayout.WEST, lblMoney);
		pl_PlayerInfo.add(lblrank);
		
		labelMoney = new JLabel("100.0");
		sl_pl_PlayerInfo.putConstraint(SpringLayout.NORTH, labelMoney, 0, SpringLayout.NORTH, lblMoney);
		sl_pl_PlayerInfo.putConstraint(SpringLayout.EAST, labelMoney, -40, SpringLayout.EAST, pl_PlayerInfo);
		pl_PlayerInfo.add(labelMoney);
		
		labelTime = new JLabel("0:00");
		sl_pl_PlayerInfo.putConstraint(SpringLayout.NORTH, labelTime, 0, SpringLayout.NORTH, lblTime);
		sl_pl_PlayerInfo.putConstraint(SpringLayout.EAST, labelTime, 0, SpringLayout.EAST, labelMoney);
		pl_PlayerInfo.add(labelTime);
		
		lblRank = new JLabel("1");
		sl_pl_PlayerInfo.putConstraint(SpringLayout.NORTH, lblRank, 0, SpringLayout.NORTH, lblrank);
		sl_pl_PlayerInfo.putConstraint(SpringLayout.EAST, lblRank, 0, SpringLayout.EAST, labelMoney);
		pl_PlayerInfo.add(lblRank);
		
		JLabel lblNewLabel = new JLabel("Balance");
		sl_pl_PlayerInfo.putConstraint(SpringLayout.NORTH, lblTime, 20, SpringLayout.SOUTH, lblNewLabel);
		sl_pl_PlayerInfo.putConstraint(SpringLayout.NORTH, lblNewLabel, 10, SpringLayout.SOUTH, lblMoney);
		sl_pl_PlayerInfo.putConstraint(SpringLayout.WEST, lblNewLabel, 0, SpringLayout.WEST, lblMoney);
		pl_PlayerInfo.add(lblNewLabel);
		
		JLabel lblName = new JLabel("MasterCOng");
		lblName.setForeground(Color.RED);
		sl_pl_PlayerInfo.putConstraint(SpringLayout.WEST, lblName, 0, SpringLayout.WEST, pl_PlayerInfo);
		pl_PlayerInfo.add(lblName);
		
		lblBalance = new JLabel("00.00");
		sl_pl_PlayerInfo.putConstraint(SpringLayout.NORTH, lblBalance, 10, SpringLayout.SOUTH, labelMoney);
		sl_pl_PlayerInfo.putConstraint(SpringLayout.WEST, lblBalance, 0, SpringLayout.WEST, labelMoney);
		pl_PlayerInfo.add(lblBalance);
		contentPane.add(lblSockEnc);
		
		JLabel lblRankTable = new JLabel("Rank");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblRankTable, 0, SpringLayout.NORTH, lblTockboard);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblRankTable, 0, SpringLayout.EAST, pl_Rank);
		sl_contentPane.putConstraint(SpringLayout.NORTH, pl_Rank, 0, SpringLayout.SOUTH, lblRankTable);
		sl_contentPane.putConstraint(SpringLayout.WEST, lblRankTable, 0, SpringLayout.WEST, pl_Rank);
		pl_Rank.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		pl_Rank.add(scrollPane_2, BorderLayout.CENTER);
		
		table_rank = new JTable();
		table_rank.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
			},
			new String[] {
				"Rank", "Player", "Money"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_rank.getColumnModel().getColumn(0).setResizable(false);
		table_rank.getColumnModel().getColumn(0).setPreferredWidth(41);
		table_rank.getColumnModel().getColumn(1).setResizable(false);
		table_rank.getColumnModel().getColumn(2).setResizable(false);
		scrollPane_2.setViewportView(table_rank);
		contentPane.add(lblRankTable);
		setVisible(true);
	}
	public void addBankMessage(String msg) {
		BankMsgData.addElement(msg);
		list_BankMs.setListData(BankMsgData);
		list_BankMs.ensureIndexIsVisible(BankMsgData.size()-1);
	}
	public void addStockMessage(String msg) {
		StockMsgData.addElement(msg);
		list_StockMessage.setListData(StockMsgData);
		list_StockMessage.ensureIndexIsVisible(StockMsgData.size()-1);
	}
	public void showRank(Integer rank) {
		lblRank.setText(rank.toString());
	};
	public void showMoney(Double money) {
		labelMoney.setText(String.format("%.2f", money));
	};
	public void showBalance(Double money) {
		lblBalance.setText(String.format("%.2f", money));
	};
	public void showTime(Integer min,Integer sec) {
		labelTime.setText(min.toString()+":"+String.format("%02d",sec));
	};
	public void updateRank(IRankCollection ranks) {
		DefaultTableModel md = (DefaultTableModel) table_rank.getModel();
		while(md.getRowCount()>0)
			md.removeRow(0);
		for (IRank rank: ranks.getRankBoard()) {
			md.addRow(new Object[] {rank.getRank(),rank.getPlayerName(),String.format("%.2f",rank.getAmount())});
		}
		md.fireTableDataChanged();
	}
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PlayerFrame frame = new PlayerFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	public String[] stockLine(IStock stock,IBidCollection bids) {
		ArrayList<IBid> sellBid = bids.getTopBids(BidType.Sell, stock.getCode(), 2);
		ArrayList<IBid> buyBid = bids.getTopBids(BidType.Buy, stock.getCode(), 2);
		IBid matchBid = bids.getLatestMatchedBid(stock.getCode());
		String[] output = new String[15];
		output[0]=stock.getCode();
		output[1]=String.format("%.2f",stock.getCapPrice());
		output[2]=String.format("%.2f",stock.getFloorPrice());
		output[3]=String.format("%.2f",stock.getPrice());
		output[4]="0";
		if(buyBid.size()==2) {
			output[6]=String.format("%.2f",buyBid.get(1).getOfferPrice());
			output[5]=String.format("%d",buyBid.get(1).getQuantity());
		}else {
			output[6]=output[5]="";
		}
		if(buyBid.size()>0) {
			output[8]=String.format("%.2f",buyBid.get(0).getOfferPrice());
			output[7]=String.format("%d",buyBid.get(0).getQuantity());
		}else {
			output[8]=output[7]="";
		}
		if(matchBid!=null) {
		output[9]=String.format("%.2f",matchBid.getOfferPrice());
		output[10]=String.format("%d",matchBid.getQuantity());
		}else {
			output[10]=output[9]="";
		}
		if(sellBid.size()>0) {
			output[12]=String.format("%.2f",sellBid.get(0).getOfferPrice());
			output[11]=String.format("%d",sellBid.get(0).getQuantity());
		}else {
			output[12]=output[11]="";
		}
		if(sellBid.size()==2) {
			output[14]=String.format("%.2f",sellBid.get(1).getOfferPrice());
			output[13]=String.format("%d",sellBid.get(1).getQuantity());
		}else {
			output[14]=output[13]="";
		}
		
		return output;
	};
	public void showStocks(IStockCollection stocks,IBidCollection bids) {
		DefaultTableModel model = (DefaultTableModel) tablStock.getModel();
		model.setRowCount(0);
		cbbCode.removeAllItems();
		stocks.toArray().forEach(stock -> {
			model.addRow(stockLine(stock,bids));
			cbbCode.addItem(stock.getCode());
		});
		model.fireTableDataChanged();
	}
	public void showBid(IBidCollection bids) {
		DefaultTableModel model = (DefaultTableModel) tablBid.getModel();
		model.setRowCount(0);
		bids.getAllBids().forEach(bid->{
			model.addRow(new String[] {
					String.valueOf(bid.getId()),
					bid.getType() == BidType.Sell ? "Sell" : "Buy",
					bid.getStock().getCode(),
					String.format("%.2f",bid.getOfferPrice()),
					String.valueOf(bid.getQuantity()),
					bid.getStatus() == BidStatus.Available ? "Available" : "Matched"
			});
		});
		model.fireTableDataChanged();
	}
}
class ListCellRenderer extends DefaultListCellRenderer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus) {
		String str= "<html><body style='width: 140px;border-top:1px solid'>"+value.toString()+"</html>";
		return super.getListCellRendererComponent(list, str, index, isSelected, cellHasFocus);
	}
}
