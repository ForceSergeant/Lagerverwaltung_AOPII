package lagerverwaltung;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

/**
 * Klasse die eine Action darstellt
 * Abgeleitet von AbstractAction, überschreibt actionPerformed
 *
 */
public class EscAction extends AbstractAction {

	private JDialog dialog = new JDialog();

	/**
	 * Konstruktor zur Erzeugung eines neuen Objektes der Klasse EscAction
	 * 
	 * @param dialog der JDialog, der geschlossen (disposed) werden soll
	 */
	public EscAction(JDialog dialog) {
		this.dialog = dialog;

	}
	
	/**
	 * wenn die Action passiert wird das übergebene Dialogfenster geschlosse(dispose)
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
