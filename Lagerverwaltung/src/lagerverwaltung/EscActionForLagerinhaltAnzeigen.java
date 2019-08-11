package lagerverwaltung;

import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;

/**
 * Klasse die eine Action darstellt, extra für Lagerinhalt anzeigen
 * Abgeleitet von AbstractAction, überschreibt actionPermored
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
	 * @param gui JFrame, auf welches die unteren Komponenten hinzugefügt werden soll
	 * @param menupanel menupanel, dass auf das JFrame hinzugefügt wird und das Iconmenü enthält
	 * @param leftpanel leftpanel, dass auf das JFrame hinzugefügt wird und die Auslastung des Lagers anzeigt
	 * @param rightpanel rightpanel, dass auf das Jframe hinzugefügt wird und das Bild/die Fachauslastung enthält
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
