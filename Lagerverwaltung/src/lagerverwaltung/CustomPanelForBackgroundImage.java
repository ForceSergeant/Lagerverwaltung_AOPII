package lagerverwaltung;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Klasse für ein eigenes JPanel
 * Abgeleitet von JPanel und überschreibt die Methode paintComponent
 *
 */
public class CustomPanelForBackgroundImage extends JPanel{

	   private Image image;

	  public CustomPanelForBackgroundImage() {
	      super();
	   }

	   
	   /*
	    * Code von:
	    * https://wiki.byte-welt.net/wiki/Hintergrundbild_in_eine_GUI_einf%C3%BCgen_(Java)
	    */
	   void setImage(Image image) {
	      this.image = image;
	      validate();
	      repaint();
	   }

	   
	   @Override
	   protected void paintComponent(Graphics g) {
		   super.paintComponent(g);
		   if(image != null) {
	            Dimension size = this.getSize();
	            g.drawImage(image, 0, 0, size.width, size.height, this);
         }
      }
	}