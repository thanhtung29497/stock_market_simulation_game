package ui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
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

import ui.table.tame.ColumnGroup;
import ui.table.tame.GroupableTableHeader;


public class StockBoard extends JTable{
	
//	protected class HeaderRenderer extends JLabel implements TableCellRenderer {
//		 
//	    public HeaderRenderer() {
//	    	
//	        setFont(new Font("Consolas", Font.BOLD, 14));
//	        setForeground(Color.BLUE);
//	        setBorder(BorderFactory.createEtchedBorder());
//	    }
//	     
//	    @Override
//	    public Component getTableCellRendererComponent(JTable table, Object value,
//	            boolean isSelected, boolean hasFocus, int row, int column) {
//	        setText(value.toString() + "cong");
//	        return this;
//	    }
//	 
//	}
	
	public Component prepareRenderer (TableCellRenderer renderer, int index_row, int index_col){
	    Component comp = super.prepareRenderer(renderer, index_row, index_col);
	    switch (index_col){
	    case 0:
	    	comp.setForeground(Color.red);break;
	    case 1:
	    	comp.setForeground(Color.yellow);break;
	    }
	    return comp;
	}
	
	protected JTableHeader createDefaultTableHeader() {
        return new GroupableTableHeader(columnModel);
    }
	
	public StockBoard(DefaultTableModel defaultTableModel) {
		super(defaultTableModel);
		TableColumnModel cm = getColumnModel();
		cm.setColumnMargin(0);
	    ColumnGroup g_mua = new ColumnGroup("Dự mua");
	    g_mua.add(cm.getColumn(4));
	    g_mua.add(cm.getColumn(5));
	    g_mua.add(cm.getColumn(6));
	    g_mua.add(cm.getColumn(7));
	    ColumnGroup g_khoplenh = new ColumnGroup("Khớp lệnh");
	    g_khoplenh.add(cm.getColumn(8));
	    g_khoplenh.add(cm.getColumn(9));
	    g_khoplenh.add(cm.getColumn(10));
	    ColumnGroup g_ban = new ColumnGroup("Dự bán");
	    g_ban.add(cm.getColumn(11));
	    g_ban.add(cm.getColumn(12));
	    g_ban.add(cm.getColumn(13));
	    g_ban.add(cm.getColumn(14));
	    GroupableTableHeader header = (GroupableTableHeader)getTableHeader();
	    header.addColumnGroup(g_mua);
	    header.addColumnGroup(g_khoplenh);
	    header.addColumnGroup(g_ban);
	}
}
