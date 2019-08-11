package lagerverwaltung;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Eigenen Klasse für den TableCellRenderer, der die Größe der Zeilen,
 * deren Hintergrundfarbe und die Schrift der Zeilenköpfe festlegt
 * Abgeleitet von der KLasse DefaultTableCellRenderer
 *
 */
public class CustomTableCellRenderer extends DefaultTableCellRenderer {
	
	/**
	 * Ungerade Zeilen bekommen den Hintergrund weiß, gerade Zeilen den Hintergrund Hellgrau
	 * Die Schriftfarbe ist immer Schwarz
	 * 
	 * 
	 * @return Component
	 * 
	 */
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
		tabelle.setRowHeight(25);
		tabelle.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		return this; 
		
	}

}

