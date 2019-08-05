package lagerverwaltung;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Actionlistener {
	
	private LagerverwaltungGUI gui;

	public void oeffnen(LagerverwaltungGUI gui) {
		System.out.println("Öffnen-Button");
	}
	
	public void beenden(LagerverwaltungGUI gui) {
			int i = JOptionPane.showOptionDialog(null, "Wollen Sie das Porgramm wirklich beenden?", "Programm schließen?",
					 JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[] 
							 {"Ja", "Nein"}, "Ja");
			if(i == JOptionPane.YES_OPTION) {
				 //Hier kommt der Speicheralgorithmus
				 System.exit(0);
			 }
		}

	public void speichern(LagerverwaltungGUI gui) {
		System.out.println("Speichern-Button");
	}

	public void  schließen(LagerverwaltungGUI gui) {
		System.out.println();
	}


	public void anzeigenLagerinhalt(LagerverwaltungGUI gui) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.DARK_GRAY);
		gui.add(panel, BorderLayout.CENTER);
		gui.pack();
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
		tabelleErzeugen(gui,panel);	
	}

	private void tabelleErzeugen(LagerverwaltungGUI gui, JPanel panel) {		
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

		panel.add(new JScrollPane(tabelle), BorderLayout.CENTER);
		panel.repaint();
		
		sorting(tabelle, panel);
		
	}

	private void sorting(JTable tabelle, JPanel panel) {
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
			gui.remove(panel);
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
		System.out.println("Entnehmen-Button");
	}

	public void einlagern(LagerverwaltungGUI gui) {
		System.out.println("Einlagern-Button");
	}

}
