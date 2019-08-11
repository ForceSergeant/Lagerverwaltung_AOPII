package lagerverwaltung;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;

/**
 * Klasse die eine eigene Progressbar darstellt
 * Abgeleitet von JProgressBar und überschreibt die Methode setValue
 *
 */
public class CustomProgressBar extends JProgressBar {
	
	private int maxvalue;
	
	/**
	 * Konstruktor zur Erzeugung eines Objektes der Klasse CustomProgressBar
	 * 
	 * @param maxvalue die maximale Anzahl der JProgressBar -> 100%
	 */
	public CustomProgressBar(int maxvalue) {
		this.maxvalue = maxvalue;
	}
	
	/**
	 * Setzt in Abhängigkeit vom Verhältnis des jeweiligen Wertes, welcher gesetzt werden soll
	 * zum maximal Möglichen Wert die Farbe des Balkens
	 * 
	 */
	@Override
	public void setValue(int n)
	{
		super.setValue(n);
		if (n < maxvalue*0.33)
		{
			this.setForeground(Color.GREEN);
		}
		else if (n < maxvalue*0.66)
		{
			this.setForeground(Color.YELLOW);
		}
		else if (n < maxvalue*0.9) {
			this.setForeground(Color.ORANGE);
		}
		else {
			this.setForeground(Color.RED);
		}
		this.setBackground(Color.gray);
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));		
	}
}
