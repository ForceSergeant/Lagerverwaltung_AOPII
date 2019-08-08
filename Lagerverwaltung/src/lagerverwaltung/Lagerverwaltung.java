package lagerverwaltung;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Lagerverwaltung {
	
	private LagerverwaltungGUI gui;

	//Konstruktor
	public Lagerverwaltung() {
		gui = new LagerverwaltungGUI();
	}
	
	public static void main(String[] args) {
		Lagerverwaltung application = new Lagerverwaltung();
		application.activate();
	}

	private void activate() {
		gui.setVisible(true);
	}
	

}
