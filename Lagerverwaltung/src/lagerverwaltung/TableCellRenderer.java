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
			setBackground(Color.WHITE);
			setForeground(Color.BLACK);
		}
		else {
			setBackground(Color.LIGHT_GRAY);
			setForeground(Color.BLACK);
		}
		tabelle.setForeground(Color.BLACK);
		tabelle.setRowHeight(25);
		tabelle.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		return this; 
		
	}

}

