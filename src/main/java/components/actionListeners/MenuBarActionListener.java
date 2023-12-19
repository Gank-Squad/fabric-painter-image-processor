package components.actionListeners;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import components.filters.ImageFilter;
import gui.Base;
import processing.ImageToInstructions;
import processing.ProcessImg;
import processing.dithering.DitherTypes;

public class MenuBarActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {

		if (!(e.getSource() instanceof JMenuItem))
			return;
		
		// File Menu
		if (Base.INSTANCE.loadImageMenuItem == e.getSource())
		{
			System.out.println("load image");
			
			FileDialog fd = new FileDialog(Base.INSTANCE, "Load Image");
			fd.setMode(FileDialog.LOAD);
			fd.setVisible(true);
			
			try {
				if (fd.getFile() != null)
				{
					Base.INSTANCE.displayImage.updateImage(ProcessImg.loadImage(new File(fd.getDirectory() + fd.getFile())));
				}
					
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
		else if (Base.INSTANCE.saveImageMenuItem == e.getSource())
		{
			int returnVal = Base.INSTANCE.fc.showSaveDialog(Base.INSTANCE);
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					File file = new File(Base.INSTANCE.fc.getSelectedFile().getAbsolutePath());
					
					// make sure it has the png file extension
					String ext = ImageFilter.getExtension(file); 
					if (ext == null || !ext.equals("png"))
						file = new File(Base.INSTANCE.fc.getSelectedFile().getAbsolutePath() + ".png");
					
					ImageIO.write(Base.INSTANCE.displayImage.getSelectionImage(), "png", file);
					
					File dir = new File(file.getParentFile().getAbsolutePath() + File.separator + file.getName() + 
							ImageToInstructions.instructionDirectoryAppension);
					System.out.println(dir.getAbsolutePath());
					ImageToInstructions.convertImageToInstructions(Base.INSTANCE.displayImage.getSelectionImage(), dir);
				}
				catch (Exception ex)
				{
					System.out.println("failed to save file: " + Base.INSTANCE.fc.getSelectedFile().getAbsoluteFile());
				}
			}
			
		}
		
		// Edit Menu
		else if (Base.INSTANCE.resetImageMenuItem == e.getSource())
		{		
			Base.INSTANCE.resetting = true;	
			
			Base.INSTANCE.xPanels.setValue(1);
			Base.INSTANCE.yPanels.setValue(1);
			Base.INSTANCE.xScale.setValue(100.0);
			Base.INSTANCE.yScale.setValue(100.0);
			Base.INSTANCE.displayImage.resetAllFields();
			
			Base.INSTANCE.resetting = false;
		}
		
			// dither submenu
		else if (Base.INSTANCE.floydSteinbergDitherMenuItem == e.getSource())
		{
			Base.INSTANCE.displayImage.setDitherType(DitherTypes.FloydSteinberg);
		}
		else if (Base.INSTANCE.riemersmaDitherMenuItem == e.getSource())
		{
			Base.INSTANCE.displayImage.setDitherType(DitherTypes.Riemersma);
		}
		else if (Base.INSTANCE.noDitherMenuItem == e.getSource())
		{
			Base.INSTANCE.displayImage.setDitherType(DitherTypes.None);
		}
		
		
		// View Menu
		else if (Base.INSTANCE.previewSelectionImageMenuItem == e.getSource())
		{
			if (Base.INSTANCE.displayImage.isDisplayingImage() && Base.INSTANCE.displayImage.getSelectionImage() != null)	
			{
				Base.INSTANCE.createImageFrame();
			}
		}
		
		
		// Help Menu
	}

}
