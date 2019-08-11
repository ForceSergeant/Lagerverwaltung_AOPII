package lagerverwaltung;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 * Klasse die eine Action darstellt
 * Abgeleitet von AbstractAction, �berschreibt actionPerformed
 *
 */
public class EscAction extends AbstractAction {

	private JDialog dialog = new JDialog();

	
	public EscAction(JDialog dialog) {
		this.dialog = dialog;

	}
	
	/**
	 * wenn die Action passiert wird das �bergebene Dialogfenster geschlosse(dispose)
	 * 
	 * @param alle Events
	 * 
	 * @return void
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.dispose();
	}

}
