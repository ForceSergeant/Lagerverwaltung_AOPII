package lagerverwaltung;

import java.awt.Color;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * Klasse f�r die UI der Progressbar
 * Abgeleitet von BasicProgressBarUI
 *
 */
public class CustomProgressBarUI extends BasicProgressBarUI{

	/**
	 * Setzt die Farbe der Schrift auf Schwarz, wenn der Balken die Schrift noch nicht �berdeckt
	 * 
	 * @return Color die gesetz wird
	 */
	public Color getSelectionBackground() {
		return Color.BLACK;
	}
	
	/**
	 * Setzt die Farbe der Schrift auf Schwarz, wenn der Balken die Schrift noch nicht �berdeckt
	 * 
	 * @return Color die gesetzt wird
	 */
	public Color getSelectionForeground() {
		return Color.BLACK;
	}
	  
}
