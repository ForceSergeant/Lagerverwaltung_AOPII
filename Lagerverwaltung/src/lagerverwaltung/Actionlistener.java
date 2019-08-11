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
	 * �ffnet einen FileChooser, mit dessen Hilfe man die Datei ausw�hlen kann die ge�ffnet werden soll
	 * Liest die Daten aus der Datei und �bergibt sie an die Klasse LagerverwaltungDaten
	 * @see LagerverwaltungDaten
	 * 
	 * @throws IOException f�ngt die Exception beim �ffnen der Datei ab
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
				//TODO �ffnenalgorithmus
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
				JOptionPane.showMessageDialog(null, "Sie k�nnen nur Textdateien (.txt) �ffnen.",
					"Fehler", JOptionPane.ERROR_MESSAGE);
				oeffnen(gui);
			}
		}
		gui.aktualisierenProgressbar(daten.getOccupied(), daten.getfreieRegalfaecher());
	}
	
	/**
	 * �fnnet einen FileChooser, mit dessen Hilfe man die Datei ausw�hlen kann in die gespeichert werden soll
	 * �berschreibt die jeweilige Datei mit den Daten
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
				JOptionPane.showMessageDialog(null, "Sie k�nnen nur in Textdateien (.txt) speichern.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				speichern();
			}
		}
		else {
			//TODO schlie�t automatisch das Programm, �ndern?
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
			int i = JOptionPane.showOptionDialog(null, "Wollen Sie das Programm wirklich beenden?", "Programm schlie�en?",
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
	 * @param gui ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * @param menupanel ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * @param leftpanel ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * @param rightpanel ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * 
	 * @return void
	 */
	public void anzeigenLagerinhalt(LagerverwaltungGUI gui, JPanel menupanel, JPanel leftpanel, JPanel rightpanel) {
		//Entfernt die Panels der Start�bersicht
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
	 * @param gui ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * @param menupanel ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * @param leftpanel ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * @param rightpanel ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * 
	 * @return void
	 */
	private void tabelleErzeugen(LagerverwaltungGUI gui, JPanel lagerpanel, JPanel menupanel, JPanel leftpanel, JPanel rightpanel) {		
		JTable tabelle = null;
		String[] theader = {"Bezeichnung", "Teilenummer", "Gr��e", "Anzahl", "Regalnummer", "Fachspalte", "Fachreihe" };
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
	 * �ffnet das Dialogfenster und fragt nach was sortiert werden soll,
	 * bei Abbruch kert der Benztzer zur Start�bersicht zur�ck
	 * 
	 * @see sortierenBezeichnung
	 * @see sortierenTeilenummer
	 * @see startseite
	 * 
	 * @param tabelle wird in den Methoden sortierenBezeichnung und sortierenTeilenummer ben�tigt
	 * @param gui ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * @param menupanel ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * @param leftpanel ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * @param rightpanel ben�tigt Methode startseite zum Zur�ckkehren auf die Startseite
	 * 
	 * @return void
	 */
	private void sortieren(JTable tabelle, LagerverwaltungGUI gui, JPanel lagerpanel, JPanel menupanel, JPanel leftpanel, JPanel rightpanel) {
		int i = JOptionPane.showOptionDialog(null,
				"Wonach m�chten Sie sortieren?", "Lagerinhalt anzeigen",
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
	 * �ndert den Text des artlabel, f�r den Fall dass der Radiobutton ge�ndert wird
	 * �ndert das Document des Labels und fokusiert es (setzt den Cursor in dieses)
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
	 * �ndert den Text des artlabel, f�r den Fall dass der Radiobutton ge�ndert wird
	 * �ndert das Document des Labels und fokusiert es (setzt den Cursor in dieses)
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
	 * �ffnet das Dialogfenster f�r die Entnahme eines Teils, setzt Eigenschaften der Komponenten
	 * und weist das Verhalten der Buttons zu (ActionListener)
	 * 
	 * @see entnehmenDatenuebergabe
	 * 
	 * @param gui wird f�r die Methode entnehmenDatenuebergabe ben�tigt
	 * 
	 * @return void
	 */
	public void entnehmen(LagerverwaltungGUI gui) {
		Dimension screensize = getScreensize();
		JDialog entnehmenDialog = new JDialog();
		JPanel radioPanel = new JPanel();
		JLabel wahlLabel = new JLabel("W�hlen Sie aus, ob sie die Bezeichnung oder die Teilenummer angeben wollen:");
		JLabel artLabel = new JLabel("Bezeichnung des Teils:");
		JTextField eingabeTextfield = new JTextField(15);
		ButtonGroup btngroup = new ButtonGroup();
		JRadioButton btnbezeichnung = new JRadioButton("Bezeichnung");
		JRadioButton btnteilenummer = new JRadioButton("Teilenummer");
		JButton btnok = new JButton("Entnehmen");
		
		//bekommt das Standarddokument eines JTextfield
		Document standarddocument = eingabeTextfield.getDocument();
		
		btnbezeichnung.setSelected(true);
		
		//Erm�glicht schlie�en durch dr�cken der Escape Tastste
		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "schlie�en");
		btnok.getActionMap().put("schlie�en", new EscAction(entnehmenDialog));
	    
	    //Erm�glicht nur eine Auswahl
		btngroup.add(btnteilenummer);
		btngroup.add(btnbezeichnung);
		
		//Hinzuf�gen der Componenten
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
		
		//Hinzuf�gen der Komponenten

		entnehmenDialog.add(wahlLabel, gbcErzeugen(0, 0, 1.0, 1.0, 1));
		
		entnehmenDialog.add(radioPanel, gbcErzeugen(1, 0, 1.0, 1.0, 1));
		
		entnehmenDialog.add(artLabel, gbcErzeugen(0, 1, 1.0, 1.0, 1));
		
		entnehmenDialog.add(eingabeTextfield, gbcErzeugen(1, 1, 1.0, 1.0, 1));
		
		entnehmenDialog.add(btnok, gbcErzeugen(1, 2, 1.0, 1.0, 1));
		
		//Legt Gr��e und Position des Frames fest
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
	 * �bergibt die eingegebenen Daten (Bezeichnung, Teilenummer und Gr��e) and LagerverwaltungDaten
	 * Ruft die Ergebnisdialoge auf durch die Methoden in LagerverwaltungGUI
	 * Ruft die Methode aktualiserenProgressbar auf
	 * 
	 * @see LagerverwaltungDaten
	 * @see LagerverwaltungGUI
	 * @see aktualisierenProgressbar
	 * 
	 * @param btnbezeichnung Radiobutton f�r die Bezeichnung
	 * @param btnteilenummer Radiobutton f�r die Teilenummer
	 * @param eingabetxtfield Eingabefeld mit Informationen �ber Bezeichnung/Teilenummer
	 * @param entnehmenDialog Dialogfenster welches sich �ffnet
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
			JOptionPane.showMessageDialog(null, "Sie m�ssen etwas in das Textfeld eingeben",
					"Fehler", JOptionPane.ERROR_MESSAGE);
			entnehmen(gui);
		}
		entnehmenDialog.dispose();	
	}

	/**
	 * �ffnet das Dialogfenster f�r das Einlagern eines Teils, setzt Eigenschaften seiner Komponenten, 
	 * und den ActionListener f�r die Buttons
	 * 
	 * @see einalgernDatenuebergabe
	 * 
	 * @param gui wird f�r die Methode einlagernDatenuebergabe ben�tigt
	 * @param bezeichnung wird f�r den Wiederaufruf bei falscher Eingabe ben�tigt, damit die Bezeichnung nicht erneut eingegeben werden muss
	 * @param groesse wird f�r den Wiederaufruf bei falscher Eingabe ben�tigt, damit die Gr��e nicht erneut eingebene werden muss
	 * 
	 * @return void
	 */
	public void einlagern(LagerverwaltungGUI gui, String bezeichnung, String groesse) {
		Dimension screensize = getScreensize();
		JDialog einlagernDialog = new JDialog();
		JLabel bezeichnungLabel = new JLabel("Bezeichnung:");
		JLabel teilenummerLabel  = new JLabel("Teilenummer:");
		JLabel groessLabel = new JLabel("Gr��e des Teils in Grundeinheiten:");
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
	 * �bergibt die Eingaben des Nutzers an LagerverwaltungDaten und gibt Auskunft dar�ber, ob das Einlagern erfolgreich war oder nicht
	 * F�ngt zus�tzlich Fehler bei der Eingabe ab
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
				JOptionPane.showMessageDialog(null, "Ein Fach hat die Gr��e von 10 Grundeinheiten, "
						+ "daher ist 10 die maximale Zahl die Sie hier eintragen k�nnen.",
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
				JOptionPane.showMessageDialog(null, "Sie m�ssen die Gr��e des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, eingabebezeichnung.getText(), null);
			}
			else if(eingabegroesse.getText().length() > 0 && eingabebezeichnung.getText().length() == 0) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sie m�ssen die Bezeichnung des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, null, eingabegroesse.getText());
			}
			else if(eingabebezeichnung.getText().length() == 0 && eingabegroesse.getText().length() == 0) {
				java.awt.Toolkit.getDefaultToolkit().beep();
				JOptionPane.showMessageDialog(null, "Sie m�ssen die Bezeichnung des Teils und die Gr��e des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, null, null);
			}
		}
		einlagerndialog.dispose();
	}

	/**
	 * L�scht alle Komponenten des Frames und erzeugt die Startoberfl�che
	 * 
	 * @param gui JFrame, auf diesem werden Komponenten entfertn/hinzugef�gt
	 * @param leftpanel Panel mit Auslastung des Lagers
	 * @param rightpanel Panel mit Bild
	 * @param menupanel Panel mit Iconmen�
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
	 * Erzeugt das richtige GridBagConstraints in Abh�ngigkeit von den �bergebenen Parametern
	 * @param gridx setzt gridx von gbc auf den jeweiligen Wert
	 * @param gridy setzt gridy von gbc auf den jeweiligen Wert
	 * @param weightx setzt weightx von gbc auf den jeweiligen Wert
	 * @param weighty setzt weighty von gbc auf den jeweiligen Wert
	 * @param gridwidth setzt gridwidth von gbc auf den jeweiligen Wert
	 * 
	 * @return GridBagConstraints gbc: wird zur Positionierung im GridBagLayout ben�tigt
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
	 * Holt sich die Gr��e des Monitors vom System und erstellt dann eine neue Dimenstion
	 * 
	 * @return new Dimension:	Informationen �ber ben�tigte Breite und H�he in Abh�ngigkeit
	 * 							von der Gr��e des Monitors
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

