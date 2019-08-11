package lagerverwaltung;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
	private CustomPanel rightpanel;
	
	//ProgressBar
	private CustomProgressBar freierPlatzBar;
	private CustomProgressBar freieFaecherBar;
	private JLabel freierPlatzLabel;
	private JLabel freieFaecherLabel;
	private JLabel ueberschriftLabel;
	private JLabel hilfslabel;
	
	//Farbe
	private Color CustomColor;
	
	//Ergebnisdialog
	private JDialog ergebnisDialog;
	private JLabel teilLabel;
	private JLabel[] ergebnisLabel;
	private JLabel[] ausschriftLabel;
	private JButton btnok;
	private EmptyBorder eborder;
	private Dimension screensize;
	
	
	
	/**
	 * Konstruktor: erzeugt die Graphikoberfläche bestehend aus Menü, Iconmenü, Auslastung des Lagers und ein Bild
	 * Zusätzlich werden Eigenschaften des JFrames gesetzt und der ActionListener für Buttons erzeugt
	 * 
	 */
	public LagerverwaltungGUI() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.put("ProgressBarUI", "javax.swing.plaf.metal.MetalProgressBarUI");
		} catch (Exception e) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Das Windows Look and Feel konnte leider nicht gesetzt werden.",
				    "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		//Eigenschaften		
		this.setLocation(0, 0);
		this.setTitle("Lagerverwaltung");
		this.setMinimumSize(new Dimension(600, 400));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setLayout(new GridBagLayout());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//		this.addWindowListener(new WindowAdapter(){
