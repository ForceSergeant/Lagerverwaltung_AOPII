package lagerverwaltung;

import javax.swing.UnsupportedLookAndFeelException;

public class Lagerverwaltung {
	
	private LagerverwaltungGUI gui = new LagerverwaltungGUI();

	//Konstruktor
	public Lagerverwaltung() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		gui = new LagerverwaltungGUI();
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		Lagerverwaltung application = new Lagerverwaltung();
		application.activate();
		
	}

	private void activate() {
		gui.setVisible(true);
		System.out.println(gui.getContentPane().getSize());
	}
	

}
