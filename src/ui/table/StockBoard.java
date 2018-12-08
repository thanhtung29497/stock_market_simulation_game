package ui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import common.BidType;
import common.IBid;
import common.IBidCollection;
import common.IStock;
import common.IStockCollection;
import ui.table.tame.ColumnGroup;
import ui.table.tame.GroupableTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

public class StockBoard extends JTable{
	private Map<String,Integer> stockName;
	private ArrayList<String> _Price;
	public class columnName{
		public static final int CODE_COL=0;
		public static final int CAP_COL=1;
		public static final int FOOL_COL=2;
		public static final int PRICE_COL=3;
		public static final int OWN_COL=4;
		public static final int BQUANTITY2_COL=5;
		public static final int BPRICE2_COL=6;
		public static final int BQUANTITY1_COL=7;
		public static final int BPRICE1_COL=8;
		public static final int MQUANTITY_COL=10;
		public static final int MPRICE_COL=9;
		public static final int SQUANTITY1_COL=11;
		public static final int SPRICE1_COL=12;
		public static final int SQUANTITY2_COL=13;
		public static final int SPRICE2_COL=14;
		};
	
	private Color getColor(int row,int column) {
		if(column == columnName.BQUANTITY1_COL)
			return getColor(row,columnName.BPRICE1_COL);
		else if(column==columnName.BQUANTITY2_COL)
			return getColor(row,columnName.BPRICE2_COL);
		else if(column==columnName.MQUANTITY_COL)
			return getColor(row,columnName.MPRICE_COL);
		else if(column==columnName.SQUANTITY1_COL)
			return getColor(row,columnName.SPRICE1_COL);
		else if(column==columnName.SQUANTITY2_COL)
			return getColor(row,columnName.SPRICE2_COL);
		if(this.getValueAt(row, column).equals(""))
			return Color.white;
		String val =(String) getValueAt(row,column);
		String tb = _Price.get(row);
		if(val.equals(tb)) return Color.YELLOW;
		else if(val.equals((String)getValueAt(row,columnName.FOOL_COL))) return Color.magenta;
		else if(val.equals((String)getValueAt(row,columnName.CAP_COL))) return Color.blue;
		else if(Float.parseFloat(val)<Float.parseFloat(tb)) return Color.RED;
		else return Color.green;
	}
	public Component prepareRenderer (TableCellRenderer renderer, int index_row, int index_col){
	    Component comp = super.prepareRenderer(renderer, index_row, index_col);
	    switch (index_col){
	    case columnName.CODE_COL:
	    	comp.setForeground(Color.red);break;
	    case columnName.FOOL_COL:
	    	comp.setForeground(Color.magenta);break;
	    case columnName.CAP_COL:
	    	comp.setForeground(Color.blue);break;
	    case columnName.PRICE_COL:
	    	comp.setForeground(Color.YELLOW);break;
	    case columnName.OWN_COL:
	    	comp.setForeground(Color.white);break;
	    default:
	    	comp.setForeground(getColor(index_row,index_col));
	    }
	    return comp;
	}
	
	protected JTableHeader createDefaultTableHeader() {
        return new GroupableTableHeader(columnModel);
    }
	public String[] stockLine(IStock stock,IBidCollection bids,int own, double compareToLastPrice) {
		ArrayList<IBid> sellBid = bids.getTopBids(BidType.Sell, stock.getCode(), 2);
		ArrayList<IBid> buyBid = bids.getTopBids(BidType.Buy, stock.getCode(), 2);
		IBid matchBid = bids.getLatestMatchedBid(stock.getCode());
		String[] output = new String[15];
		output[0]=stock.getCode();
		output[1]=String.format("%.2f",stock.getCapPrice());
		output[2]=String.format("%.2f",stock.getFloorPrice());
		_Price.add(String.format("%.2f",stock.getPrice()));
		output[3] = ((compareToLastPrice > 0) ? "^" :((compareToLastPrice < 0) ? "v" : " ")) +  String.format("%.2f",stock.getPrice());
		output[4]=String.valueOf(own);
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
		DefaultTableModel model = (DefaultTableModel) getModel();
		model.setRowCount(0);
		this._Price.clear();
		stocks.toArray().forEach(stock -> {
			model.addRow(stockLine(stock,bids,stocks.getStockQuantity(stock.getCode()),stocks.compareWithLastPrice(stock.getCode())));
		});
		model.fireTableDataChanged();
	}
	public StockBoard() {
		super(new DefaultTableModel(
						new Object[][] {
						},
						new String[] {
								"CK","Trần","Sàn","TC","Sở hữu","KL2","Giá 2","KL1","Giá 1","Giá","KL","KL1","Giá 1","KL2","Giá 2"
						}
					) {
						private static final long serialVersionUID = 1L;

						public boolean isCellEditable(int row, int column) {
							return false;
						}
					});
		TableColumnModel cm = getColumnModel();
		cm.setColumnMargin(0);
	    ColumnGroup g_mua = new ColumnGroup("Dự mua");
	    g_mua.add(cm.getColumn(8));
	    g_mua.add(cm.getColumn(5));
	    g_mua.add(cm.getColumn(6));
	    g_mua.add(cm.getColumn(7));
	    ColumnGroup g_khoplenh = new ColumnGroup("Khớp lệnh");
	    g_khoplenh.add(cm.getColumn(10));
	    g_khoplenh.add(cm.getColumn(9));
	    ColumnGroup g_ban = new ColumnGroup("Dự bán");
	    g_ban.add(cm.getColumn(14));
	    g_ban.add(cm.getColumn(11));
	    g_ban.add(cm.getColumn(12));
	    g_ban.add(cm.getColumn(13));
	    GroupableTableHeader header = (GroupableTableHeader)getTableHeader();
	    header.addColumnGroup(g_mua);
	    header.addColumnGroup(g_khoplenh);
	    header.addColumnGroup(g_ban);
	    this.setRowHeight(30);
		stockName = new HashMap<String,Integer>();
		_Price = new ArrayList<>();
	}
	public void updateStockOwn(String code, Integer num) {
		DefaultTableModel model = (DefaultTableModel) getModel();
		if(stockName.containsKey(code)) {
			model.setValueAt(String.valueOf(num), stockName.get(code), columnName.OWN_COL);
		}
		model.fireTableDataChanged();
	}
}
