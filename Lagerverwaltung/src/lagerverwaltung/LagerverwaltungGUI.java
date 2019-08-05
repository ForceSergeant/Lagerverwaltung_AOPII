package lagerverwaltung;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class LagerverwaltungGUI extends JFrame{
	
	//Komponenten der Oberfläche
	
	private LagerverwaltungDaten daten;
	private Actionlistener actionlistener = new Actionlistener();
	
	//Menübar
	private JMenuBar menubar;
	private JMenu menuDatei;
	private JMenuItem oeffnenitem;
	private JMenuItem menuBeenden;
	private JMenuItem menuSpeichern;
	private JMenuItem menuanzeigenLagerinhalt;
	private JMenuItem menuentnehmen;
	private JMenuItem menueinlagern;
	
	//Startoberfläche
	private JButton btnDateioeffnen;
	private JButton btnAnzeigenLagerinhalt;
	private JButton btnEntnehmen;
	private JButton btnEinlagern;
	
	public LagerverwaltungGUI() {
		//Eigenschaften
		this.setLocation(0, 0);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("Lagerverwaltung");
		this.setMinimumSize(new Dimension(500, 300));
		this.setLayout(new GridLayout(1,3));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Eigentliche Close Operation
//		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//		this.addWindowListener(new WindowAdapter(){
//			public void windowClosing(WindowEvent e) {		
//				int i = JOptionPane.showOptionDialog(null, "<html><p style=\"color:red\">Wollen Sie das Porgramm wirklich beenden?</p></html>", "Programm schließen?",
//						 JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] 
//								 {"Ja", "Nein"}, "Ja");
//				if(i == JOptionPane.YES_OPTION) {
//					 //Hier kommt der Speicheralgorithmus
//					 System.exit(0);
//				 }
//			}
//		});
		
		//Menü
		menubar = new JMenuBar();
		menuDatei = new JMenu("Datei");
		oeffnenitem = new JMenuItem("Datei laden");
		menuBeenden = new JMenuItem("Beenden");
		menuSpeichern = new JMenuItem("Speichern");
		menuanzeigenLagerinhalt = new JMenuItem ("Anzeigen Lagerinhalt");
		menuentnehmen = new JMenuItem("Entnehmen eines Teils");
		menueinlagern = new JMenuItem("Einlagern");
		menuDatei.add(oeffnenitem);
		menuDatei.add(menuDatei);
		menuDatei.add(menuBeenden);
		menuDatei.add(menuSpeichern);
		menuDatei.add(menuanzeigenLagerinhalt);
		menuDatei.add(menuentnehmen);
		menuDatei.add(menueinlagern);
		menubar.add(menuDatei);
		this.setJMenuBar(menubar);
		
		//Startpanels
		JPanel leftpanel = new JPanel();
		leftpanel.setBackground(Color.red);
		
		JPanel middlepanel = new JPanel();
		middlepanel.setLayout(new GridLayout(2,1));
		
		JPanel rightpanel = new JPanel();
		rightpanel.setLayout(new GridLayout(2,1));
		
		this.add(leftpanel);
		this.add(middlepanel);
		this.add(rightpanel);
		
		//Button auf Panels hinzufügen
		btnDateioeffnen = new JButton("Datei laden");
		btnAnzeigenLagerinhalt = new JButton("Lagerinhalt anzeigen");
		btnEntnehmen = new JButton("Entnahme eines Teils");
		btnEinlagern = new JButton("Einlagern eines Teils");
		
		btnDateioeffnen.setBackground(Color.CYAN);

		
		middlepanel.add(btnDateioeffnen);
		rightpanel.add(btnAnzeigenLagerinhalt);
		middlepanel.add(btnEntnehmen);
		rightpanel.add(btnEinlagern);
		
		
		
		
		
		//Actionlistener
		oeffnenitem.addActionListener(e -> actionlistener.oeffnen(this));
		menuBeenden.addActionListener(e -> actionlistener.beenden(this));
		menuSpeichern.addActionListener(e -> actionlistener.speichern(this));
		menuanzeigenLagerinhalt.addActionListener(e -> actionlistener.anzeigenLagerinhalt(this));
		menuentnehmen.addActionListener(e -> actionlistener.entnehmen(this));
		menueinlagern.addActionListener(e -> actionlistener.einlagern(this));
		
	}

	private void beenden() {
		System.out.println("Schließen-Button");
	}


	public void setDaten(LagerverwaltungDaten daten) {
		this.daten = daten;
		
	}

}
