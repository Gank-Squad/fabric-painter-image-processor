package processing;

import java.awt.Point;
import java.awt.image.BufferedImage;

import components.DisplayImage;

public class ComponentLogicHelper {
	/**
	 * Gets the x offset bounds (minimum and maximum, for the selection box movement)
	 * @param displayImage DisplayImage component bounds will correspond to
	 * @return maximum values selection box can be before exiting image bounds
	 */
	public static Point getSelectionBoxMaxBounds(DisplayImage displayImage)
	{
		BufferedImage img = displayImage.getImage();	
		if (img == null) {
			System.out.println("Something went very wrong, and display image is null");
			return new Point(32,32);
		}
			
		return new Point(img.getWidth() - (displayImage.getXPanels() * 32), img.getHeight() - (displayImage.getYPanels() * 32));
	}
}
