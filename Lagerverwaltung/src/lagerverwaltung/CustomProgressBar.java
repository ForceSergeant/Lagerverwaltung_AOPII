package lagerverwaltung;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JProgressBar;

public class CustomProgressBar extends JProgressBar {
	
	private int maxvalue;
	
	public CustomProgressBar(int maxlength) {
		this.maxvalue = maxlength;
	}
	
	@Override
	public void setValue(int n)
	{
		super.setValue(n);
		if (n < maxvalue*0.33)
		{
			this.setForeground(Color.GREEN);
		}
		else if (n < maxvalue*0.66)
		{
			this.setForeground(Color.YELLOW);
		}
		else if (n < maxvalue*90) {
			this.setForeground(Color.ORANGE);
		}
		else {
			this.setForeground(Color.RED);
		}
		this.setBackground(Color.gray);
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));		
	}
}
