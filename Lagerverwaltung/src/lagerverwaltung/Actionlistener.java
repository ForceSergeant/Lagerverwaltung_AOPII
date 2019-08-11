package lagerverwaltung;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
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
	 * 
	 * @return void
	 */
	public void oeffnen(LagerverwaltungGUI gui) throws IOException {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
		JFileChooser chooser = new JFileChooser();
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
				   	
					if(zeile != null && zeile.length() >0 && zeile != "") {
						String[] string = zeile.substring(0, zeile.length()-1).split(" ");
						for(int i = 0; i < string.length; i++ ) {
				   			System.out.println(string[i]);
				   			//TODO weiterverarbeitung
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
		}
		gui.aktualisierenProgressbar(daten.getOccupied(), daten.getfreieRegalfaecher());
	}
	
	/**
	 * Öfnnet einen FileChooser, mit dessen Hilfe man die Datei auswählen kann in die gespeichert werden soll
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
							writer.write(" ");
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
							e.printStackTrace();
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
		else {
			//TODO schließt automatisch das Programm, ändern?
		}
	}

	/**
	 * Beendet die Anwendung nachdem gefragt wurde, ob gespeichert werden soll
	 * 
	 * @see speichern
	 * 
	 * @return void
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
	 * 
	 * @return void
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
	 * 
	 * @return void
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
	 * 
	 * @return void
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
	 * 
	 * @return void
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
	 * 
	 * @return void
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
	 * 
	 * @return void
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
	 *
	 * @return void
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
	 * 
	 * @return void
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
		
		//Ermöglicht schließen durch drücken der Escape Tastste
		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "schließen");
		btnok.getActionMap().put("schließen", new EscAction(entnehmenDialog));
	    
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
	 * 
	 * @return void
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
	 * 
	 * @return void
	 */
	public void einlagern(LagerverwaltungGUI gui, String bezeichnung, String groesse) {
		Dimension screensize = getScreensize();
		JDialog einlagernDialog = new JDialog();
		JLabel bezeichnungLabel = new JLabel("Bezeichnung:");
		JLabel teilenummerLabel  = new JLabel("Teilenummer:");
		JLabel groessLabel = new JLabel("Größe des Teils in Grundeinheiten:");
		JTextField eingabebezeichnung = new JTextField(bezeichnung, 20);
		JTextField eingabeteilenummer = new JTextField(20);
		JTextField eingabegroesse = new JTextField(2);
		JButton btnok = new JButton("Ok");
		
		//Setzt den Focus in das Textfeld
		SwingUtilities.invokeLater( new Runnable() { 
			public void run() { 
				eingabebezeichnung.requestFocus(); 
			} 
		} );
		
		btnok.setBorder(new EmptyBorder(7, 20, 7, 20));
		eingabeteilenummer.setDocument(new AllowedDocument(9));
		eingabegroesse.setDocument(new AllowedDocument(2));
		eingabegroesse.setText(groesse);
		
		einlagernDialog.setTitle("Teil einlagern");
		einlagernDialog.setLayout(new GridBagLayout());
		
		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "beenden");
		btnok.getActionMap().put("beenden", new EscAction(einlagernDialog));
		

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
		btnok.addActionListener(e -> einlagernDatenuebergabe(einlagernDialog, eingabebezeichnung, eingabeteilenummer, eingabegroesse, gui));
		eingabegroesse.addActionListener(e -> einlagernDatenuebergabe(einlagernDialog, eingabebezeichnung, eingabeteilenummer, eingabegroesse, gui));
		
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
				einlagern(gui, eingabebezeichnung.getText(), null);
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
				einlagern(gui, eingabebezeichnung.getText(), null);
			}
			else if(eingabegroesse.getText().length() > 0 && eingabebezeichnung.getText().length() == 0) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sie müssen die Bezeichnung des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, null, eingabegroesse.getText());
			}
			else if(eingabebezeichnung.getText().length() == 0 && eingabegroesse.getText().length() == 0) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sie müssen die Bezeichnung des Teils und die Größe des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, null, null);
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
		gui.add(menupanel);
		gui.add(leftpanel);
		gui.add(rightpanel);
		gui.repaint();
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

