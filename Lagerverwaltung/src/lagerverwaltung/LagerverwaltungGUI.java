package lagerverwaltung;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

public class LagerverwaltungGUI extends JFrame{

	private Actionlistener actionlistener = new Actionlistener();
	private LagerverwaltungDaten daten = new LagerverwaltungDaten();
	
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
	
	//IconMenu
	private JButton oeffnenbtn;
	private JButton beendenbtn;
	private JButton speichernbtn;
	private JButton anzeigenLagerinhaltbtn;
	private JButton entnehmenbtn;
	private JButton einlagernbtn;
	private JButton startseitebtn;
	
	//Icon
	private ImageIcon close;
	private ImageIcon house;
	private ImageIcon open;
	private ImageIcon pallettake;
	private ImageIcon palletstore;
	private ImageIcon save;
	private ImageIcon stock;
	
	//Startoberfläche
	private Box hilfsbox;
	private JPanel menupanel;
	private JPanel leftpanel;
	private JPanel rightpanel;
	
	//ProgressBar
	private CustomProgressBar freierPlatzBar;
	private CustomProgressBar freieFaecherBar;
	private JLabel freierPlatzLabel;
	private JLabel freieFaecherLabel;
	private JLabel hilfslabel;
	
	//Farbe
	private Color CustomColor;
	
