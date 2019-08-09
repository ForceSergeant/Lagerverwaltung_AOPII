package lagerverwaltung;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class CustomProgressBarUI extends BasicProgressBarUI{

	protected Color getSelectionBackground() {
		return Color.BLACK;
	}
	
	protected Color getSelectionForeground() {
		return Color.BLACK;
	}
	  
}
