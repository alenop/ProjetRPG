package elements_interactifs;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PNJ {

		public static class ConvertImageFile {

		   public static void main(String[]args) {

		    BufferedImage bufferedImage;
		    try {

		     //read image file
		    
		      bufferedImage = ImageIO.read(new File("c.pnj"));
		      BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
		            bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		      newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

		      ImageIO.write(newBufferedImage, "pnj", new File("pnj"));

		      System.out.println("Done");

		    } catch (IOException e) {

		      e.printStackTrace();

		    }

		   }
		}
}

		