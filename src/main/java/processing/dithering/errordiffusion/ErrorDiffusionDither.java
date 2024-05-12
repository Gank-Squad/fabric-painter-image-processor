package processing.dithering.errordiffusion;

import java.awt.image.BufferedImage;

import processing.colors.Colors;

public class  ErrorDiffusionDither  {
	private static int[] RGBfromHex(int c) {
		int[] rgb = {0,0,0};
		
		rgb[0] = c >>> 16 & 255;
		rgb[1] = c >>> 8 & 255;
		rgb[2] = c & 255;
		
		return rgb;
	}
	private static int RGBtoHex(int[] rgb) {
		return (rgb[0] << 16) + (rgb[1] << 8) + rgb[2];
	}
	private static int[] getQuantizationError(int c1, int c2) {
		int[] rgb1 = RGBfromHex(c1);
		int[] rgb2 = RGBfromHex(c2);
		
		rgb1[0] = (rgb1[0] - rgb2[0]);
		rgb1[1] = (rgb1[1] - rgb2[1]);
		rgb1[2] = (rgb1[2] - rgb2[2]);
		
		return rgb1;
	}
	
	private static int clamp255(int n) {
		if (n < 0)
			return 0;
		if (n > 255)
			return 255;
		return n;
	}
	private static int clamp255(float n) {
		if (n < 0)
			return 0;
		if (n > 255)
			return 255;
		return (int) n;
	}
	
	private static int addQuantizationError(int c1, int[] qe, float coeff) {
		int[] rgb = RGBfromHex(c1);
		
		rgb[0] = clamp255(rgb[0] + qe[0] * coeff);
		rgb[1] = clamp255(rgb[1] + qe[1] * coeff);
		rgb[2] = clamp255(rgb[2] + qe[2] * coeff);
		
		return RGBtoHex(rgb);
	}
	private static int addQuantizationError(int c1, int[] qe, int shift) {
		int[] rgb = RGBfromHex(c1);
		
		rgb[0] = clamp255(rgb[0] + qe[0] >> shift);
		rgb[1] = clamp255(rgb[1] + qe[1] >> shift);
		rgb[2] = clamp255(rgb[2] + qe[2] >> shift);
		
		return RGBtoHex(rgb);
	}
	
	public static  void ditherImage(BufferedImage image, float[][] matrix) {
		if (image == null) {
			return;
		}
		
		final int height = image.getHeight();
		final int width = image.getWidth();
		final int mHeight = matrix.length;
		final int mWidth = matrix[0].length;
		
		int xoffset = mWidth / 2;
		for (int i = 0; i < mWidth; i++) {
			if (matrix[0][i] != 0) {
				xoffset = i;
				break;
			}
		}
		

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int oldPixel = image.getRGB(x, y);
				int newPixel = Colors.HexToColor(oldPixel).RGBAsHex();
				
				image.setRGB(x, y, newPixel);
				
				int[] quantErr = getQuantizationError(oldPixel,newPixel);
				
				for (int y1 = 0; y1 < mHeight; y1++) {
					for (int x1 = 0; x1 < mWidth; x1++) {
						int wx = x+x1-xoffset;
						
						if (matrix[y1][x1] == 0.0f)
							continue;
						if (wx >= width)
							break;
						if (wx < 0)
							continue;
						if (y + y1 >= height)
							break; // ideally exit out of both loops
						
						image.setRGB(wx, y+y1, 
								addQuantizationError(
										image.getRGB(wx, y+y1), 
										quantErr, 
										matrix[y1][x1]));
					}
				}
				
				
			}
		}
		
	}

	
	public static final float[][] AtkinsonMatrix = {
			{ 0,			0,			1.0f/8.0f,	1.0f/8.0f	},
			{ 1.0f/8.0f,	1.0f/8.0f,	1.0f/8.0f, 	0			},
			{ 0,			1.0f/8.0f,	0,			0			}
	};
	
	public static final float[][] FloydSteinbergMatrix = {
			{ 0,			0,			7.0f/16.0f },
			{ 3.0f/16.0f,	5.0f/16.0f,	1.0f/16.0f }
	};
	
	public static final float[][] Simple2D = {
			{ 0, 0.5f },
			{ 0.5f, 0 }
	};
	
	public static final float[][] JarvisJudiceNinke = {
			{ 0,			0,			0,			7.0f/48.0f,	5.0f/48.0f },
			{ 3.0f/48.0f,	5.0f/48.0f,	7.0f/48.0f,	5.0f/48.0f, 3.0f/48.0f },
			{ 1.0f/48.0f,	3.0f/48.0f,	5.0f/48.0f,	3.0f/48.0f,	1.0f/48.0f }
	};
	
	public static final float[][] Stucki = {
			{ 0,			0, 			0,			8.0f/42.0f,	4.0f/42.0f },
			{2.0f/42.0f,	4.0f/42.0f,	8.0f/42.0f,	4.0f/42.0f,	2.0f/42.0f },
			{1.0f/42.0f,	2.0f/42.0f,	4.0f/42.0f,	2.0f/42.0f,	1.0f/42.0f }
	};
	
	public static final float[][] Sierra3 = {
			{ 0,			0,			0,			5.0f/32.0f,	3.0f/32.0f },
			{ 2.0f/32.0f,	4.0f/32.0f,	5.0f/32.0f,	4.0f/32.0f,	2.0f/32.0f },
			{ 0,			2.0f/32.0f,	3.0f/32.0f,	2.0f/32.0f,	0		   }
	};
	
	public static final float[][] Sierra2 = {
			{ 0,			0,			0,			4.0f/16.0f,	3.0f/16.0f },
			{ 1.0f/16.0f,	2.0f/16.0f,	3.0f/16.0f,	2.0f/16.0f, 1.0f/16.0f }
	};
	
	public static final float[][] Burkes = {
			{ 0,			0,			0,			8.0f/32.0f,	4.0f/32.0f },
			{2.0f/32.0f,	4.0f/32.0f,	8.0f/32.0f,	4.0f/32.0f,	2.0f/32.0f }
	};
	
	
	
}
