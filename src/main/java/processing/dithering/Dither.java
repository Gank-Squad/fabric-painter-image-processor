package processing.dithering;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.im4java.core.IMOperation;
import org.im4java.core.ImageCommand;
import org.im4java.core.Stream2BufferedImage;
import org.im4java.process.Pipe;

import processing.ProcessImg;
import processing.colors.Colors;
import processing.dithering.errordiffusion.ErrorDiffusionDither;

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
		
		ImageCommand cmd = new ImageCommand();
		IMOperation op = new IMOperation();
		
		op.addRawArgs("magick","convert");
		

		
		switch(type)
		{
		case None:
			op.dither("None");
			break;
		case FloydSteinberg:
			op.dither("FloydSteinberg");
			break;
		case Atkinson:
			ErrorDiffusionDither.ditherImage(input, ErrorDiffusionDither.AtkinsonMatrix);
			return input;
		case Riemersma:
			op.dither("Riemersma");
			break;
		case FloydSteinberg2:
			ErrorDiffusionDither.ditherImage(input, ErrorDiffusionDither.FloydSteinbergMatrix);
			return input;
		case Simple2D:
			ErrorDiffusionDither.ditherImage(input, ErrorDiffusionDither.Simple2D);
			return input;
		case JarvisJudiceNinke:
			ErrorDiffusionDither.ditherImage(input, ErrorDiffusionDither.JarvisJudiceNinke);
			return input;
		case Stucki:
			ErrorDiffusionDither.ditherImage(input, ErrorDiffusionDither.Stucki);
			return input;
		case Sierra3:
			ErrorDiffusionDither.ditherImage(input, ErrorDiffusionDither.Sierra3);
			return input;
		case Sierra2:
			ErrorDiffusionDither.ditherImage(input, ErrorDiffusionDither.Sierra2);
			return input;
		case Burkes:
			ErrorDiffusionDither.ditherImage(input, ErrorDiffusionDither.Burkes);
			return input;
		
		}
		
		try {
			// palette image
			op.remap(ProcessImg.getPaletteImage().getAbsolutePath());
			
			Stream2BufferedImage s2b = new Stream2BufferedImage();
			cmd.setOutputConsumer(s2b);
			
			Pipe pipe = new Pipe(ProcessImg.getInputStreamFromBufferedImage(input), null);
			cmd.setInputProvider(pipe);
			
			// input image
			op.addImage("-");
			
			// output image
			op.addImage("-");
			
			cmd.run(op);
			return s2b.getImage();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
		
		
		// input image
		
			// generate input image temp file
//		File inputImage = ProcessImg.saveImage(input);
//		op.addImage(inputImage.getAbsolutePath());
		
		
		// output image
			//generate output image temp file
//		File outputImage = ProcessImg.getOutputFile();
//		op.addImage(outputImage.getAbsolutePath());
		
//		BufferedImage output = input;
//		
//		try {
//			cmd.run(op);
//			output = ImageIO.read(outputImage);
//		} 
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			return null;
//		}
//		finally
//		{
//			inputImage.delete();
//			outputImage.delete();
//		}
//		
//		return output;
	}
	
	
	
}