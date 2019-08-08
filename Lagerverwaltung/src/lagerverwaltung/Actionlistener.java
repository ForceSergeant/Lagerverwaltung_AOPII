package lagerverwaltung;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Document;

public class Actionlistener {
	
	private LagerverwaltungGUI gui;
	private LagerverwaltungDaten daten;
	
	public void oeffnen() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(filter);
		chooser.setDialogTitle("Datei laden");
		chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		chooser.setSelectedFile(new File("Lagerverwaltung.txt"));
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\Save"));
		
		int result = chooser.showOpenDialog(null);
		
		if(result == JFileChooser.APPROVE_OPTION) {
			String dateipfad = chooser.getSelectedFile().toString();
			if(chooser.getFileFilter().accept(new File(dateipfad))) {
				//Öffnenalgorithmus			
			}
			else {
				JOptionPane.showMessageDialog(null, "Sie können nur Textdateien (.txt) öffnen, versuchen Sie es erneut");
				oeffnen();
			}
		}
	}
	
	public void beenden(LagerverwaltungGUI gui) {
			int i = JOptionPane.showOptionDialog(null, "Wollen Sie das Programm wirklich beenden?", "Programm schließen?",
					 JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] 
							 {"Ja", "Nein"}, "Ja");
			if(i == JOptionPane.YES_OPTION) {
				 speichern();
				 System.exit(0);
			 }
		}

	public void speichern() {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Textdatei", "txt");
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Speichern unter");
		chooser.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser.setSelectedFile(new File("Lagerverwaltung.txt"));
		chooser.setFileFilter(filter);
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir")+ "\\Save"));
		chooser.setVisible(true);

		int result = chooser.showSaveDialog(chooser);		
	
		if(result == JFileChooser.APPROVE_OPTION) {
			String dateipfad = chooser.getSelectedFile().toString();
			if(chooser.getFileFilter().accept(new File(dateipfad))) {
				//Speicheralgorithmus
			}
			else {
				JOptionPane.showMessageDialog(null, "Falscher Datentyp!\n Speichern Sie nur in Textdateien (txt).");
				speichern();
			}
		}
	}

	//TODO
	public void  schließen(LagerverwaltungGUI gui) {
		System.out.println();
	}


	public void anzeigenLagerinhalt(LagerverwaltungGUI gui, JPanel leftpanel, JPanel rightpanel, JPanel middlepanel) {
		//Entfernt die Panels der Startübersicht
		for (Component c : gui.getContentPane().getComponents()) {
			gui.remove(c);
		}
		JPanel lagerpanel = new JPanel();
		lagerpanel.setLayout(new BorderLayout());
		lagerpanel.setBackground(Color.DARK_GRAY);
		gui.add(lagerpanel, BorderLayout.CENTER);
		gui.pack();
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
		tabelleErzeugen(gui,lagerpanel, leftpanel, rightpanel, middlepanel);	
	}

	private void tabelleErzeugen(LagerverwaltungGUI gui, JPanel lagerpanel, JPanel leftpanel, JPanel rightpanel, JPanel middlepanel) {		
		//Tabelle
		JTable tabelle = null;
		String[] theader = {"Bezeichnung", "Teilenummer", "Größe", "Lagerort", "Anzahl"};
		String[][] inhalt = new String[50][5];
		
		//erzeugt nicht editierbare Tabelle
		tabelle = new JTable(new DefaultTableModel(inhalt,theader)) {
			public boolean isCellEditable(int x, int y) {
				return false;
			}		
		};
		
		tabelle.setDefaultRenderer(Object.class, new TableCellRenderer());

		lagerpanel.add(new JScrollPane(tabelle), BorderLayout.CENTER);
		lagerpanel.repaint();
		
		sorting(tabelle, gui, lagerpanel, leftpanel, rightpanel, middlepanel);	
	}

	private void sorting(JTable tabelle, LagerverwaltungGUI gui, JPanel lagerpanel, JPanel leftpanel, JPanel rightpanel, JPanel middlepanel) {
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
			gui.add(leftpanel);
			gui.add(middlepanel);
			gui.add(rightpanel);
			gui.remove(lagerpanel);
			gui.repaint();
		}
	}

	//sortiert Tabelle nach Bezeichnung
	private void sortierenBezeichnung(JTable tabelle) {
		DefaultTableModel model = (DefaultTableModel) tabelle.getModel();
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
	    tabelle.setRowSorter(sorter);
	    sorter.setModel(model);
	    tabelle.getRowSorter().toggleSortOrder(0);
	}

	//sortiert Tabelle nach Teilenummer
	private void sortierenTeilenummer(JTable tabelle) {
		DefaultTableModel model = (DefaultTableModel) tabelle.getModel();
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
	    tabelle.setRowSorter(sorter);
	    sorter.setModel(model);
	    tabelle.getRowSorter().toggleSortOrder(1);	
	}
	
	public void entnehmen(LagerverwaltungGUI gui) {
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
		JDialog entnehmendialog = new JDialog();
		JPanel radiopanel = new JPanel();
		JLabel wahllabel = new JLabel("Wählen Sie aus, ob sie die Bezeichnung oder die Teilenummer angeben wollen:");
		JLabel artlabel = new JLabel("Bezeichnung des Teils:");
		JTextField eingabetxtfield = new JTextField(15);
		ButtonGroup btngroup = new ButtonGroup();
		JRadioButton btnbezeichnung = new JRadioButton("Bezeichnung");
		JRadioButton btnteilenummer = new JRadioButton("Teilenummer");
		JButton btnok = new JButton("Entnehmen");
		
		//bekommt das Standarddokument eines JTextfield
		Document standarddocument = eingabetxtfield.getDocument();
		
		btnbezeichnung.setSelected(true);
	    
	    //Ermöglicht nur eine Auswahl
		btngroup.add(btnteilenummer);
		btngroup.add(btnbezeichnung);
		
		//Hinzufügen der Componenten
		radiopanel.add(btnbezeichnung);
		radiopanel.add(btnteilenummer);	
		
		//Setzt den Focus in das Textfeld
		SwingUtilities.invokeLater( new Runnable() { 
			public void run() { 
			        eingabetxtfield.requestFocus(); 
			    } 
			} );
		
		entnehmendialog.setTitle("Teil entnehmen");
		entnehmendialog.setLayout(new GridBagLayout());
		
		//Hinzufügen der Komponenten
		gbc.gridx = 0; //Spalte
		gbc.gridy = 0; //Zeile
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		entnehmendialog.add(wahllabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		entnehmendialog.add(radiopanel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		entnehmendialog.add(artlabel, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		entnehmendialog.add(eingabetxtfield, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		entnehmendialog.add(btnok, gbc);
		
		//Legt Größe und Position des Frames fest
		entnehmendialog.setSize(width, height);
		entnehmendialog.setLocationRelativeTo(null);
		entnehmendialog.setVisible(true);
		
		//Actionlistener
		btnbezeichnung.addActionListener(e -> btnbezeichnung(artlabel, eingabetxtfield, standarddocument));
		btnteilenummer.addActionListener(e -> btnteilenummer(artlabel, eingabetxtfield));
		eingabetxtfield.addActionListener(e -> btnannehmeneingabe(btnbezeichnung, btnteilenummer, eingabetxtfield, entnehmendialog));
		btnok.addActionListener(e -> btnannehmeneingabe(btnbezeichnung, btnteilenummer, eingabetxtfield, entnehmendialog));
	}

	//Ändert den Text des artlabel
	private void btnbezeichnung(JLabel artlabel, JTextField eingabetxtfield, Document standarddocument) {
			artlabel.setText("Bezeichnung des Teils:");
			eingabetxtfield.setDocument(standarddocument);
			eingabetxtfield.requestFocusInWindow(); 
		}
	
	//Ändert den Text des artlabel
	private void btnteilenummer(JLabel artlabel, JTextField eingabetxtfield) {
			eingabetxtfield.setDocument(new AllowedDocument());
			artlabel.setText("Teilenummer des Teils:");
			eingabetxtfield.requestFocusInWindow(); 
		}
	
	private void btnannehmeneingabe(JRadioButton btnbezeichnung, JRadioButton btnteilenummer, JTextField eingabetxtfield, JDialog entnehmenpanel) {
		String bezeichnung = "";
		String teilenummer = "";
		int teilenummerint = 0;
		//TODO alles in else in if und umgekehrt
		if(eingabetxtfield.getText().length() <= 0) {
			JOptionPane.showMessageDialog(null, "Sie müssen etwas in das Textfeld eingeben",
					"Fehler", JOptionPane.ERROR_MESSAGE);
			entnehmen(gui);
		}
		else {
			if(btnbezeichnung.isSelected()) {
				bezeichnung = eingabetxtfield.getText();
				teilenummerint = -1;
			}
			else if(btnteilenummer.isSelected()) {
				teilenummer = eingabetxtfield.getText();
				teilenummerint = Integer.parseInt(teilenummer);
				bezeichnung = "";
			}
			
			//TODO methode die true zurück liefert, wenn teil gefunden
			int[] ergebnis = daten.entnehmen(bezeichnung, teilenummerint);
			if(ergebnis[0] == 1) {
				if(btnbezeichnung.isSelected()) {
					oeffnenergebnisdialog(bezeichnung, ergebnis);
				}
				else if(btnteilenummer.isSelected()) {
					oeffnenergebnisdialog(teilenummer, ergebnis);
				}
			}
			else  {
				JOptionPane.showMessageDialog(null, "Das angegebene Teil konnte leider nicht gefunden werden."
						+ "\n Suchen Sie nach einem anderen Teil.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				entnehmen(gui);
			}
		}
		entnehmenpanel.dispose();	
	}

	private void oeffnenergebnisdialog(String teilenamen, int[] ergebnis) {
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
		btnok.addActionListener(e -> schlieseErgebnisDialog(ergebnisdialog));
		
	}

	private boolean pruefeString(String teilenamen) {
		boolean testbestanden;
		try {
			Integer.parseInt(teilenamen);
		    testbestanden = true;
		} catch(NumberFormatException e) {
		      testbestanden = false;
		   }
		   return testbestanden;
	}

	private void schlieseErgebnisDialog(JDialog ergebnisdialog) {
		ergebnisdialog.dispose();
	}

	public void einlagern(LagerverwaltungGUI gui) {
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
		JDialog einlagerndialog = new JDialog();
		JLabel bezeichnung = new JLabel("Bezeichnung");
		JLabel teilenummer = new JLabel("Teilenummer");
		JTextField eingabebezeichnung = new JTextField(20);
		JTextField eingabeteilenummer = new JTextField(20);
		
		//Setzt den Focus in das Textfeld
		SwingUtilities.invokeLater( new Runnable() { 
			public void run() { 
				eingabebezeichnung.requestFocus(); 
			} 
		} );
		
		einlagerndialog.setTitle("Teil einlagern");
		einlagerndialog.setLayout(new GridBagLayout());
		
		gbc.gridx = 0; //Spalte
		gbc.gridy = 0; //Zeile
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		einlagerndialog.add(bezeichnung, gbc);
		
		gbc.gridx = 1;
		einlagerndialog.add(eingabebezeichnung, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		einlagerndialog.add(teilenummer, gbc);
		
		gbc.gridx = 1;
		einlagerndialog.add(eingabeteilenummer, gbc);
		
		
		
		einlagerndialog.setSize(width, height);
		einlagerndialog.setLocationRelativeTo(null);
		einlagerndialog.setVisible(true);
		
		
	}

	public void zurueck() {
		
	}

	public void startseite(LagerverwaltungGUI gui, JPanel leftpanel, JPanel rightpanel, JPanel middlepanel) {
		for (Component c : gui.getContentPane().getComponents()) {
			gui.remove(c);
		}
		gui.add(leftpanel);
		gui.add(middlepanel);
		gui.add(rightpanel);
		gui.repaint();
	}

}
