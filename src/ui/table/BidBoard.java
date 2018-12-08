package ui.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import ui.table.tame.GroupableTableHeader;

public class BidBoard extends JTable{

	private static final long serialVersionUID = 1L;

	public Component prepareRenderer (TableCellRenderer renderer, int index_row, int index_col){
	    Component comp = super.prepareRenderer(renderer, index_row, index_col);
	    String type =((String)getValueAt(index_row,5));
	    if(type.equals("Matched")) comp.setBackground(Color.green);
	    else if(type.equals("Sell")) comp.setBackground(Color.ORANGE);
	    else comp.setBackground(Color.yellow);
	    return comp;
	}
	
	protected JTableHeader createDefaultTableHeader() {
        return new GroupableTableHeader(columnModel);
    }
	
	public BidBoard(DefaultTableModel defaultTableModel) {
		super(defaultTableModel);
		TableColumnModel cm = getColumnModel();
		cm.setColumnMargin(0);
	}

}
