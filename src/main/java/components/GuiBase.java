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
//			d = new DisplayImage(ImageIO.read(new File("E:\\Data\\512Laplus\\1ec1f3caa2edcebb419f237244ec0294c9ed1083ba01a3685ce55ae7a5b422cb.jpg")));
			d = new DisplayImage();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		d.setPreferredSize(new Dimension(500,500));
		d.setMaximumSize(new Dimension(500,500));
		
		return d;
	}
	
	public static JFileChooser createFileChooser()
	{
		JFileChooser fc = new JFileChooser();
		
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		return fc;
	}
}
