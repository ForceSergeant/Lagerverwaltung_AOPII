package lagerverwaltung;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;


public class EscActionForLagerinhaltAnzeigen extends AbstractAction {
	
	private LagerverwaltungGUI gui;
	private JPanel menupanel;
	private JPanel leftpanel;
	private JPanel rightpanel;
	
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
