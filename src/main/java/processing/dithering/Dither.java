package processing.dithering;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;

import processing.ProcessImg;
import processing.colors.Colors;

public class Dither {
	
	static BufferedImage paletteImage = null;
	
	private static void generatePaletteImage()
	{
		paletteImage = new BufferedImage(1, Colors.colorArr.length, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < Colors.colorArr.length; i++)
		{
			paletteImage.setRGB(0, i, Colors.colorArr[i].RGBAsHex());
		}
		


		File outputfile = new File("colorPalette.png");
		try {
			ImageIO.write(paletteImage, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public static BufferedImage ditherImage(BufferedImage input, DitherTypes type)
	{
		if (input == null)
			return null;
		
//		ConvertCmd cmd = new ConvertCmd();
		ImageCommand cmd = new ImageCommand();
		IMOperation op = new IMOperation();
		
		op.addRawArgs("magick","convert");
		
//		op.dither("FloydSteinberg");
		
		
		switch(type)
		{
		case None:
			op.dither("None");
			break;
		case FloydSteinberg:
			op.dither("FloydSteinberg");
			break;
		case Riemersma:
			op.dither("Riemersma");
			break;
		default:
			return input;
		}
		
		
		// palette image
		op.remap(ProcessImg.getPaletteImage().getAbsolutePath());
		
		// input image
		
			// generate input image temp file
		File inputImage = ProcessImg.saveImage(input);
		op.addImage(inputImage.getAbsolutePath());
		
		
		// output image
			//generate output image temp file
		File outputImage = ProcessImg.getOutputFile();
		op.addImage(outputImage.getAbsolutePath());
		
		BufferedImage output = input;
		
		try {
			cmd.run(op);
			output = ImageIO.read(outputImage);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			inputImage.delete();
			outputImage.delete();
		}
		
		return output;
	}
}
