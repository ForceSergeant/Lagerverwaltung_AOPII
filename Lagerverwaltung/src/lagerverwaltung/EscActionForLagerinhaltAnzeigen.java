package lagerverwaltung;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;

/**
 * Klasse die eine Action darstellt, extra f�r Lagerinhalt anzeigen
 * Abgeleitet von AbstractAction, �berschreibt actionPermored
 *
 */
public class EscActionForLagerinhaltAnzeigen extends AbstractAction {
	
	private LagerverwaltungGUI gui;
	private JPanel menupanel;
	private JPanel leftpanel;
	private JPanel rightpanel;
	
	/**
	 * Konstruktor zur Erzeugung eines neue Objekt der Klasse EscActionForLagerinhaltAnzeigen
	 * 
	 * @see Konstruktor LagerverwaltungGUI
	 * 
	 * @param gui JFrame, auf welches die unteren Komponenten hinzugef�gt werden soll
	 * @param menupanel menupanel, dass auf das JFrame hinzugef�gt wird und das Iconmen� enth�lt
	 * @param leftpanel leftpanel, dass auf das JFrame hinzugef�gt wird und die Auslastung des Lagers anzeigt
	 * @param rightpanel rightpanel, dass auf das Jframe hinzugef�gt wird und das Bild/die Fachauslastung enth�lt
	 */
	public EscActionForLagerinhaltAnzeigen(LagerverwaltungGUI gui, JPanel menupanel, JPanel leftpanel, JPanel rightpanel) {
		this.gui = gui;
		this.menupanel = menupanel;
		this.leftpanel = leftpanel;
		this.rightpanel = rightpanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Component c : gui.getContentPane().getComponents()) {
			gui.remove(c);
		}
		gui.add(menupanel);
		gui.add(leftpanel);
		gui.add(rightpanel);
		gui.repaint();
	}


}
