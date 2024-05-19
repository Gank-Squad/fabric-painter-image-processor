package processing.colors;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * This class solely exists to generate images that will be shipped with the final product, and realistically does not need to be part of the code base,
 * but if for any reason the images need to be regenerated it will be good to have them here
 *
 */
public class ColorIcons {
	
	// not gonna bother offering comprehensive customization given that this will never really need to be used
	public static void generateColorImage(processing.colors.Color c) {
		try {
			
			BufferedImage dye = ImageIO.read(new File("icons/dyes/" + c.name + ".png"));
			
			BufferedImage lightness;
			
			int cmax = Math.max(c.r, Math.max(c.g, c.b));
			
			if (cmax < 128)
				lightness = ImageIO.read(new File("icons/numbers/" + (c.lightness + 2) + "l.png"));
			else
				lightness = ImageIO.read(new File("icons/numbers/" + (c.lightness + 2) + ".png"));
			BufferedImage bg = generateSolidColorImage(c.r,c.g,c.b);
			
			Graphics g = bg.getGraphics();
			
			g.drawImage(dye, 0,0, null);
			g.drawImage(lightness, 0,0, null);
			
			g.dispose();
			
			ImageIO.write(bg,  "png", new File("icons/" + c.toString() + ".png"));
			
		} catch (IOException e) {
			
			e.printStackTrace();
			System.out.println("error occured with " + c.toString());
		}
	}
	
	public static void generateDyeImages() {
		for (processing.colors.Color c : Colors.colorArr) {
			generateColorImage(c);
		}
	}
	
	public static BufferedImage generateSolidColorImage(int r, int g, int b) {
		BufferedImage i = new BufferedImage(32,32, BufferedImage.TYPE_INT_ARGB);
		java.awt.Color c = new java.awt.Color(r,g,b, 255); 
		Graphics gra = i.getGraphics();
		
		gra.setColor(c);;
		
		gra.fillRect(0, 0, 32, 32);
		gra.dispose();
		
		return i;
	}
}
