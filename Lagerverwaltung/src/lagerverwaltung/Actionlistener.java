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

import javax.swing.Action;
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
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.Document;

public class Actionlistener {
	
	private LagerverwaltungGUI gui;
	private LagerverwaltungDaten daten = new LagerverwaltungDaten();
	
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
		
		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "beenden");
		btnok.getActionMap().put("beenden", new EscAction(entnehmendialog) );
	    
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
		eingabetxtfield.addActionListener(e -> entnehmenDatenuebergabe(btnbezeichnung, btnteilenummer, eingabetxtfield, entnehmendialog));
		btnok.addActionListener(e -> entnehmenDatenuebergabe(btnbezeichnung, btnteilenummer, eingabetxtfield, entnehmendialog));
	}

	//Ändert den Text des artlabel
	private void btnbezeichnung(JLabel artlabel, JTextField eingabetxtfield, Document standarddocument) {
			artlabel.setText("Bezeichnung des Teils:");
			eingabetxtfield.setDocument(standarddocument);
			eingabetxtfield.requestFocusInWindow(); 
		}
	
	//Ändert den Text des artlabel
	private void btnteilenummer(JLabel artlabel, JTextField eingabetxtfield) {
			eingabetxtfield.setDocument(new AllowedDocument(9));
			artlabel.setText("Teilenummer des Teils:");
			eingabetxtfield.requestFocusInWindow(); 
		}
	
	private void entnehmenDatenuebergabe(JRadioButton btnbezeichnung, JRadioButton btnteilenummer, JTextField eingabetxtfield, JDialog entnehmenpanel) {
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
			
			if(ergebnis[0] == 1) {
				if(btnbezeichnung.isSelected()) {
					gui.entnehmenErgebnisDialog(bezeichnung, ergebnis);
				}
				else if(btnteilenummer.isSelected()) {
					gui.entnehmenErgebnisDialog(teilenummer, ergebnis);
				}
			}
			else  {
				JOptionPane.showMessageDialog(null, "Das angegebene Teil konnte leider nicht gefunden werden."
						+ "\n Suchen Sie nach einem anderen Teil.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				entnehmen(gui);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Sie müssen etwas in das Textfeld eingeben",
					"Fehler", JOptionPane.ERROR_MESSAGE);
			entnehmen(gui);
		}
		entnehmenpanel.dispose();	
	}

	public void einlagern(LagerverwaltungGUI gui, String bezeichnung, String groesse) {
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
		JLabel bezeichnunglabel = new JLabel("Bezeichnung:");
		JLabel teilenummerlabel  = new JLabel("Teilenummer:");
		JLabel groesslabel = new JLabel("Größe des Teils in Grundeinheiten:");
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
		
		einlagerndialog.setTitle("Teil einlagern");
		einlagerndialog.setLayout(new GridBagLayout());
		
		btnok.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "beenden");
		btnok.getActionMap().put("beenden", new EscAction(einlagerndialog) );
		
		gbc.gridx = 0; //Spalte
		gbc.gridy = 0; //Zeile
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		einlagerndialog.add(bezeichnunglabel, gbc);
		
		gbc.gridx = 1;
		einlagerndialog.add(eingabebezeichnung, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		einlagerndialog.add(teilenummerlabel, gbc);
		
		gbc.gridx = 1;
		einlagerndialog.add(eingabeteilenummer, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		einlagerndialog.add(groesslabel, gbc);
		
		gbc.gridx = 1;
		einlagerndialog.add(eingabegroesse, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		einlagerndialog.add(btnok, gbc);
		
		einlagerndialog.setSize(width, height);
		einlagerndialog.setLocationRelativeTo(null);
		einlagerndialog.setVisible(true);
		
		//ActionListener
		btnok.addActionListener(e -> einlagernDatenuebergabe(einlagerndialog, eingabebezeichnung, eingabeteilenummer, eingabegroesse, gui));
		eingabegroesse.addActionListener(e -> einlagernDatenuebergabe(einlagerndialog, eingabebezeichnung, eingabeteilenummer, eingabegroesse, gui));
		
	}

	private void einlagernDatenuebergabe(JDialog einlagerndialog, JTextField eingabebezeichnung, JTextField eingabeteilenummer, JTextField eingabegroesse, LagerverwaltungGUI gui2) {
		
		int[] ergebnis = new int[4];
		
		if(eingabebezeichnung.getText().length() > 0 && eingabegroesse.getText().length() > 0) {
			int teilenummer = Integer.parseInt(eingabeteilenummer.getText());
			int groesse = Integer.parseInt(eingabegroesse.getText());
			ergebnis = daten.einlagern(eingabebezeichnung.getText(), teilenummer, groesse);
			
			/*
			 * erste nummer:
			 * 0 -> 
			 * 1 -> 
			 * 2 -> 
			 */
			
			switch(ergebnis[0]) {
			case 0:
				
				break;
			case 1: 
				
				break;
			case 2:
				
				break;
			default:
				JOptionPane.showMessageDialog(null, "Es ist ein unerwarteter Fehler aufgetreten.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		else {		
			if(eingabebezeichnung.getText().length() > 0 && eingabegroesse.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Sie müssen die Größe des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, eingabebezeichnung.getText(), null);
			}
			else if(eingabegroesse.getText().length() > 0 && eingabebezeichnung.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Sie müssen die Bezeichnung des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, null, eingabegroesse.getText());
			}
			else if(eingabebezeichnung.getText().length() == 0 && eingabegroesse.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "Sie müssen die Bezeichnung des Teils und die Größe des Teils angeben.",
						"Fehler", JOptionPane.ERROR_MESSAGE);
				einlagern(gui, null, null);
			}
		}
		einlagerndialog.dispose();
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
