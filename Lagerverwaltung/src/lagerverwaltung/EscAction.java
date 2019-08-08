package lagerverwaltung;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

public class EscAction extends AbstractAction {

	private JDialog dialog = new JDialog();
	
	public EscAction(JDialog dialog) {
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		dialog.dispose();
	}

}
