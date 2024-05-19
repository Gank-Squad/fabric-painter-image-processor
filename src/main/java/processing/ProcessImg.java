package processing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;
import org.im4java.core.Operation;
import org.im4java.core.Stream2BufferedImage;
import org.im4java.process.Pipe;

import gui.Base;
import processing.colors.Colors;

public class ProcessImg {
	
	public static boolean verifyImageMagick()
	{
		ImageCommand cmd = new ImageCommand();
		Operation op = new Operation();
		
		op.addRawArgs("magick", "--version");
		try
		{
			cmd.run(op);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	public static File saveImage(BufferedImage image)
	{
		
		try {
			File file = File.createTempFile("imageMagickTempInputFile", ".png");
			ImageIO.write(image, "png", file);
			
			return file;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	public static File getOutputFile()
	{
		try
		{
			File file = File.createTempFile("imageMagickTempOutputFile", ".png");
			return file;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public static File getPaletteImage()
	{
		
		File file = new File("paletteImage.png");
		
		if (file.exists())
			return file;
		
		System.out.println("palette file does not exist. creating");
		
		updatePaletteImage(Base.INSTANCE.displayImage.getPalette());
		
		return file;
	}
	public static BufferedImage createPaletteImage()
	{
		BufferedImage image = new BufferedImage(1,Colors.colorArr.length, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < Colors.colorArr.length; i++)
		{
//			image.setRGB(0, i, Colors.colorArr[i].RGBAsHex());
			image.setRGB(0, i, i);
		}
		
		return image;
	}
	public static void updatePaletteImage(BufferedImage palette) {
		File file = new File("paletteImage.png");
		try {
			ImageIO.write(Base.INSTANCE.displayImage.getPalette(), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *	Scale/Resize an image to match the given width/height as closely as possible 
	 * @param image		image to be scaled
	 * @param width		desired width of the scaled image
	 * @param height	desired height of the scaled image
	 * @param ignoreAspectRatio		whether the aspect ratio of the image should be maintained
	 * @return	scaled image
	 */
	public static BufferedImage scaleImage(BufferedImage image, int width, int height, boolean ignoreAspectRatio)
	{
		if (image == null)
			return null;
		
		ImageCommand cmd = new ImageCommand();
		IMOperation op = new IMOperation();
		
		op.addRawArgs("magick");
		
		try
		{
			Stream2BufferedImage s2b = new Stream2BufferedImage();
			cmd.setOutputConsumer(s2b);
			
			Pipe pipe = new Pipe(getInputStreamFromBufferedImage(image), null);
			cmd.setInputProvider(pipe);
			
			op.addImage("-");
			
			op.addRawArgs("-resize", String.format("%dx%d", width,height) + (ignoreAspectRatio ? "\\!" : ""));
			
			op.addImage("-");
			
			cmd.run(op);
			return s2b.getImage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * stretches the image by the given amounts. IGNORES ASPECT RATIO
	 * @param image
	 * @param xScale
	 * @param yScale
	 * @return
	 */
	public static BufferedImage scaleImage(BufferedImage image, double xScale, double yScale)
	{
		if (image == null)
			return null;
		
		ImageCommand cmd = new ImageCommand();
		IMOperation op = new IMOperation();
		
		try
		{
			op.addRawArgs("magick");
			
			Stream2BufferedImage s2b = new Stream2BufferedImage();
			cmd.setOutputConsumer(s2b);
			
			Pipe pipe = new Pipe(getInputStreamFromBufferedImage(image), null);
			cmd.setInputProvider(pipe);
			
			op.addImage("-");
			
			op.addRawArgs("-resize", 
					xScale*100 + "%x" + yScale*100 + "%!");
			// String.format("%d%x%d%!", xScale * 100, yScale * 100
			
			op.addImage("-");
			
			cmd.run(op);
			return s2b.getImage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Will match one dimension, and the other will be larger than desired.<br>
	 * I.e. if your image is 100x50, and you want it to be 200x150, it will scale to be 300x150
	 * @param image		image to be scaled
	 * @param width		desired width
	 * @param height	desired height
	 * @param crop		crop anything anything that would be outside the frame
	 * @return
	 */
	public static BufferedImage scaleImageExceed1Dimension(BufferedImage image, int width, int height, boolean crop)
	{
		if (image == null)
			return null;
		
		ImageCommand cmd = new ImageCommand();
		IMOperation op = new IMOperation();
		
		op.addRawArgs("magick");

		Stream2BufferedImage s2b = new Stream2BufferedImage();
		cmd.setOutputConsumer(s2b);
		
		Pipe pipe;
		try {
			pipe = new Pipe(getInputStreamFromBufferedImage(image), null);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		cmd.setInputProvider(pipe);
		
		double widthScaleFactor = (double)width / (double)image.getWidth();
		double heightScaleFactor = (double)height / (double)image.getHeight();
		
//		int scaledWidth = width;
//		int scaledHeight = height;
		
		op.addImage("-");
		
		// width will be the one to exceed the desired measure, height will match
		if (widthScaleFactor < heightScaleFactor)
		{
//			scaledWidth = (int) Math.round(width * heightScaleFactor);
			op.addRawArgs("-resize", String.format("x%d", height) /*+ "\\!"*/);
		}
		else
		{
//			scaledHeight = (int)Math.round(height * widthScaleFactor);
			op.addRawArgs("-resize", String.format("%dx", width) /*+ "\\!"*/);
		}
		
		
		// adding the \\! to make sure it matches properly, just in case the rounding/casting wouldn't match up properly w/ image magick
//		op.addRawArgs("-resize", String.format("%dx%d", scaledWidth, scaledHeight) /*+ "\\!"*/);
		
		op.addImage("-");
		
		try
		{
			cmd.run(op);
			return crop ? ProcessImg.cropImage(image, 0, 0, width, height) : s2b.getImage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Will match one dimension, and the other will be larger than desired.<br>
	 * I.e. if your image is 100x50, and you want it to be 200x150, it will scale to be 300x150.
	 * The scale will be applied after the initial scaling, and will the aspect ratio
	 * @param image		image to be scaled
	 * @param width		desired width
	 * @param height	desired height
	 * @param crop		crop anything anything that would be outside the frame
	 * @param xScale	
	 * @param yScale	
	 * @return
	 */
	public static BufferedImage scaleImageExceed1Dimension(BufferedImage image, int width, int height, double xScale, double yScale, boolean crop)
	{
		if (image == null)
			return null;
		
		ImageCommand cmd = new ImageCommand();
		IMOperation op = new IMOperation();
		
		op.addRawArgs("magick");

		
		Stream2BufferedImage s2b = new Stream2BufferedImage();
		cmd.setOutputConsumer(s2b);
		
		Pipe pipe;
		try {
			pipe = new Pipe(getInputStreamFromBufferedImage(image), null);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		cmd.setInputProvider(pipe);
		
		double widthScaleFactor = (double)width / (double)image.getWidth();
		double heightScaleFactor = (double)height / (double)image.getHeight();
		
		// probably not necessary, but should stop floating point imprecision from messing with the image
		if (Math.abs(xScale - 1) < 0.00001)
			xScale = 1;
		if (Math.abs(yScale - 1) < 0.00001)
			yScale = 1;
		
		op.addImage("-");
		
		// width will be the one to exceed the desired measure, height will match
		if (widthScaleFactor < heightScaleFactor)
		{
			// calculate width based on the height
			int newWidth = (int)Math.round((double)image.getWidth() / (double)image.getHeight() * (double)height * xScale);
			
			op.addRawArgs("-resize", String.format("%dx%d", newWidth, (int)Math.round((double)height * yScale)) + "\\!");
		}
		else
		{
			int newHeight = (int)Math.round((double)image.getHeight() / (double)image.getWidth() * (double)width * yScale);
			
			op.addRawArgs("-resize", String.format("%dx%d", (int)Math.round((double)width * xScale), newHeight) + "\\!");
		}
		
		op.addImage("-");
		
		try
		{
			cmd.run(op);
			return crop ? ProcessImg.cropImage(image, 0, 0, width, height) : s2b.getImage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Scales the given image by the given scale factor
	 * @param image		input image to be scaled
	 * @param scale 	% value, i.e. 1 = 100% = no change
	 * @return	scaled image
	 */
	public static BufferedImage scaleImage(BufferedImage image, double scale)
	{
		if (image == null)
			return null;
		
		ImageCommand cmd = new ImageCommand();
		IMOperation op = new IMOperation();
		
		try
		{
			op.addRawArgs("magick");
			
			Stream2BufferedImage s2b = new Stream2BufferedImage();
			cmd.setOutputConsumer(s2b);
			
			Pipe pipe = new Pipe(getInputStreamFromBufferedImage(image), null);
			cmd.setInputProvider(pipe);
			
			op.addImage("-");
			
			op.addRawArgs("-resize", Double.toString(scale) + "%");
			
			op.addImage("-");
			
			cmd.run(op);
//			BufferedImage output = ImageIO.read(outputImage);
			return s2b.getImage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
	
	
	/**
	 * 
	 * @param image	image to be cropped
	 * @param x		left edge of cropped image
	 * @param y		top edge of cropped image
	 * @param width	width of cropped image
	 * @param height	height of cropped image
	 * @return	cropped image
	 */
	public static BufferedImage cropImage(BufferedImage image, int x, int y, int width, int height)
	{
		if (image == null)
			return null;
		
		ImageCommand cmd = new ImageCommand();
		IMOperation op = new IMOperation();
		
		try
		{
			op.addRawArgs("magick", "convert");
			
			Stream2BufferedImage s2b = new Stream2BufferedImage();
			cmd.setOutputConsumer(s2b);
			
			Pipe pipe = new Pipe(getInputStreamFromBufferedImage(image), null);
			cmd.setInputProvider(pipe);
			
			op.addImage("-");
			
			op.addRawArgs("-crop", String.format("%dx%d+%d+%d", width,height,x,y));
			
			op.addImage("-");
			
			cmd.run(op);
			
			return s2b.getImage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static BufferedImage loadImage(File file)
	{	
		ImageCommand cmd = new ImageCommand();
		IMOperation op = new IMOperation();
	
		op.addRawArgs("magick", "convert");
		
		op.addImage(file.getAbsolutePath());
		op.addImage("bmp:-");
		
		Stream2BufferedImage s2b = new Stream2BufferedImage();
		cmd.setOutputConsumer(s2b);
		
		try
		{
			cmd.run(op);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return s2b.getImage();
	}
	
	public static InputStream getInputStreamFromBufferedImage(BufferedImage image) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image,  "png", os);
		return (InputStream)(new ByteArrayInputStream(os.toByteArray()));
	}
	
	/**
	 * modify the contrast and HSV values of an image. All inputs are percentage based. 
	 * @param image image to modify
	 * @param hue the new hue value, a value of 100 will make no changes
	 * @param saturation the new saturation value, a value of 100 will make no changes
	 * @param brightness the new brightness value, a value of 100 will make no changes
	 * @param contrast the new contrast value, a value of 0 will make no changes. Offsets black and white pixels equally, specify black offset.
	 * @return the modified image
	 */
	public static BufferedImage modifyContrastAndHSV(BufferedImage image, float hue, float saturation, float brightness, float contrast) {
		if (image == null)
			return null;
		
		ImageCommand cmd = new ImageCommand();
		IMOperation op = new IMOperation();
		
		try
		{
			op.addRawArgs("magick", "convert");
			
			Stream2BufferedImage s2b = new Stream2BufferedImage();
			cmd.setOutputConsumer(s2b);
			
			Pipe pipe = new Pipe(getInputStreamFromBufferedImage(image), null);
			cmd.setInputProvider(pipe);
			
			op.addImage("-");
			
			op.addRawArgs("-modulate", String.format("%f,%f,%f", brightness, saturation, hue));
			
			op.addRawArgs("-level", String.format("%f", contrast) + "%");
			
			op.addImage("-");
			
			cmd.run(op);
			
			return s2b.getImage();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
}
