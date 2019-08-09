package lagerverwaltung;

import java.awt.Color;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class CustomProgressBarUI extends BasicProgressBarUI{

	protected Color getSelectionBackground() {
		return Color.BLACK;
	}
	
	protected Color getSelectionForeground() {
		return Color.BLACK;
	}
	  
}
