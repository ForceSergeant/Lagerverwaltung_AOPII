package lagerverwaltung;

import javax.swing.UnsupportedLookAndFeelException;

public class Lagerverwaltung {
	
	private LagerverwaltungGUI gui;

	//Konstruktor
	public Lagerverwaltung() {
		gui = new LagerverwaltungGUI();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		Lagerverwaltung application = new Lagerverwaltung();
		application.activate();		
	}

	private void activate() {
		gui.setVisible(true);
	}
	

}
