package lagerverwaltung;

import javax.swing.table.DefaultTableModel;

/**
 * Klasse die ein neues TableModel darstellt
 * Abgeleitet von der Klasse DefaultTableModel und überschreibt isCellEditable
 *
 */
public class CustomTableModel extends DefaultTableModel {

	
	/**
	 * Konstruktor für die Erzeugung des Objekts der Klasse CustomTableModel
	 * der Konstruktor der Oberklasse -> DefaultTableModel
	 * 
	 * @param data daten der Tabelle
	 * @param headers Zeilenkopf der Tabelle
	 */
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