//			public void windowClosing(WindowEvent e) {		
//				int i = JOptionPane.showOptionDialog(null, "Wollen Sie das Porgramm wirklich beenden?",
//						"Programm schließen?", JOptionPane.YES_NO_OPTION,
//						JOptionPane.WARNING_MESSAGE, null, new String[] {"Ja", "Nein"}, "Ja");
//				if(i == JOptionPane.YES_OPTION) {
//					 actionlistener.speichern(gui, menupanel, leftpanel, rightpanel);
//					 System.exit(0);
//				 }
//			}
//		});
		
		//Menü
		menueErzeugen();
				
		//Startpanels
		startpanelsErzeugen();
		
		//Button für Menupanel
		iconmenuErzeugen();
		
		//Style
		stylebtn(oeffnenbtn);
		stylebtn(beendenbtn);
		stylebtn(speichernbtn);
		stylebtn(anzeigenLagerinhaltbtn);
		stylebtn(entnehmenbtn);
		stylebtn(einlagernbtn);
		stylebtn(startseitebtn);
		
		//Hilfsbox
		hilfsboxErzeugen();
		
		//ProgressBar
		progressbarErzeugen();
		
		
		//Actionlistener
		oeffnenitem.addActionListener(e -> {
			try {
				actionlistener.oeffnen();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		beendenitem.addActionListener(e -> actionlistener.beenden());
		speichernitem.addActionListener(e -> actionlistener.speichern());
		anzeigenLagerinhaltitem.addActionListener(e -> actionlistener.anzeigenLagerinhalt(this, menupanel, leftpanel, rightpanel));
		entnehmenitem.addActionListener(e -> actionlistener.entnehmen(this));
		einlagernitem.addActionListener(e -> actionlistener.einlagern(this, null, null));
		startseiteitem.addActionListener(e -> actionlistener.startseite(this, leftpanel, rightpanel, menupanel));
		
		oeffnenbtn.addActionListener(e -> {
			try {
				actionlistener.oeffnen();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		});
		speichernbtn.addActionListener(e -> actionlistener.speichern());
		anzeigenLagerinhaltbtn.addActionListener(e -> actionlistener.anzeigenLagerinhalt(this, menupanel, leftpanel, rightpanel));
		entnehmenbtn.addActionListener(e -> actionlistener.entnehmen(this));
		einlagernbtn.addActionListener(e -> actionlistener.einlagern(this, null, null));
		startseitebtn.addActionListener(e -> actionlistener.startseite(this, leftpanel, rightpanel, menupanel));
		beendenbtn.addActionListener(e -> actionlistener.beenden());
	
	}
	
	/**
	 * Erzeugt die Panels für die Startübersicht bestehend aus dem Iconmenü, Auslastung des Lagers und ein Bild
	 * Zusätzlich werden Eigenschaften dieser Panels gesetzt
	 * 
	 * @return void
	 */
	private void startpanelsErzeugen() {
		CustomColor = new Color(80, 80, 80);
		menupanel = new JPanel();
		leftpanel = new JPanel();
		rightpanel = new CustomPanel();
		
		menupanel.setLayout(new BoxLayout(menupanel, BoxLayout.LINE_AXIS));
		leftpanel.setLayout(new GridBagLayout());
		rightpanel.setLayout(new BorderLayout());
		leftpanel.setBorder(BorderFactory.createCompoundBorder((BorderFactory.createLineBorder(Color.BLACK)), new EmptyBorder(0, 20, 0, 20)));
		leftpanel.setBackground(CustomColor);

		Image image = null;
		try {
			image = ImageIO.read(new File("../Lagerverwaltung_AOPII/img/lager2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rightpanel.setImage(image);
		
		this.add(menupanel,gbcErzeugen(0, 0, 3, 1, 0.0, 0.0, GridBagConstraints.BOTH));
		this.add(leftpanel, gbcErzeugen(0, 1, 1, 1, 0.25, 0.0, GridBagConstraints.BOTH));
		this.add(rightpanel,gbcErzeugen(1, 1, 1, 1, 0.75, 1.0, GridBagConstraints.BOTH));
	}

	/**
	 * Erzeugt das Menü am oberen linken Rand des Programms: Dabei gibt es als die zwei Hauptmenüs Datei und Zurück
	 * Die Hauptmenüs haben dabei noch einzelne Menüunterpunkte
	 * 
	 * @retrun void
	 */
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

	/**
	 * Erzeugt das Iconmenü, welches sich auf dem menupanel befindet. Es werden die Icons geladen und die Buttons erzeugt
	 * 
	 * @return void
	 */
	private void iconmenuErzeugen() {
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
	
	/**
	 * Dient zur Ausrichtung der jeweiligen Iconmenübuttons, sodass diese immer den gleichen Abstand zueiander haben
	 * Geschieht mit Hilfe einer Box und deren createGlue methode
	 * 
	 * @retrun void
	 */
	private void hilfsboxErzeugen() {
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

	/**
	 * Erzeugt die Progressbars und die Label für die Veranschaulichung der Auslastung des Lagers
	 * Die Progressbar und die Labels werden erzeugt und gewisse Eigenschaften gesetzt
	 * 
	 * @return void
	 */
	private void progressbarErzeugen() {
		freierPlatzBar = new CustomProgressBar(8000);
		freieFaecherBar = new CustomProgressBar(800);
		freierPlatzLabel = new JLabel();
		freieFaecherLabel = new JLabel();
		ueberschriftLabel = new JLabel("Aktueller Stand des Lagers:");
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
		
		freierPlatzLabel.setText("Freier Platz: \t"+ freierPlatzBar.getValue() +"/" + freierPlatzBar.getMaximum());
		freieFaecherLabel.setText("Freie Fächer: \t"+ freieFaecherBar.getValue() +"/" + freieFaecherBar.getMaximum());

		ueberschriftLabel.setBorder(new EmptyBorder(0, 5, 10, 0));
		ueberschriftLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD,  15));
		ueberschriftLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ueberschriftLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		
		leftpanel.add(ueberschriftLabel, gbcErzeugen(0, 0, 1 ,1, 1.0, 0.46, GridBagConstraints.BOTH));
		leftpanel.add(freierPlatzLabel, gbcErzeugen(0, 1, 1, 1, 1.0, 0.01, GridBagConstraints.BOTH));
		leftpanel.add(freierPlatzBar, gbcErzeugen(0, 2, 1, 1, 0.0, 0.01, GridBagConstraints.BOTH));
		leftpanel.add(freieFaecherLabel, gbcErzeugen(0, 3, 1, 1, 0.0, 0.05, GridBagConstraints.BOTH));
		leftpanel.add(freieFaecherBar, gbcErzeugen(0, 4, 1, 1, 0.0, 0.01, GridBagConstraints.BOTH));
		leftpanel.add(hilfslabel, gbcErzeugen(0, 5, 1, 1, 0.0, 0.46, GridBagConstraints.BOTH));
	}

	//TODO funktioniert nicht
	public void aktualisierenProgressbar(int freierPlatz, int freieFaecher) {
		freieFaecherBar.setValue(800 - freieFaecher);
		freierPlatzBar.setValue(freierPlatz);
		freieFaecherLabel.setText("Freier Platz: \t"+ freierPlatzBar.getValue() +"/" + freierPlatzBar.getMaximum());
		freierPlatzLabel.setText("Freie Fächer: \t"+ freieFaecherBar.getValue() +"/" + freieFaecherBar.getMaximum());
	}

	
	/**
	 * Dient zum "Stylen" des jeweiligen Iconmenübuttons, es werden die benötigten Eigenschaften gesetzt
	 * 
	 * @see iconmenuErzeugen
	 * 
	 * @param component der Button, welcher diese Eigenschaften erhalten soll
	 * 
	 * @return void
	 */
	private void stylebtn(JButton component) {
		component.setSize(75, 75);
		component.setMargin(new Insets(0,0,0,0));
		component.setBorder(null);
		component.setVerticalTextPosition(SwingConstants.BOTTOM);
		component.setHorizontalTextPosition(SwingConstants.CENTER);
	}

	/**
	 * Erzeugt den Ergebnisdialog, welcher nach dem Einlagern zu sehen ist, abhängig von der Größe des verwendeten Monitors
	 * 
	 * @param eingabebezeichnung dient zur Ausgabe des Bezeichnung, welche das Teil beim Einlagern erhielt
	 * @param eingabeteilenummer dient zur Ausgabe der Teilenummer, welche das Teil beim Einlagern erhielt
	 * @param ergebnis enthält Informationen über die Wege, welche das Transportsystem zurück gelegt hat
	 * 
	 * @return void
	 */
	public void einlagernErgebnisDialog(JTextField eingabebezeichnung, JTextField eingabeteilenummer, int[] ergebnis) {
		screensize = actionlistener.getScreensize();
		ergebnisDialog = new JDialog();
		teilLabel = new JLabel();
		ergebnisLabel = new JLabel[3];
		ausschriftLabel = new JLabel[3];
		btnok = new JButton("OK");
		eborder = new EmptyBorder(0, 20, 0, 0);

		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "beenden");
		btnok.getActionMap().put("beenden", new EscAction(ergebnisDialog) );
		
		
		if(eingabeteilenummer.getText().length() > 0) {
			teilLabel.setText("Das Teil mit der Bezeichnung " + eingabebezeichnung.getText() + 
				" und der Teilenummer " + eingabeteilenummer.getText() + 
				" wurde erfolgreich eingelagert");
		}
		else {
			teilLabel.setText("Das Teil mit der Bezeichnung " + eingabebezeichnung.getText() + 
					" wurde erfolgreich eingelagert");
		}
		
		for(int i = 0; i < ergebnisLabel.length; i++) {
			ergebnisLabel[i] = new JLabel(ergebnis[i+1] + " Meter");
		}
		
		
		for(int i = 0; i < ausschriftLabel.length; i++) {
			ausschriftLabel[i] = new JLabel();
			ausschriftLabel[i].setBorder(eborder);
		}
		
		ausschriftLabel[0].setText("Der Fahrtweg in x-Richtung betrug: ");
		ausschriftLabel[1].setText("Der Fahrtweg in y-Richtung betrug: ");
		ausschriftLabel[2].setText("Der Fahrtweg in z-Richtung betrug: ");
		
		ergebnisDialog.setTitle("Teil wurde in das Lager aufgenommen.");
		ergebnisDialog.setLayout(new GridBagLayout());
		
		btnok.setBorder(new EmptyBorder(7, 25, 7, 25));		

		ergebnisDialog.add(ausschriftLabel[0], gbcErzeugen(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));
	
		ergebnisDialog.add(ergebnisLabel[0], gbcErzeugen(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(ausschriftLabel[1], gbcErzeugen(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(ergebnisLabel[1], gbcErzeugen(1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(ausschriftLabel[2], gbcErzeugen(0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(ergebnisLabel[2], gbcErzeugen(1, 3, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(btnok, gbcErzeugen(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.VERTICAL));
		
		ergebnisDialog.add(teilLabel, gbcErzeugen(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.VERTICAL));
		
		ergebnisDialog.setSize(screensize.width, screensize.height);
		ergebnisDialog.setLocationRelativeTo(null);
		ergebnisDialog.setVisible(true);
		
		//ActionListener
		btnok.addActionListener(e -> schliesseErgebnisDialog(ergebnisDialog));
	}

	/**
	 * Erzeugt den Ergebnisdialog, welcher nach dem entnehmen zu sehen ist, abhängig von der Größe des verwendeten Monitors
	 * 
	 * @param teilenamen enthält die Information entweder über die Bezeichnung des Teils oder die Teilenummer
	 * @param ergebnis enthält Informationen über die Wege, welche das Transportsystem zurück gelegt hat
	 * 
	 * @return void
	 */
	public void entnehmenErgebnisDialog(String teilenamen, int[] ergebnis) {
		screensize = actionlistener.getScreensize();
		ergebnisDialog = new JDialog();
		teilLabel = new JLabel();
		ergebnisLabel = new JLabel[3];
		ausschriftLabel = new JLabel[3];
		btnok = new JButton("OK");
		eborder = new EmptyBorder(0, 20, 0, 0);
		
		if(pruefeString(teilenamen)) {
			teilLabel.setText("Das Teil mit der Teilenummer " + teilenamen + " wurde erfolgreich entnommen");	
		}
		else {
			teilLabel.setText("Das Teil mit der Bezeichnung " + teilenamen + " wurde erfolgreich entnommen");
		}
		
		for(int i = 0; i < ergebnisLabel.length; i++) {
			ergebnisLabel[i] = new JLabel(ergebnis[i+1] + " Meter");
		}
		
		
		for(int i = 0; i < ausschriftLabel.length; i++) {
			ausschriftLabel[i] = new JLabel();
			ausschriftLabel[i].setBorder(eborder);
		}
		
		ausschriftLabel[0].setText("Der Fahrtweg in x-Richtung betrug: ");
		ausschriftLabel[1].setText("Der Fahrtweg in y-Richtung betrug: ");
		ausschriftLabel[2].setText("Der Fahrtweg in z-Richtung betrug: ");
		
		ergebnisDialog.setTitle("Teil wurde aus dem Lager entnommen.");
		ergebnisDialog.setLayout(new GridBagLayout());
		
		btnok.setBorder(new EmptyBorder(7, 25, 7, 25));
		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "beenden");
		btnok.getActionMap().put("beenden", new EscAction(ergebnisDialog) );

		ergebnisDialog.add(ausschriftLabel[0], gbcErzeugen(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(ergebnisLabel[0], gbcErzeugen(1, 1, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(ausschriftLabel[1], gbcErzeugen(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(ergebnisLabel[1], gbcErzeugen(1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(ausschriftLabel[2], gbcErzeugen(0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(ergebnisLabel[2], gbcErzeugen(1, 3, 1, 1, 1.0, 1.0, GridBagConstraints.BOTH));

		ergebnisDialog.add(btnok, gbcErzeugen(0, 4, 2, 1, 0.0, 0.0, GridBagConstraints.VERTICAL));
		
		ergebnisDialog.add(teilLabel, gbcErzeugen(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.VERTICAL));
		
		ergebnisDialog.setSize(screensize.width, screensize.height);
		ergebnisDialog.setLocationRelativeTo(null);
		ergebnisDialog.setVisible(true);

		//Actionlistener
		btnok.addActionListener(e -> schliesseErgebnisDialog(ergebnisDialog));
		
	}

	
	/**
	 * Versucht den String in eine Zahl umzuwandenl und je nachdem ob dies klappt wird true oder false zurück gegeben
	 * 
	 * @param teilenamen enthält die Information entweder über die Bezeichnung des Teils oder die Teilenummer
	 * 
	 * @return boolean true, wenn es sich um eine Zahl handelt
	 */
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
	
	/**
	 * Schließt das Fenster des Ergebnisdialogs, abhängig von dem übergebenem Parameter
	 * 
	 * @param ergebnisdialog erhält Informationen darüber, welches Fenster(JDialog) geschlossen werden soll
	 * 
	 * @return void
	 */
	public void schliesseErgebnisDialog(JDialog ergebnisdialog) {
		ergebnisdialog.dispose();
	}
	
	/**
	 * Erzeugt das richtige GridBagConstraints in Abhängigkeit von den übergebenen Parametern
	 * 
	 * @param gridx setzt gridx von gbc auf den jeweiligen Wert
	 * @param gridy setzt gridy von gbc auf den jeweiligen Wert
	 * @param gridwidth setzt gridwidth von gbc auf den jeweiligen Wert
	 * @param gridheight setzt gridheight von gbc auf den jeweiligen Wert
	 * @param weightx setzt weightx von gbc auf den jeweiligen Wert
	 * @param weighty setzt weighty von gbc auf den jeweiligen Wert
	 * @param fill setzt fill von gbc auf den jeweiligen Wert
	 * 
	 * @return GridBagConstraints gbc: wird zur Positionierung im GridBagLayout benötigt
	 */
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