	public LagerverwaltungGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.put("ProgressBarUI", "javax.swing.plaf.metal.MetalProgressBarUI");
		} catch (Exception e) {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			JOptionPane.showMessageDialog(null, "Wir konnten leider das gewünschte Look and Feel nicht verwenden und mussten auf das Look and Feel des Systems umsteigen.",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		//Eigenschaften		
		this.setLocation(0, 0);
		this.setTitle("Lagerverwaltung");
		this.setMinimumSize(new Dimension(600, 400));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new GridBagLayout());
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
		menueErzeugen();
				
		//Startpanels
		startpanelsErzeugen();
		
		//Button für Menupanel
		btnerzeugen();
		
		//Style
		stylebtn(oeffnenbtn);
		stylebtn(beendenbtn);
		stylebtn(speichernbtn);
		stylebtn(anzeigenLagerinhaltbtn);
		stylebtn(entnehmenbtn);
		stylebtn(einlagernbtn);
		stylebtn(startseitebtn);
		
		//Hilfsbox
		hilfsboxerzeugen();
		
		//ProgressBar
		progressbarErzeugen();
	
		//Actionlistener
		oeffnenitem.addActionListener(e -> actionlistener.oeffnen());
		beendenitem.addActionListener(e -> actionlistener.beenden(this, leftpanel, rightpanel, menupanel));
		speichernitem.addActionListener(e -> actionlistener.speichern(this, leftpanel, rightpanel, menupanel));
		anzeigenLagerinhaltitem.addActionListener(e -> actionlistener.anzeigenLagerinhalt(this, leftpanel, rightpanel));
		entnehmenitem.addActionListener(e -> actionlistener.entnehmen(this));
		einlagernitem.addActionListener(e -> actionlistener.einlagern(this, null, null));
		startseiteitem.addActionListener(e -> actionlistener.startseite(this, leftpanel, rightpanel, menupanel));
		
		oeffnenbtn.addActionListener(e -> actionlistener.oeffnen());
		speichernbtn.addActionListener(e -> actionlistener.speichern(this, leftpanel, rightpanel, menupanel));
		anzeigenLagerinhaltbtn.addActionListener(e -> actionlistener.anzeigenLagerinhalt(this, leftpanel, rightpanel));
		entnehmenbtn.addActionListener(e -> actionlistener.entnehmen(this));
		einlagernbtn.addActionListener(e -> actionlistener.einlagern(this, null, null));
		startseitebtn.addActionListener(e -> actionlistener.startseite(this, leftpanel, rightpanel, menupanel));
		beendenbtn.addActionListener(e -> actionlistener.beenden(this, leftpanel, rightpanel, menupanel));
	}
	
	
	private void startpanelsErzeugen() {
		CustomColor = new Color(80, 80, 80);
		hilfslabel = new JLabel(new ImageIcon(new ImageIcon("../Lagerverwaltung_AOPII/img/lager2.jpg").getImage().getScaledInstance( 600, 400,  java.awt.Image.SCALE_SMOOTH )));
		menupanel = new JPanel();
		leftpanel = new JPanel();
		rightpanel = new JPanel();
		
		menupanel.setLayout(new BoxLayout(menupanel, BoxLayout.LINE_AXIS));
		leftpanel.setLayout(new GridBagLayout());
		rightpanel.setLayout(new BorderLayout());
		leftpanel.setBorder(BorderFactory.createCompoundBorder((BorderFactory.createLineBorder(Color.BLACK)), new EmptyBorder(0, 20, 0, 20)));
		leftpanel.setBackground(CustomColor);

		//TODO Image immer auf größe des JPanels rightpanel
		rightpanel.add(hilfslabel);
		
		this.add(menupanel,gbcErzeugen(0, 0, 3, 1, 0.0, 0.0, GridBagConstraints.BOTH));
		this.add(leftpanel, gbcErzeugen(0, 1, 1, 1, 0.25, 0.0, GridBagConstraints.BOTH));
		this.add(rightpanel,gbcErzeugen(1, 1, 1, 1, 0.75, 1.0, GridBagConstraints.BOTH));
	}

	private void menueErzeugen() {
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
	}

	private void btnerzeugen() {
		open = new ImageIcon(new ImageIcon("../Lagerverwaltung_AOPII/icons/open.png").getImage().getScaledInstance( 55, 55,  java.awt.Image.SCALE_SMOOTH ));
		close = new ImageIcon(new ImageIcon("../Lagerverwaltung_AOPII/icons/close.png").getImage().getScaledInstance( 55, 55,  java.awt.Image.SCALE_SMOOTH ));
		house = new ImageIcon(new ImageIcon("../Lagerverwaltung_AOPII/icons/house.png").getImage().getScaledInstance( 55, 55,  java.awt.Image.SCALE_SMOOTH));
		palletstore = new ImageIcon(new ImageIcon("../Lagerverwaltung_AOPII/icons/palletstore2.png").getImage().getScaledInstance( 55, 55,  java.awt.Image.SCALE_SMOOTH));	
		pallettake = new ImageIcon(new ImageIcon("../Lagerverwaltung_AOPII/icons/pallettake2.png").getImage().getScaledInstance( 55, 55,  java.awt.Image.SCALE_SMOOTH ));
		save = new ImageIcon(new ImageIcon("../Lagerverwaltung_AOPII/icons/diskette.png").getImage().getScaledInstance( 55, 55,  java.awt.Image.SCALE_SMOOTH ));
		stock = new ImageIcon(new ImageIcon("../Lagerverwaltung_AOPII/icons/stock2.png").getImage().getScaledInstance( 55, 55,  java.awt.Image.SCALE_SMOOTH ));
				
		oeffnenbtn = new JButton("Datei öffnen", open);
		beendenbtn = new JButton("Beenden", close);
		startseitebtn = new JButton("Startseite", house);
		speichernbtn = new JButton("Speichern", save);
		anzeigenLagerinhaltbtn = new JButton("Lagerinhalt anzeigen", stock);
		entnehmenbtn = new JButton("Entnehmen", pallettake);
		einlagernbtn = new JButton("Einlagern", palletstore);	
	}
	
	private void hilfsboxerzeugen() {
		hilfsbox = Box.createHorizontalBox();
		hilfsbox.setBorder(new EmptyBorder(10, 0, 5, 0));
		hilfsbox.add(Box.createGlue());
		hilfsbox.add(oeffnenbtn);
		hilfsbox.add(Box.createGlue());
		hilfsbox.add(speichernbtn);
		hilfsbox.add(Box.createGlue());
		hilfsbox.add(anzeigenLagerinhaltbtn);
		hilfsbox.add(Box.createGlue());
		hilfsbox.add(entnehmenbtn);
		hilfsbox.add(Box.createGlue());
		hilfsbox.add(einlagernbtn);
		hilfsbox.add(Box.createGlue());
		hilfsbox.add(startseitebtn);
		hilfsbox.add(Box.createGlue());
		hilfsbox.add(beendenbtn);
		hilfsbox.add(Box.createGlue());
		menupanel.add(hilfsbox);
	}

	private void progressbarErzeugen() {
		freierPlatzBar = new CustomProgressBar(8000);
		freieFaecherBar = new CustomProgressBar(800);
		freierPlatzLabel = new JLabel();
		freieFaecherLabel = new JLabel();
		hilfslabel = new JLabel();
		
		freierPlatzBar.setModel(new DefaultBoundedRangeModel(0, 0 , 0, 8000));
		freieFaecherBar.setModel(new DefaultBoundedRangeModel(0, 0, 0, 800));
		freierPlatzBar.setUI(new CustomProgressBarUI());
		freieFaecherBar.setUI(new CustomProgressBarUI());
		freierPlatzBar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		freieFaecherBar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));		
		freierPlatzBar.setStringPainted(true);
		freieFaecherBar.setStringPainted(true);		
		freierPlatzBar.setBorder(new EmptyBorder(8,0,8,0));
		freieFaecherBar.setBorder(new EmptyBorder(8,0,8,0));
		
		freierPlatzLabel.setBorder(new EmptyBorder(0, 5, 10, 0));
		freieFaecherLabel.setBorder(new EmptyBorder(10, 5, 10, 0));
		freierPlatzLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD,  15));
		freieFaecherLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD,  15));
		freierPlatzLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		freieFaecherLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		
		//TODO Funktion schreiben, wie ich an die jeweiligen Daten komme
		freieFaecherBar.setValue(daten.freieRegalfaecher());
		freierPlatzBar.setValue(5000);
		
		freierPlatzLabel.setText("Freier Platz: \t"+ freierPlatzBar.getValue() +"/" + freierPlatzBar.getMaximum());
		freieFaecherLabel.setText("Freie Fächer: \t"+ freieFaecherBar.getValue() +"/" + freieFaecherBar.getMaximum());
		
		leftpanel.add(freierPlatzLabel, gbcErzeugen(0, 0, 1, 1, 1.0, 0.46, GridBagConstraints.BOTH));
		leftpanel.add(freierPlatzBar, gbcErzeugen(0, 1, 1, 1, 0.0, 0.01, GridBagConstraints.BOTH));
		leftpanel.add(freieFaecherLabel, gbcErzeugen(0, 2, 1, 1, 0.0, 0.05, GridBagConstraints.BOTH));
		leftpanel.add(freieFaecherBar, gbcErzeugen(0, 3, 1, 1, 0.0, 0.01, GridBagConstraints.BOTH));
		leftpanel.add(hilfslabel, gbcErzeugen(0, 4, 1, 1, 0.0, 0.47, GridBagConstraints.BOTH));
	}
	
	private void stylebtn(JButton component) {
		component.setSize(75, 75);
		component.setMargin(new Insets(0,0,0,0));
		component.setBorder(null);
		component.setVerticalTextPosition(SwingConstants.BOTTOM);
		component.setHorizontalTextPosition(SwingConstants.CENTER);
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

	public GridBagConstraints gbcErzeugen(int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.fill = fill;
		return gbc;
	}

}
