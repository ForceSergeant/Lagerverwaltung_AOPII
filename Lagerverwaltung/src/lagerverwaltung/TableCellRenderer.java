package lagerverwaltung;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class TableCellRenderer extends DefaultTableCellRenderer {
	public java.awt.Component getTableCellRendererComponent(
			JTable tabelle, Object value, boolean isSelected, boolean hasFocus,
			int row, int column){
		super.getTableCellRendererComponent(tabelle,value, isSelected, hasFocus, row, column);
		if(row%2 == 1) {
			setBackground(Color.LIGHT_GRAY);
		}
		else {
			setBackground(Color.WHITE);
		}
		tabelle.setRowHeight(25);
		tabelle.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 15));
		return this; 
		
	}

}

