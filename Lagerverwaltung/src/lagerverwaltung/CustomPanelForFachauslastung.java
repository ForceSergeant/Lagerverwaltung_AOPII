package lagerverwaltung;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class CustomPanelForFachauslastung extends JPanel {
	
	private JPanel panel;
	
	public CustomPanelForFachauslastung() {
		super();
	}
	
	void setPanel(JPanel panel) {
		this.panel = panel;
		validate();
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(panel != null) {
			Dimension size = this.getSize();
			g.drawRect(0, 0, size.width, size.height);
		}
	}
	
}
