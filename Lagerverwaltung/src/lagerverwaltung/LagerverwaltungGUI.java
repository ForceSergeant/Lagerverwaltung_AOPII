package lagerverwaltung;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class LagerverwaltungGUI extends JFrame{
	
	private LagerverwaltungDaten daten;
	private Actionlistener actionlistener = new Actionlistener();
	
	//Menübar
	private JMenuBar menubar;
	private JMenu menuDatei;
	private JMenu menuZurueck;
	private JMenuItem oeffnenitem;
	private JMenuItem beendenitem;
	private JMenuItem speichernitem;
	private JMenuItem anzeigenLagerinhaltitem;
	private JMenuItem entnehmenitem;
	private JMenuItem einlagernitem;
	private JMenuItem startseiteitem;
	
	//Startoberfläche
	private JButton btnDateioeffnen;
	private JButton btnAnzeigenLagerinhalt;
	private JButton btnEntnehmen;
	private JButton btnEinlagern;
	
	//ProgressBar
	CustomProgressBar freierPlatz;
	CustomProgressBar freieFaecher;
	
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
		menuZurueck = new JMenu("Zurück");
		oeffnenitem = new JMenuItem("Datei laden");
		beendenitem = new JMenuItem("Beenden");
		speichernitem = new JMenuItem("Speichern");
		anzeigenLagerinhaltitem = new JMenuItem ("Anzeigen Lagerinhalt");
		entnehmenitem = new JMenuItem("Entnehmen eines Teils");
		einlagernitem = new JMenuItem("Einlagern");
		startseiteitem = new JMenuItem("Zurück zur Startseite");
		menuDatei.add(oeffnenitem);
		menuDatei.add(menuDatei);
		menuDatei.add(beendenitem);
		menuDatei.add(speichernitem);
		menuDatei.add(anzeigenLagerinhaltitem);
		menuDatei.add(entnehmenitem);
		menuDatei.add(einlagernitem);
		menuZurueck.add(startseiteitem);
		menubar.add(menuDatei);
		menubar.add(menuZurueck);		
		this.setJMenuBar(menubar);
		
		//Startpanels
		JPanel leftpanel = new JPanel();
		
		JPanel middlepanel = new JPanel();
		middlepanel.setLayout(new GridLayout(2,1));
		
		JPanel rightpanel = new JPanel();
		rightpanel.setLayout(new GridLayout(2,1));
		
		this.add(leftpanel);
		this.add(middlepanel);
		this.add(rightpanel);
		
		//Progressbar
		freierPlatz = new CustomProgressBar(8000);
		freieFaecher = new CustomProgressBar(800);
		
		freierPlatz.setModel(new DefaultBoundedRangeModel(0, 0 , 0, 8000));
		freieFaecher.setModel(new DefaultBoundedRangeModel(0, 0, 0, 800));
		
//		freierPlatz.setUI(new CustomProgressBarUI() );
		
		freierPlatz.setValue(7000);
		freieFaecher.setValue(0);
		freierPlatz.setStringPainted(true);
		freieFaecher.setStringPainted(true);
		
		leftpanel.add(freierPlatz);
		leftpanel.add(freieFaecher);
		
		//Button auf Panels hinzufügen
		btnDateioeffnen = new JButton("Datei laden");
		btnAnzeigenLagerinhalt = new JButton("Lagerinhalt anzeigen");
		btnEntnehmen = new JButton("Entnahme eines Teils");
		btnEinlagern = new JButton("Einlagern eines Teils");
		
		
		middlepanel.add(btnDateioeffnen);
		rightpanel.add(btnAnzeigenLagerinhalt);
		middlepanel.add(btnEntnehmen);
		rightpanel.add(btnEinlagern);
		
		//Actionlistener
		oeffnenitem.addActionListener(e -> actionlistener.oeffnen());
		beendenitem.addActionListener(e -> actionlistener.beenden(this));
		speichernitem.addActionListener(e -> actionlistener.speichern());
		anzeigenLagerinhaltitem.addActionListener(e -> actionlistener.anzeigenLagerinhalt(this, leftpanel, rightpanel, middlepanel));
		entnehmenitem.addActionListener(e -> actionlistener.entnehmen(this));
		einlagernitem.addActionListener(e -> actionlistener.einlagern(this, null, null));
		startseiteitem.addActionListener(e -> actionlistener.startseite(this, leftpanel, rightpanel, middlepanel));
		
	}

	public void setDaten(LagerverwaltungDaten daten) {
		this.daten = daten;	
	}
	
	public void einlagernErgebnisDialog(JTextField eingabebezeichnung, JTextField eingabeteilenummer, int[] ergebnis) {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width, height;
		if(screensize.getWidth()<= 1280 && screensize.getHeight() <= 720) {
			width = (int) Math.round(screensize.getWidth()/2);
			height = (int) Math.round(screensize.getHeight()/2);
		}
		else {
			width = (int) Math.round(screensize.getWidth()/3);
			height = (int) Math.round(screensize.getHeight()/3);
		}
		
		GridBagConstraints gbc = new GridBagConstraints();
		JDialog ergebnisdialog = new JDialog();
		JLabel teil = new JLabel();
		JLabel ergebnisx = new JLabel();
		JLabel ergebnisy = new JLabel();
		JLabel ergebnisz = new JLabel();
		JLabel ausschriftx = new JLabel("Der Fahrtweg in x-Richtung betrug: ");
		JLabel ausschrifty = new JLabel("Der Fahrtweg in y-Richtung betrug: ");
		JLabel ausschriftz = new JLabel("Der Fahrtweg in z-Richtung betrug: ");
		JButton btnok = new JButton("OK");
		EmptyBorder eborder = new EmptyBorder(0, 20, 0, 0);

		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "beenden");
		btnok.getActionMap().put("beenden", new EscAction(ergebnisdialog) );
		
		
		if(eingabeteilenummer.getText().length() > 0) {
			teil.setText("Das Teil mit der Bezeichnung " + eingabebezeichnung.getText() + 
				" und der Teilenummer " + eingabeteilenummer.getText() + 
				" wurde erfolgreich eingelagert");
		}
		else {
			teil.setText("Das Teil mit der Bezeichnung " + eingabebezeichnung.getText() + 
					" wurde erfolgreich eingelagert");
		}
		
		ergebnisx.setText(ergebnis[1] + " Meter");
		ergebnisy.setText(ergebnis[2] + " Meter");
		ergebnisz.setText(ergebnis[3] + " Meter");
		
		ausschriftx.setBorder(eborder);
		ausschrifty.setBorder(eborder);
		ausschriftz.setBorder(eborder);
		
		ergebnisdialog.setTitle("Teil wurde in das Lager aufgenommen.");
		ergebnisdialog.setLayout(new GridBagLayout());
		
		btnok.setBorder(new EmptyBorder(7, 25, 7, 25));		
		
		gbc.gridx = 0; //Spalte
		gbc.gridy = 1; //Zeile
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		ergebnisdialog.add(ausschriftx, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		ergebnisdialog.add(ergebnisx, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		ergebnisdialog.add(ausschrifty, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;

		ergebnisdialog.add(ergebnisy, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		ergebnisdialog.add(ausschriftz, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		ergebnisdialog.add(ergebnisz, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		ergebnisdialog.add(btnok, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		ergebnisdialog.add(teil, gbc);
		
		ergebnisdialog.setSize(width, height);
		ergebnisdialog.setLocationRelativeTo(null);
		ergebnisdialog.setVisible(true);
		
		//ActionListener
		btnok.addActionListener(e -> schliesseErgebnisDialog(ergebnisdialog));
	}

	public void entnehmenErgebnisDialog(String teilenamen, int[] ergebnis) {
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int width, height;
		if(screensize.getWidth()<= 1280 && screensize.getHeight() <= 720) {
			width = (int) Math.round(screensize.getWidth()/2);
			height = (int) Math.round(screensize.getHeight()/2);
		}
		else {
			width = (int) Math.round(screensize.getWidth()/3);
			height = (int) Math.round(screensize.getHeight()/3);
		}
		
		GridBagConstraints gbc = new GridBagConstraints();
		JDialog ergebnisdialog = new JDialog();
		JLabel teil = new JLabel();
		JLabel ergebnisx = new JLabel();
		JLabel ergebnisy = new JLabel();
		JLabel ergebnisz = new JLabel();
		JLabel ausschriftx = new JLabel("Der Fahrtweg in x-Richtung betrug: ");
		JLabel ausschrifty = new JLabel("Der Fahrtweg in y-Richtung betrug: ");
		JLabel ausschriftz = new JLabel("Der Fahrtweg in z-Richtung betrug: ");
		JButton btnok = new JButton("OK");
		
		EmptyBorder eborder = new EmptyBorder(0, 20, 0, 0);
		
		if(pruefeString(teilenamen)) {
			teil.setText("Das Teil mit der Teilenummer " + teilenamen + " wurde erfolgreich entnommen");	
		}
		else {
			teil.setText("Das Teil mit der Bezeichnung " + teilenamen + " wurde erfolgreich entnommen");
		}
		
		ergebnisx.setText(ergebnis[1] + " Meter");
		ergebnisy.setText(ergebnis[2] + " Meter");
		ergebnisz.setText(ergebnis[3] + " Meter");
		
		ausschriftx.setBorder(eborder);
		ausschrifty.setBorder(eborder);
		ausschriftz.setBorder(eborder);
		
		ergebnisdialog.setTitle("Teil wurde aus dem Lager entnommen.");
		ergebnisdialog.setLayout(new GridBagLayout());
		
		btnok.setBorder(new EmptyBorder(7, 25, 7, 25));
		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "beenden");
		btnok.getActionMap().put("beenden", new EscAction(ergebnisdialog) );
		
		gbc.gridx = 0; //Spalte
		gbc.gridy = 1; //Zeile
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		ergebnisdialog.add(ausschriftx, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		ergebnisdialog.add(ergebnisx, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		ergebnisdialog.add(ausschrifty, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;

		ergebnisdialog.add(ergebnisy, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		ergebnisdialog.add(ausschriftz, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		ergebnisdialog.add(ergebnisz, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		ergebnisdialog.add(btnok, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		ergebnisdialog.add(teil, gbc);
		
		ergebnisdialog.setSize(width, height);
		ergebnisdialog.setLocationRelativeTo(null);
		ergebnisdialog.setVisible(true);

		//Actionlistener
		btnok.addActionListener(e -> schliesseErgebnisDialog(ergebnisdialog));
		
	}

	public boolean pruefeString(String teilenamen) {
		boolean testbestanden;
		try {
			Integer.parseInt(teilenamen);
		    testbestanden = true;
		} catch(NumberFormatException e) {
		      testbestanden = false;
		   }
		   return testbestanden;
	}
	
	public void schliesseErgebnisDialog(JDialog ergebnisdialog) {
		ergebnisdialog.dispose();
	}
	
}
