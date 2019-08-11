package lagerverwaltung;

import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Klasse die ein Document für ein Textfeld erzeugt und die insertString-Methode überschreibt
 * abgeleitet von der Klasse PlainDocument
 *
 */
public class AllowedDocument extends PlainDocument  {
	private int maxlenght;
	
	public AllowedDocument(int maxlenght) {
		this.maxlenght = maxlenght;
	}
	
	/**
	 * Versucht String in einen Integer umzuwandeln und falls es nicht klappt wird ein Fehler erzeugt
	 * Außerdem wird die Länge der Eingabe in Abhängigkeit von maxlenght, welches beim Aufruf der Klasse
	 * übergeben wird
	 * 
	 * @return void
	 */
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
		try{
            Integer.parseInt(str);
        }
        catch(Exception ex)
        {
        	Toolkit.getDefaultToolkit().beep();
        	JOptionPane.showMessageDialog(null, "Sie dürfen hier nur Zahlen eingeben!",
					"Fehler", JOptionPane.ERROR_MESSAGE);
           return;
        }
		
		if(str == null) {
			return;
		}
		
		int actualLength = this.getLength();
		if(actualLength + str.length() <= maxlenght) {
			super.insertString(offs, str, a);
		}
		else {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Sie dürfen nicht mehr als " + maxlenght + " Zahlen eingeben!",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}	
}
