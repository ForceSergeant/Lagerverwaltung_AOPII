package lagerverwaltung;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

public class Lagerverwaltung {
	
	private LagerverwaltungGUI gui;
	private LagerverwaltungDaten daten;

	//Konstruktor
	public Lagerverwaltung() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			
		}
		gui = new LagerverwaltungGUI();
		daten = new LagerverwaltungDaten();
		gui.setDaten(daten);
		//Erik war hier kkk
	}
	
	public static void main(String[] args) {
		Lagerverwaltung application = new Lagerverwaltung();
		application.activate();
	}

	private void activate() {
		gui.setVisible(true);
	}
	

}
