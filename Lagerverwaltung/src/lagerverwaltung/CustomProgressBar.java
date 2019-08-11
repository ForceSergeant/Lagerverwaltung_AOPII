package lagerverwaltung;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;

/**
 * Klasse die eine eigene Progressbar darstellt
 * Abgeleitet von JProgressBar und �berschreibt die Methode setValue
 *
 */
public class CustomProgressBar extends JProgressBar {
	
	private int maxvalue;
	
	public CustomProgressBar(int maxlength) {
		this.maxvalue = maxlength;
	}
	
	/**
	 * Setzt in Abh�ngigkeit vom Verh�ltnis des jeweiligen Wertes, welcher gesetzt werden soll
	 * zum maximal M�glichen Wert die Farbe des Balkens
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
