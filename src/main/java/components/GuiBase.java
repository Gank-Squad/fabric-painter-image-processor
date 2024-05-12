package components;

import java.awt.Dimension;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;


/**
 * 
 * This class just exists to split the gui stuff into multiple files so its more readable
 *
 */
public class GuiBase {
	
	public static DisplayImage createDisplayImage()
	{
		DisplayImage d = null;
		try
		{
			d = new DisplayImage();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return d;
	}
	
	public static JFileChooser createFileChooser()
	{
		JFileChooser fc = new JFileChooser();
		
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		return fc;
	}
}
