package lagerverwaltung;

import java.awt.Color;

import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class CustomProgressBar extends JProgressBar {
	
	private int maxlength;
	
	public CustomProgressBar(int maxlength) {
		this.maxlength = maxlength;
	}

	@Override
	public void setValue(int n)
	{
		super.setValue(n);
		if (n < maxlength*0.33)
		{
			this.setForeground(Color.GREEN);
		}
		else if (n < maxlength*0.66)
		{
			this.setForeground(Color.YELLOW);
		}
		else if (n < maxlength*90) {
			this.setForeground(Color.ORANGE);
		}
		else
		{
			this.setForeground(Color.RED);
		}
	}
	
}
