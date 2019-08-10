package lagerverwaltung;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class CustomPanel extends JPanel{

	   private Image image;
	   private boolean fitImage;

	   CustomPanel() {
	      super();
	   }

	   
	   /*
	    * Code von:
	    * https://wiki.byte-welt.net/wiki/Hintergrundbild_in_eine_GUI_einf%C3%BCgen_(Java)
	    */
	   void setImage(Image image) {
	      this.image = image;
	      this.fitImage = true;
	      validate();
	      repaint();
	   }

	   
	   @Override
	   protected void paintComponent(Graphics g) {
	      super.paintComponent(g);
	      if(image != null) {
	         if(fitImage) {
	            Dimension size = this.getSize();
	            g.drawImage(image, 0, 0, size.width, size.height, this);
	         } else {
	            g.drawImage(image, 0, 0, this);
	         }
	      }
	   }
}
