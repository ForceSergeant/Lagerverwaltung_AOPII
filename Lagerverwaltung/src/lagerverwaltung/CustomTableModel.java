package lagerverwaltung;

import javax.swing.table.DefaultTableModel;

/**
 * Klasse die ein neues TableModel darstellt
 * Abgeleitet von der Klasse DefaultTableModel und �berschreibt isCellEditable
 *
 */
public class CustomTableModel extends DefaultTableModel {

	
	public CustomTableModel(Object[][] data, Object[] headers) {
		super(data, headers);
	}
	
	/**
	 * Verhindert das Editieren einer Zelle in einer Tabelle
	 * 
	 * @param x die jeweilige Spalte
	 * @param y die jeweilige Zeile
	 * 
	 * @return boolean
	 */
	@Override
	public boolean isCellEditable(int x, int y) {
		return false;
	}
	
}
