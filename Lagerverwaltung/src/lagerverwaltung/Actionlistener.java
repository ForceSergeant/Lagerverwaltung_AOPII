package lagerverwaltung;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Document;
 
public class Actionlistener {
	
	private LagerverwaltungDaten daten = new LagerverwaltungDaten();
	
	/**
	 * Öffnet einen FileChooser, mit dessen Hilfe man die Datei auswählen kann die geöffnet werden soll
	 * Liest die Daten aus der Datei und übergibt sie an die Klasse LagerverwaltungDaten
	 * @see LagerverwaltungDaten
	 * 
	 * @throws IOException fängt die Exception beim Öffnen der Datei ab
	 */
	public void oeffnen(LagerverwaltungGUI gui) throws IOException {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
		JFileChooser chooser = new JFileChooser();
		boolean erfolgreichgeladen = true;
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Datei laden");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setSelectedFile(new File("test.txt"));
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\Save"));
		
		int result = chooser.showOpenDialog(null);
		
		if(result == JFileChooser.APPROVE_OPTION) {
			String dateipfad = chooser.getSelectedFile().toString();
			if(chooser.getSelectedFile().toString().endsWith(".txt")) {
				//TODO Öffnenalgorithmus
				BufferedReader br = new BufferedReader(new FileReader(dateipfad));
				String zeile = "";

				while( (zeile = br.readLine()) != null ) {
					if(zeile != null && zeile.length() >0) {
						String[] string = zeile.substring(0, zeile.length()-1).split("~");
						try {
							daten.laden(string[0], Integer.parseInt(string[1]), Integer.parseInt(string[2]), Integer.parseInt(string[3]),Integer.parseInt(string[4]), Integer.parseInt(string[5]), Integer.parseInt(string[6]));
						} catch (Exception e) {
							erfolgreichgeladen = false;
							break;
						}		
				   	}
				}
				br.close();
			} else {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sie können nur Textdateien (.txt) öffnen.",
					"Fehler", JOptionPane.ERROR_MESSAGE);
				oeffnen(gui);
			}
			if(!erfolgreichgeladen) {
				JOptionPane.showMessageDialog(null, "Die Textdatei ist ungültig und kann nicht geladen werden",
						"Fehler", JOptionPane.ERROR_MESSAGE);
			}
			gui.aktualisierenProgressbar(daten.getOccupied(), daten.getfreieRegalfaecher());
		}
	}
	
	/**
	 * Öffnet einen FileChooser, mit dessen Hilfe man die Datei auswählen kann in die gespeichert werden soll
	 * Überschreibt die jeweilige Datei mit den Daten
	 *
	 * @return void
	 */
	public void speichern() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
		BufferedWriter writer = null;
		UIManager.put("FileChooser.cancelButtonText", "Dont Save");
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Speichern unter");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setSelectedFile(new File("test.txt"));
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")+ "\\Save"));
		chooser.setVisible(true);

		int result = chooser.showSaveDialog(chooser);		
	
		if(result == JFileChooser.APPROVE_OPTION) {
			String dateipfad = chooser.getSelectedFile().toString();
			if(chooser.getSelectedFile().toString().endsWith(".txt")) {
				ArrayList<ArrayList<String>> inhaltdaten = new ArrayList<ArrayList<String>>();	
				inhaltdaten = daten.getItemTable();
				try {
					writer = new BufferedWriter(new FileWriter(dateipfad, false));
					for(int i = 0; i < inhaltdaten.size(); i++) {
						for(int j = 0; j < inhaltdaten.get(i).size(); j++) {
							writer.write(inhaltdaten.get(i).get(j)); 
							writer.write("~");
						}
						writer.write("\n");
					} 
				} catch (IOException e) {
					e.printStackTrace();
				}
				finally {
		            if (writer != null) {
		                try {
							writer.close();
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, "Beim Schließen des BufferedWriter kam es zu einem unerwarteten Fehler.",
									"Fehler", JOptionPane.ERROR_MESSAGE);
						}
		            }
				}       
			} else {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sie können nur in Textdateien (.txt) speichern.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				speichern();
			}
		}
	}

	/**
	 * Beendet die Anwendung nachdem gefragt wurde, ob gespeichert werden soll
	 * 
	 * @see speichern
	 */
	public void beenden() {
			int i = JOptionPane.showOptionDialog(null, "Wollen Sie das Programm wirklich beenden?", "Programm schließen?",
					 JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] 
							 {"Ja", "Nein"}, "Ja");
			if(i == JOptionPane.YES_OPTION) {
				 speichern();
				 System.exit(0);
			 }
		}

	/**
	 * Erzeugt das Panel auf dem sich die Tabelle befindet und ruft die Methode tabelleErzeugen auf
	 * 
	 * @see tabelleErzeugen
	 * @see startseite
	 * 
	 * @param gui benötigt Methode startseite zum Zurückkehren auf die Startseite
	 * @param menupanel benötigt Methode startseite zum Zurückkehren auf die Startseite
	 * @param leftpanel benötigt Methode startseite zum Zurückkehren auf die Startseite
	 * @param rightpanel benötigt Methode startseite zum Zurückkehren auf die Startseite
	 */
	public void anzeigenLagerinhalt(LagerverwaltungGUI gui, JPanel menupanel, JPanel leftpanel, JPanel rightpanel) {
		//Entfernt die Panels der Startübersicht
		for (Component c : gui.getContentPane().getComponents()) {
			gui.remove(c);
		}
		JPanel lagerPanel = new JPanel();
		lagerPanel.setLayout(new BorderLayout());
		lagerPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
		lagerPanel.setBackground(Color.DARK_GRAY);
		
		lagerPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "beenden");
		lagerPanel.getActionMap().put("beenden", new EscActionForLagerinhaltAnzeigen(gui, leftpanel, rightpanel, menupanel) );
		
		gui.setLayout(new BorderLayout());
		gui.add(lagerPanel, BorderLayout.CENTER);
		gui.pack();
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		tabelleErzeugen(gui,lagerPanel, menupanel, leftpanel, rightpanel);	
	}

	/**
	 * Erzeugt die Tabelle und holt sich die Daten aus LagerverwaltungDaten, ruft die Methode sortieren auf
	 * 
	 * @see LagerverwaltungDaten
	 * @see sortieren
	 * @see startseite
	 * 
	 * @param gui benötigt Methode startseite zum Zurückkehren auf die Startseite
	 * @param menupanel benötigt Methode startseite zum Zurückkehren auf die Startseite
	 * @param leftpanel benötigt Methode startseite zum Zurückkehren auf die Startseite
	 * @param rightpanel benötigt Methode startseite zum Zurückkehren auf die Startseite
	 */
	private void tabelleErzeugen(LagerverwaltungGUI gui, JPanel lagerpanel, JPanel menupanel, JPanel leftpanel, JPanel rightpanel) {		
		JTable tabelle = null;
		String[] theader = {"Bezeichnung", "Teilenummer", "Größe", "Anzahl", "Regalnummer", "Fachspalte", "Fachreihe" };
		ArrayList<ArrayList<String>> inhaltdaten = new ArrayList<ArrayList<String>>();
		inhaltdaten = daten.getItemTable();
		String[][] inhalt = new String[inhaltdaten.size()][7];
		
		for(int i = 0; i < inhaltdaten.size(); i++) {
			for(int j = 0; j < inhaltdaten.get(i).size(); j++) {
				inhalt[i][j] = inhaltdaten.get(i).get(j); 
			}
		}
		
		tabelle = new JTable(new CustomTableModel(inhalt, theader));
		
		tabelle.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
		lagerpanel.add(new JScrollPane(tabelle), BorderLayout.CENTER);
		lagerpanel.repaint();
		
		sortieren(tabelle, gui, lagerpanel, menupanel, leftpanel, rightpanel);	
	}
	
	/**
	 * Öffnet das Dialogfenster und fragt nach was sortiert werden soll,
	 * bei Abbruch kert der Benztzer zur Startübersicht zurück
	 * 
	 * @see sortierenBezeichnung
	 * @see sortierenTeilenummer
	 * @see startseite
	 * 
	 * @param tabelle wird in den Methoden sortierenBezeichnung und sortierenTeilenummer benötigt
	 * @param gui benötigt Methode startseite zum Zurückkehren auf die Startseite
	 * @param menupanel benötigt Methode startseite zum Zurückkehren auf die Startseite
	 * @param leftpanel benötigt Methode startseite zum Zurückkehren auf die Startseite
	 * @param rightpanel benötigt Methode startseite zum Zurückkehren auf die Startseite
	 */
	private void sortieren(JTable tabelle, LagerverwaltungGUI gui, JPanel lagerpanel, JPanel menupanel, JPanel leftpanel, JPanel rightpanel) {
		int i = JOptionPane.showOptionDialog(null,
				"Wonach möchten Sie sortieren?", "Lagerinhalt anzeigen",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, new String[] {"Bezeichnung", "Teilenummer", "Abbrechen"}, "Bezeichnung");
		if (i == JOptionPane.YES_OPTION) {
			sortierenBezeichnung(tabelle);
		}
		else if(i == JOptionPane.NO_OPTION) {
			sortierenTeilenummer(tabelle);
		}
		else if(i == JOptionPane.CANCEL_OPTION || i == JOptionPane.CLOSED_OPTION) {
			startseite(gui, leftpanel, rightpanel, menupanel);
		}
	}

	/**
	 * Sortiert die Zeilen der Tabelle nach der Bezeichnung, also der ersten Spalte
	 * 
	 * @param tabelle, die Tabelle, die sortiert werden soll
	 */
	private void sortierenBezeichnung(JTable tabelle) {
		DefaultTableModel model = (DefaultTableModel) tabelle.getModel();
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
		sorter.setModel(model);
		tabelle.setRowSorter(sorter);
	    tabelle.getRowSorter().toggleSortOrder(0);
	}

	/**
	 * Sortiert die Zeilen der Tabelle nach der Teilenummer, also der zweiten Spalte
	 * 
	 * @param tabelle, die Tabelle, die sortiert werden soll
	 */
	private void sortierenTeilenummer(JTable tabelle) {
		DefaultTableModel model = (DefaultTableModel) tabelle.getModel();
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
	    tabelle.setRowSorter(sorter);
	    sorter.setModel(model);
	    tabelle.getRowSorter().toggleSortOrder(1);	
	}
	
	/**
	 * Ändert den Text des artlabel, für den Fall dass der Radiobutton geändert wird
	 * Ändert das Document des Labels und fokusiert es (setzt den Cursor in dieses)
	 * 
	 * @param artlabel das Label, welches den neuen Text erhalten soll
	 * @param eingabetxtfield Textfeld, welches das neue Dokument und den Fokus erhalten soll
	 * @param standarddocument das Doukment
	 */
	private void radiobuttonAuswahlBezeichnung(JLabel artlabel, JTextField eingabetxtfield, Document standarddocument) {
			artlabel.setText("Bezeichnung des Teils:");
			eingabetxtfield.setDocument(standarddocument);
			eingabetxtfield.requestFocusInWindow(); 
		}
	
	/**
	 * Ändert den Text des artlabel, für den Fall dass der Radiobutton geändert wird
	 * Ändert das Document des Labels und fokusiert es (setzt den Cursor in dieses)
	 * 
	 * @param artlabel das Label, welches den neuen Text erhalten soll
	 * @param eingabetxtfield Textfeld, welches das neue Dokument und den Fokus erhalten soll
	 */
	private void radiobuttonAuswahlTeilenummer(JLabel artlabel, JTextField eingabetxtfield) {
			eingabetxtfield.setDocument(new AllowedDocument(9));
			artlabel.setText("Teilenummer des Teils:");
			eingabetxtfield.requestFocusInWindow(); 
		}
	
	/**
	 * Öffnet das Dialogfenster für die Entnahme eines Teils, setzt Eigenschaften der Komponenten
	 * und weist das Verhalten der Buttons zu (ActionListener)
	 * 
	 * @see entnehmenDatenuebergabe
	 * 
	 * @param gui wird für die Methode entnehmenDatenuebergabe benötigt
	 */
	public void entnehmen(LagerverwaltungGUI gui) {
		Dimension screensize = getScreensize();
		JDialog entnehmenDialog = new JDialog();
		JPanel radioPanel = new JPanel();
		JLabel wahlLabel = new JLabel("Wählen Sie aus, ob sie die Bezeichnung oder die Teilenummer angeben wollen:");
		JLabel artLabel = new JLabel("Bezeichnung des Teils:");
		JTextField eingabeTextfield = new JTextField(15);
		ButtonGroup btngroup = new ButtonGroup();
		JRadioButton btnbezeichnung = new JRadioButton("Bezeichnung");
		JRadioButton btnteilenummer = new JRadioButton("Teilenummer");
		JButton btnok = new JButton("Entnehmen");
		
		//bekommt das Standarddokument eines JTextfield
		Document standarddocument = eingabeTextfield.getDocument();
		
		btnbezeichnung.setSelected(true);
	    
	    //Ermöglicht nur eine Auswahl
		btngroup.add(btnteilenummer);
		btngroup.add(btnbezeichnung);
		
		//Hinzufügen der Componenten
		radioPanel.add(btnbezeichnung);
		radioPanel.add(btnteilenummer);	
		
		//Setzt den Focus in das Textfeld
		SwingUtilities.invokeLater( new Runnable() { 
			public void run() { 
			        eingabeTextfield.requestFocus(); 
			    } 
			} );
		
		entnehmenDialog.setTitle("Teil entnehmen");
		entnehmenDialog.setLayout(new GridBagLayout());
		
		//Hinzufügen der Komponenten

		entnehmenDialog.add(wahlLabel, gbcErzeugen(0, 0, 1.0, 1.0, 1));
		
		entnehmenDialog.add(radioPanel, gbcErzeugen(1, 0, 1.0, 1.0, 1));
		
		entnehmenDialog.add(artLabel, gbcErzeugen(0, 1, 1.0, 1.0, 1));
		
		entnehmenDialog.add(eingabeTextfield, gbcErzeugen(1, 1, 1.0, 1.0, 1));
		
		entnehmenDialog.add(btnok, gbcErzeugen(1, 2, 1.0, 1.0, 1));
		
		//Legt Größe und Position des Frames fest
		entnehmenDialog.setSize(screensize.width, screensize.height);
		entnehmenDialog.setLocationRelativeTo(null);
		entnehmenDialog.setVisible(true);
		
		//Actionlistener
		btnbezeichnung.addActionListener(e -> radiobuttonAuswahlBezeichnung(artLabel, eingabeTextfield, standarddocument));
		btnteilenummer.addActionListener(e -> radiobuttonAuswahlTeilenummer(artLabel, eingabeTextfield));
		eingabeTextfield.addActionListener(e -> entnehmenDatenuebergabe(btnbezeichnung, btnteilenummer, eingabeTextfield, entnehmenDialog, gui));
		btnok.addActionListener(e -> entnehmenDatenuebergabe(btnbezeichnung, btnteilenummer, eingabeTextfield, entnehmenDialog, gui));
		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "schließen");
		btnok.getActionMap().put("schließen", new EscAction(entnehmenDialog));
	}

	/**
	 * Übergibt die eingegebenen Daten (Bezeichnung, Teilenummer und Größe) and LagerverwaltungDaten
	 * Ruft die Ergebnisdialoge auf durch die Methoden in LagerverwaltungGUI
	 * Ruft die Methode aktualiserenProgressbar auf
	 * 
	 * @see LagerverwaltungDaten
	 * @see LagerverwaltungGUI
	 * @see aktualisierenProgressbar
	 * 
	 * @param btnbezeichnung Radiobutton für die Bezeichnung
	 * @param btnteilenummer Radiobutton für die Teilenummer
	 * @param eingabetxtfield Eingabefeld mit Informationen über Bezeichnung/Teilenummer
	 * @param entnehmenDialog Dialogfenster welches sich öffnet
	 * @param gui Instanzt der Klasse LagerverwaltungGUI
	 */
	private void entnehmenDatenuebergabe(JRadioButton btnbezeichnung, JRadioButton btnteilenummer, JTextField eingabetxtfield, JDialog entnehmenDialog, LagerverwaltungGUI gui) {
		String bezeichnung = "";
		String teilenummer = "";
		int teilenummerint = 0;
		int[] ergebnis = new int[4];
		
		if(eingabetxtfield.getText().length() > 0) {
			if(btnbezeichnung.isSelected()) {
				bezeichnung = eingabetxtfield.getText();
				teilenummerint = -1;
			}
			else if(btnteilenummer.isSelected()) {
				teilenummer = eingabetxtfield.getText();
				teilenummerint = Integer.parseInt(teilenummer);
				bezeichnung = "";
			}

			ergebnis = daten.entnehmen(bezeichnung, teilenummerint);
			
			gui.aktualisierenProgressbar(daten.getOccupied(), daten.getfreieRegalfaecher());
			
			if(ergebnis[0] == 1) {
				if(btnbezeichnung.isSelected()) {
					gui.entnehmenErgebnisDialog(bezeichnung, ergebnis);
				}
				else if(btnteilenummer.isSelected()) {
					gui.entnehmenErgebnisDialog(teilenummer, ergebnis);
				}
			}
			else  {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Das angegebene Teil konnte leider nicht gefunden werden."
						+ "\n Suchen Sie nach einem anderen Teil.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				entnehmen(gui);
			}
		}
		else {
			java.awt.Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Sie müssen etwas in das Textfeld eingeben",
					"Fehler", JOptionPane.ERROR_MESSAGE);
			entnehmen(gui);
		}
		entnehmenDialog.dispose();	
	}

	/**
	 * Öffnet das Dialogfenster für das Einlagern eines Teils, setzt Eigenschaften seiner Komponenten, 
	 * und den ActionListener für die Buttons
	 * 
	 * @see einalgernDatenuebergabe
	 * 
	 * @param gui wird für die Methode einlagernDatenuebergabe benötigt
	 * @param bezeichnung wird für den Wiederaufruf bei falscher Eingabe benötigt, damit die Bezeichnung nicht erneut eingegeben werden muss
	 * @param groesse wird für den Wiederaufruf bei falscher Eingabe benötigt, damit die Größe nicht erneut eingebene werden muss
	 */
	public void einlagern(LagerverwaltungGUI gui, String bezeichnung, String teilenummer, String groesse) {
		Dimension screensize = getScreensize();
		JDialog einlagernDialog = new JDialog();
		JLabel bezeichnungLabel = new JLabel("Bezeichnung:");
		JLabel teilenummerLabel  = new JLabel("Teilenummer:");
		JLabel groessLabel = new JLabel("Größe des Teils in Grundeinheiten:");
		JTextField eingabebezeichnung = new JTextField(bezeichnung, 20);
		JTextField eingabeteilenummer = new JTextField(20);
		JTextField eingabegroesse = new JTextField(groesse, 2);
		JButton btnok = new JButton("Ok");
		
		//Setzt den Focus in das Textfeld
		SwingUtilities.invokeLater( new Runnable() { 
			public void run() { 
				eingabebezeichnung.requestFocus(); 
			} 
		} );
		
		btnok.setBorder(new EmptyBorder(7, 20, 7, 20));
		eingabeteilenummer.setDocument(new AllowedDocument(9));
		eingabeteilenummer.setText(teilenummer);
		eingabegroesse.setDocument(new AllowedDocument(2));
		eingabegroesse.setText(groesse);
		
		einlagernDialog.setTitle("Teil einlagern");
		einlagernDialog.setLayout(new GridBagLayout());		

		einlagernDialog.add(bezeichnungLabel, gbcErzeugen(0, 0, 1.0, 1.0, 1));
		
		einlagernDialog.add(eingabebezeichnung, gbcErzeugen(1, 0, 1.0, 1.0, 1));
		
		einlagernDialog.add(teilenummerLabel, gbcErzeugen(0, 1, 1.0, 1.0, 1));
		
		einlagernDialog.add(eingabeteilenummer, gbcErzeugen(1, 1, 1.0, 1.0, 1));
		
		einlagernDialog.add(groessLabel, gbcErzeugen(0, 2, 1.0, 1.0, 1));
		
		einlagernDialog.add(eingabegroesse, gbcErzeugen(1, 2, 1.0, 1.0, 1));
		
		einlagernDialog.add(btnok, gbcErzeugen(0, 3, 1.0, 1.0, 2));
		
		einlagernDialog.setSize(screensize.width, screensize.height);
		einlagernDialog.setLocationRelativeTo(null);
		einlagernDialog.setVisible(true);
		
		//ActionListener
		eingabegroesse.addActionListener(e -> einlagernDatenuebergabe(einlagernDialog, eingabebezeichnung, eingabeteilenummer, eingabegroesse, gui));
		btnok.addActionListener(e -> einlagernDatenuebergabe(einlagernDialog, eingabebezeichnung, eingabeteilenummer, eingabegroesse, gui));
		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "beenden");
		btnok.getActionMap().put("beenden", new EscAction(einlagernDialog));		
	}

	/**
	 * Übergibt die Eingaben des Nutzers an LagerverwaltungDaten und gibt Auskunft darüber, ob das Einlagern erfolgreich war oder nicht
	 * Fängt zusätzlich Fehler bei der Eingabe ab
	 * Ruft die Methode aktualisierenProgressbar auf
	 * 
	 * @see LagerverwaltungDaten
	 * @see LagerverwaltungGUI @see aktualisierenProgressbar
	 * 
	 * @param einlagerndialog
	 * @param eingabebezeichnung
	 * @param eingabeteilenummer
	 * @param eingabegroesse
	 * @param gui
	 */
	private void einlagernDatenuebergabe(JDialog einlagerndialog, JTextField eingabebezeichnung, JTextField eingabeteilenummer, JTextField eingabegroesse, LagerverwaltungGUI gui) {
		int[] ergebnis = new int[4];
		int teilenummer;
		
		if(eingabebezeichnung.getText().length() > 0 && eingabegroesse.getText().length() > 0) {
			if(eingabeteilenummer.getText().length() > 0) {
				teilenummer = Integer.parseInt(eingabeteilenummer.getText());	
			}
			else {
				teilenummer = -1;
			}
			int groesse = Integer.parseInt(eingabegroesse.getText());
			ergebnis = daten.einlagern(eingabebezeichnung.getText(), teilenummer, groesse);
			
			
			gui.aktualisierenProgressbar(daten.getOccupied(), daten.getfreieRegalfaecher());
			
			switch(ergebnis[0]) {
			case 0:
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Das Teil kann nicht eingelagert werden, da das Fach bereits voll ist.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				break;
			case 1: 
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Ein Fach hat die Größe von 10 Grundeinheiten, "
						+ "daher ist 10 die maximale Zahl die Sie hier eintragen können.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, eingabebezeichnung.getText(), eingabeteilenummer.getText(), null);
				break;
			case 2:
				gui.einlagernErgebnisDialog(eingabebezeichnung, eingabeteilenummer, ergebnis);
				break;
			case 3:
				gui.einlagernErgebnisDialog(eingabebezeichnung, eingabeteilenummer, ergebnis);
				break;
			
			default:
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Es ist ein unerwarteter Fehler aufgetreten.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else {		
			if(eingabebezeichnung.getText().length() > 0 && eingabegroesse.getText().length() == 0) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sie müssen die Größe des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, eingabebezeichnung.getText(), eingabeteilenummer.getText(), null);
			}
			else if(eingabegroesse.getText().length() > 0 && eingabebezeichnung.getText().length() == 0) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sie müssen die Bezeichnung des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, null, eingabeteilenummer.getText(), eingabegroesse.getText());
			}
			else if(eingabebezeichnung.getText().length() == 0 && eingabegroesse.getText().length() == 0) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sie müssen die Bezeichnung des Teils und die Größe des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, null, eingabeteilenummer.getText(), null);
			}
		}
		einlagerndialog.dispose();
	}

	/**
	 * Löscht alle Komponenten des Frames und erzeugt die Startoberfläche
	 * 
	 * @param gui JFrame, auf diesem werden Komponenten entfertn/hinzugefügt
	 * @param leftpanel Panel mit Auslastung des Lagers
	 * @param rightpanel Panel mit Bild
	 * @param menupanel Panel mit Iconmenü
	 */
	public void startseite(LagerverwaltungGUI gui, JPanel leftpanel, JPanel rightpanel, JPanel menupanel) {
		for (Component c : gui.getContentPane().getComponents()) {
			gui.remove(c);
		}
		gui.add(leftpanel);
		gui.add(rightpanel);
		gui.add(menupanel);
		gui.repaint();
	}

	/**
	 * Erzeugt ein Dialogfenster, in dem man die Bezeichnung oder die Teilenummer eines Teils eingeben kann
	 * Ruft bei Bestätigung die Methode fachauslastungErgebnis auf
	 * 
	 * @see fachauslastungErgebnis
	 * 
	 * @param gui wird zur Übergabe für die Methode fachauslastungErgebnis benötigt
	 * @param rightpanel wird zur Übergabe für die Methode fachauslastungErgebnis benötigt
	 */
	public void fachauslastungDialog(LagerverwaltungGUI gui, CustomPanel rightpanel) {
		Dimension screensize = getScreensize();
		JDialog fachauslastungDialog = new JDialog();
		JPanel radioPanel = new JPanel();
		JLabel beschreibungLabel = new JLabel("Geben Sie die Bezeichnung oder die Teilenummer des Items an,"
				+ "\nfür dessen Fach sie die Auslastung sehen möchten.");
		JLabel wahlLabel = new JLabel("Wählen Sie aus, ob sie die Bezeichnung oder die Teilenummer angeben wollen:");
		JLabel artLabel = new JLabel("Bezeichnung des Teils:");
		JTextField eingabeTextfield = new JTextField(15);
		ButtonGroup btngroup = new ButtonGroup();
		JRadioButton btnbezeichnung = new JRadioButton("Bezeichnung");
		JRadioButton btnteilenummer = new JRadioButton("Teilenummer");
		JButton btnok = new JButton("Anzeigen");
		
		//bekommt das Standarddokument eines JTextfield
		Document standarddocument = eingabeTextfield.getDocument();
		
		//Ermöglicht nur eine Auswahl
		btngroup.add(btnteilenummer);
		btngroup.add(btnbezeichnung);
				
		//Hinzufügen der Componenten
		radioPanel.add(btnbezeichnung);
		radioPanel.add(btnteilenummer);	
				
		//Setzt den Focus in das Textfeld
		SwingUtilities.invokeLater( new Runnable() { 
			public void run() { 
		        eingabeTextfield.requestFocus(); 
		    } 
		} );
				
		fachauslastungDialog.setTitle("Fachauslastung anzeigen");
		fachauslastungDialog.setLayout(new GridBagLayout());
		
		//Hinzufügen der Komponenten
		fachauslastungDialog.add(beschreibungLabel, gbcErzeugen(0, 0, 1.0, 1.0, 2));
		
		fachauslastungDialog.add(wahlLabel, gbcErzeugen(0, 1, 1.0, 1.0, 1));
				
		fachauslastungDialog.add(radioPanel, gbcErzeugen(1, 1, 1.0, 1.0, 1));
				
		fachauslastungDialog.add(artLabel, gbcErzeugen(0, 2, 1.0, 1.0, 1));
				
		fachauslastungDialog.add(eingabeTextfield, gbcErzeugen(1, 2, 1.0, 1.0, 1));
				
		fachauslastungDialog.add(btnok, gbcErzeugen(1, 3, 1.0, 1.0, 1));
				
		//Legt Größe und Position des Frames fest
		fachauslastungDialog.setSize(screensize.width, screensize.height);
		fachauslastungDialog.setLocationRelativeTo(null);
		fachauslastungDialog.setVisible(true);
		
		btnbezeichnung.setSelected(true);
		
		//ActionListener
		btnbezeichnung.addActionListener(e -> radiobuttonAuswahlBezeichnung(artLabel, eingabeTextfield, standarddocument));
		btnteilenummer.addActionListener(e -> radiobuttonAuswahlTeilenummer(artLabel, eingabeTextfield));
		eingabeTextfield.addActionListener(e -> fachauslastungErgebnis(gui, fachauslastungDialog, rightpanel, eingabeTextfield));
		btnok.addActionListener(e -> fachauslastungErgebnis(gui, fachauslastungDialog, rightpanel, eingabeTextfield));
		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "beenden");
		btnok.getActionMap().put("beenden", new EscAction(fachauslastungDialog) );
	}

	/**
	 * Erzeugt die Ausschrift auf dem Panel der Startseite, welches vorher das Bild besitz
	 * Setzt dafür die benötigten Eigenschaften der Progressbar, der Labels und des Buttons
	 * Der Button Schließen ruft die Methode fachauswahlDialogschliessen auf
	 * 
	 * @see fachauswahlDialogschliessen
	 * @see pruefeString
	 * 
	 * @param gui wird zum Aufruf der Funktion pruefeString benötigt
	 * @param fachauslastungDialog wird zum Schließen des Dialogfensters benötig
	 * @param rightpanel wird zur Übergabe an die Methode fachauswahlDialogschliessen benötigt
	 * @param eingabeTextfield bringt Informationen über die Nutzereingabe
	 * 
	 * @return void
	 */
	private void fachauslastungErgebnis(LagerverwaltungGUI gui, JDialog fachauslastungDialog, CustomPanel rightpanel, JTextField eingabeTextfield) {
		int ergebnis = 0;
		Color CustomColor = new Color(80, 80, 80);
		Font font = new Font(Font.SANS_SERIF, Font.BOLD,  15);
		CustomProgressBar fachauslastungBar = new CustomProgressBar(10);
		JLabel ueberschriftLabel = new JLabel();
		JLabel fachauslastungLabel = new JLabel();
		JButton btnschliessen = new JButton("Schließen");
		
		for (Component c : rightpanel.getComponents()) {
			rightpanel.remove(c);
		}
		
		fachauslastungDialog.dispose();
				
		fachauslastungBar.setModel(new DefaultBoundedRangeModel(0, 0, 0, 10));
		fachauslastungBar.setUI(new CustomProgressBarUI());
		fachauslastungBar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		fachauslastungBar.setStringPainted(true);
		fachauslastungBar.setBorder(new EmptyBorder(8, 20, 8, 20));
		
		ueberschriftLabel.setFont(font);
		ueberschriftLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		ueberschriftLabel.setBorder(new EmptyBorder(0, 0, 15, 0));
		if(gui.pruefeString(eingabeTextfield.getText())) {
			ueberschriftLabel.setText("Die Auslastung des Fachs für das Teil mit der Teilenummer "
					+ eingabeTextfield.getText() +":");
			ergebnis = daten.getBelegterPlatz("", Integer.parseInt(eingabeTextfield.getText()));	
		}
		else {
			ueberschriftLabel.setText("Die Auslastung des Fachs für das Teil mit der Bezeichnung "
					+ eingabeTextfield.getText() +":");
			ergebnis = daten.getBelegterPlatz(eingabeTextfield.getText(), -1);
		}
		
		if(ergebnis > 0) {
			fachauslastungBar.setValue(ergebnis);
		}
		
		fachauslastungLabel.setText("Belegter Platz: \t"+ fachauslastungBar.getValue() + "/" + fachauslastungBar.getMaximum());
		fachauslastungLabel.setFont(font);
		fachauslastungLabel.setVerticalAlignment(SwingConstants.TOP);
		fachauslastungLabel.setBorder(new EmptyBorder(10, 0, 15, 0));
		
		rightpanel.setLayout(new GridBagLayout());
		rightpanel.setBackground(CustomColor);
		rightpanel.add(ueberschriftLabel, gbcErzeugen(0, 0, 0.0, 0.35, 2));
		rightpanel.add(fachauslastungLabel, gbcErzeugen(0, 1, 0.0, 0.0 , 2));
		rightpanel.add(fachauslastungBar, gbcErzeugen(0, 3, 0.0, 0.0, 2, GridBagConstraints.BOTH));
		rightpanel.add(btnschliessen, gbcErzeugen(0, 4, 0.0, 0.65, 2));
		
		rightpanel.setImage(null);
		
		//ActionListener
		btnschliessen.addActionListener(e -> fachauswahlDialogschliessen(rightpanel));
	}

	/**
	 * Entfernt alle Komponenten des Rightpanel und fügt das Bild wieder hinzu
	 * 
	 * @param rightpanel wird zum Hinzufügen des Bildes zum rightpanel benötigt
	 */
	private void fachauswahlDialogschliessen(CustomPanel rightpanel) {
		for (Component c : rightpanel.getComponents()) {
			rightpanel.remove(c);
		}
		
		Image image = null;
		try {
			image = ImageIO.read(new File("../Lagerverwaltung_AOPII/img/lager.jpg"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Beim Öffnen des Bildes für das rechte Panel kam es zu einem Fehler, "
					+ "gehen Sie sicher, dass sich das Bild lager.jpg im Ordner img unter Lagerverwaltung_AOPII befindet",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}
		rightpanel.setImage(image);
	}

	/**
	 * Erzeugt das richtige GridBagConstraints in Abhängigkeit von den übergebenen Parametern
	 * @param gridx setzt gridx von gbc auf den jeweiligen Wert
	 * @param gridy setzt gridy von gbc auf den jeweiligen Wert
	 * @param weightx setzt weightx von gbc auf den jeweiligen Wert
	 * @param weighty setzt weighty von gbc auf den jeweiligen Wert
	 * @param gridwidth setzt gridwidth von gbc auf den jeweiligen Wert
	 * 
	 * @return GridBagConstraints gbc: wird zur Positionierung im GridBagLayout benötigt
	 */
	public GridBagConstraints gbcErzeugen(int gridx, int gridy, double weightx, double weighty, int gridwidth) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.gridwidth = gridwidth;
		return gbc;
	}
	
	
	/**
	 * Erzeugt das richtige GridBagConstraints in Abhängigkeit von den übergebenen Parametern
	 * @param gridx setzt gridx von gbc auf den jeweiligen Wert
	 * @param gridy setzt gridy von gbc auf den jeweiligen Wert
	 * @param weightx setzt weightx von gbc auf den jeweiligen Wert
	 * @param weighty setzt weighty von gbc auf den jeweiligen Wert
	 * @param gridwidth setzt gridwidth von gbc auf den jeweiligen Wert
	 * @param fill	setzt fill von gbc auf den jewiligen Wert
	 * 
	 * @return GridBagConstraints gbc: wird zur Positionierung im GridBagLayout benötigt
	 */
	private GridBagConstraints gbcErzeugen(int gridx, int gridy, double weightx, double weighty, int gridwith, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.gridwidth = gridwith;
		gbc.fill = fill;
		return gbc;
	}

	/**
	 * Holt sich die Größe des Monitors vom System und erstellt dann eine neue Dimenstion
	 * 
	 * @return new Dimension:	Informationen über benötigte Breite und Höhe in Abhängigkeit
	 * 							von der Größe des Monitors
	 */
	public Dimension getScreensize() {
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
		return new Dimension(width, height);
	}

}