package components.actionListeners;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gui.Base;
import gui.notification.Notification;
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
//			int returnVal = Base.INSTANCE.fc.showSaveDialog(Base.INSTANCE);
			
			FileDialog fd = new FileDialog(Base.INSTANCE, "Save Folder", FileDialog.SAVE);
			fd.setVisible(true);
			
			try
			{
				if (fd.getFile() == null)
					return;
				
				// create directory, save image in there, create csv files
				// if image name 
				
				// create directory
				File dir = new File(fd.getDirectory() + fd.getFile());
				dir.mkdir();
				
				// create image output file inside directory, replace "image" with original file name at some point
				// maybe cap length at something arbitrary like 16 char but probably wont bother with that
				// or if I do make it an option to disable/enable
				File file = new File(dir.getAbsolutePath() + File.separator + "image" + ImageToInstructions.outputImageAppension + ".png");
				
				// write output image to file
				ImageIO.write(Base.INSTANCE.displayImage.getSelectionImage(), "png", file);
				
				// write original image to file, will probably have an option to disable this in the future
				// but it just seems like a convenient thing to have
				// would probably be best to copy the original source, but I am currently planning
				// on implementing a feature that shrinks the original to decrease processing times
				// but this is better than nothing
				file = new File(dir.getAbsolutePath() + File.separator + "image" + ImageToInstructions.originalImageAppension + ".png");
				
				// write output image to file
				ImageIO.write(Base.INSTANCE.displayImage.getOriginalImage(), "png", file);
				
				ImageToInstructions.convertImageToInstructions(Base.INSTANCE.displayImage.getSelectionImage(), dir);
				
				System.out.println("Saving output to " + dir.getAbsolutePath());
			}
			catch (Exception ex)
			{
				System.out.println("failed to save file: " + fd.getFile());
				ex.printStackTrace();
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
		else if (Base.INSTANCE.atkinsonDitherMenuItem == e.getSource()) 
		{
			Base.INSTANCE.displayImage.setDitherType(DitherTypes.Atkinson);
		}
		
		// View Menu
		else if (Base.INSTANCE.previewSelectionImageMenuItem == e.getSource())
		{
			if (Base.INSTANCE.displayImage.isDisplayingImage() && Base.INSTANCE.displayImage.getSelectionImage() != null)	
			{
				Base.INSTANCE.createImageFrame();
			}
		}
		else if (Base.INSTANCE.toggleGridLinesMenuItem == e.getSource())
		{
			Base.INSTANCE.displayImage.setMinorGridLines(Base.INSTANCE.toggleGridLinesMenuItem.getState());
//			Base.INSTANCE.displayImage.setMinorGridLines(true);
		}
		
		
		// Help Menu
		else if (Base.INSTANCE.helpMenuItem == e.getSource())
		{
			JPanel panel = new JPanel();
			panel.setLayout(new BorderLayout());
			
			panel.add(new JLabel("test 123"), BorderLayout.CENTER);
			
			panel.setSize(250,250);
			
			Notification.createAlert("test123", "Help Window");
		}
	}

}
