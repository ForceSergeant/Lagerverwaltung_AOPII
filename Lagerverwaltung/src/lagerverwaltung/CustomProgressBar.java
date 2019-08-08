package lagerverwaltung;

import java.awt.Color;

import javax.swing.JProgressBar;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class CustomProgressBar extends JProgressBar {

	@Override
	public void setValue(int n)
	{
		super.setValue(n);
		if (n <30)
		{
			this.setForeground(Color.GREEN);
		}
		else if (n < 60)
		{
			this.setForeground(Color.YELLOW);
		}
		else
		{
			this.setForeground(Color.RED);
		}
	}
	
}
