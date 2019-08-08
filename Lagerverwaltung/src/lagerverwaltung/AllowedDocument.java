package lagerverwaltung;

import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class AllowedDocument extends PlainDocument  {
	private int maxlenght = 8;
	
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
			JOptionPane.showMessageDialog(null, "Sie dürfen nicht mehr als 8 Zeichen eingeben!",
					"Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}

	
}
