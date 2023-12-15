package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import processing.ProcessImg;
import processing.dithering.Dither;
import processing.dithering.DitherTypes;

public class DisplayImage extends JComponent
{
	public static final int canvasSize = 32;
	
	// panels = individual maps/canvases in game, each is 32x32px
	private int xPanels = 1;
	private int yPanels = 1;
	
	private double scaleX = 1;
	private double scaleY = 1;
	
	private int offsetX = 0;
	private int offsetY = 0;
	
	private boolean displayImage = true;
	private boolean imageChanged = true;
	private boolean scaleChanged = true;
	
	private DitherTypes ditherType = DitherTypes.FloydSteinberg;
	private DitherTypes prevDitherType = ditherType;
	
	private static final String uiClassID = "DisplayImage";
	
	private BufferedImage originalImage;
	private BufferedImage workingImage;
	
	private DisplayBackground bg;
	
	public DisplayImage()
	{
		this(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
		this.displayImage = false;
	}
	public DisplayImage(BufferedImage image)
	{
		this.updateImage(image);
		this.displayImage = true;
		this.bg = new DisplayBackground();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500,500);
	}
	
	public void updateImage(BufferedImage image)
	{
		if (image == null)
		{
			System.out.println("image was null");
			return;
		}
		
		this.originalImage = image;
		this.displayImage = true;
		this.imageChanged = true;
		processWorkingImage();
		repaint();
	}
	 
	private Dimension calcDimension()
	{
		// for this just base it off of height
		
		// calc width based on max height
		Dimension d = new Dimension();
		d.height = this.getHeight();
		
		double hwRatio = displayImage ? (double)this.workingImage.getWidth() / (double)this.workingImage.getHeight() : 
			(double)(xPanels * 32) / (double)(yPanels * 32);
		d.width = (int)Math.round((double)this.getHeight() * hwRatio);
		
		if (d.width > this.getWidth())
		{
			// calc height, width should be component width
			d.width = this.getWidth();
			d.height = (int)Math.round((double)this.getWidth() / hwRatio);
		}
		
		return d;
	}
	
	private void processWorkingImage()
	{
		if (!displayImage)
			return;
		
		if (ditherType != prevDitherType || imageChanged || scaleChanged || checkIfSizeChanged())
		{
			imageChanged = false;
			scaleChanged = false;
			prevDitherType = ditherType;
			
			// resize image
			System.out.println("resizing");
			this.workingImage = ProcessImg.scaleImageExceed1Dimension(this.originalImage, 
					(int)Math.round((double)this.xPanels * 32.0), 
					(int)Math.round((double)this.yPanels * 32.0),
					this.scaleX, this.scaleY, false);
			
			// dither image
			System.out.println("dithering");
			this.workingImage = Dither.ditherImage(this.workingImage, ditherType);
			System.out.println();
		}
		
	}
	
	private boolean checkIfSizeChanged()
	{
		if (this.workingImage == null)
			return true;
		
		// check if it exceeds, and check that at least one dimension matches
		boolean exceedsDimensions = this.xPanels * 32 > this.workingImage.getWidth() || this.yPanels * 32 > this.workingImage.getHeight(); 
		boolean matchesDimensions = this.xPanels * 32 == this.workingImage.getWidth() || this.yPanels * 32 == this.workingImage.getHeight();
		
		return exceedsDimensions || !matchesDimensions;

	}
	
	
	private void drawSelectionBox(Graphics2D g, int imageXPos, int imageYPos)
	{
		g.setColor(new Color(255,0,0,255));
		
		// scale factor should be 1px from source = n.n px on displayed
		Dimension d = calcDimension();
		
		double xScale = displayImage ? (double)d.width / (double)this.workingImage.getWidth() : (double)d.width / (double)(xPanels * 32);
		double yScale = displayImage ? (double)d.height / (double)this.workingImage.getHeight() : (double)d.height / (double)(yPanels * 32);
		
		g.drawRect(this.getX() + (int)Math.round((double)this.offsetX * xScale) + imageXPos,
				this.getY() + (int)Math.round((double)this.offsetY * yScale) + imageYPos,
				(int)Math.round(32 * this.xPanels * xScale) - 1, (int)Math.round(32 * this.yPanels * yScale) - 1);
	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        Graphics2D g2d = (Graphics2D) g;
        
        Dimension d = calcDimension();
        
        int imageXPos = (int)((double)this.getX() + (double)this.getWidth() / 2.0 - (double)d.width / 2.0);
      	int imageYPos = (int)((double)this.getY() + (double)this.getHeight() / 2.0 - (double)d.height / 2.0);
      	
      	bg.drawBackground(g2d, this.getWidth(), this.getHeight());

//        if (this.displayImage)
//        g2d.drawImage(workingImage, imageXPos, imageYPos, this.calcWidth(this.getWidth()) + imageXPos, this.calcHeight(this.getHeight()) + imageYPos,
//        		0, 0, workingImage.getWidth(), workingImage.getHeight(),
//        		null);
        
        
        if (this.displayImage)
	        g2d.drawImage(workingImage, imageXPos, imageYPos, d.width + imageXPos, d.height + imageYPos,
	        		0, 0, workingImage.getWidth(), workingImage.getHeight(),
	        		null);
        
        this.drawSelectionBox(g2d, imageXPos, imageYPos);

    }
	
	public void setPanels(int xPanels, int yPanels)
	{
		this.xPanels = xPanels;
		this.yPanels = yPanels;
		this.processWorkingImage();
		this.repaint();
	}
	public int getXPanels() { return this.xPanels; }
	public int getYPanels() { return this.yPanels; }
	
	public void setOffset(int xOffset, int yOffset)
	{
		this.offsetX = xOffset;
		this.offsetY = yOffset;
		this.repaint();
	}
	public int getXOffset() { return this.offsetX; }
	public int getYOffset() { return this.offsetY; }
	
	public void setDitherType(DitherTypes ditherType)
	{
		if (ditherType == this.ditherType)
			return;
		this.prevDitherType = this.ditherType;
		this.ditherType = ditherType;
		this.processWorkingImage();
		this.repaint();
	}
	public DitherTypes getDitherType() { return this.ditherType; }
	
	/**
	 * params should be given in decimal, not % form (1 = 100%, 0.1 = 10%)
	 * @param xScale decimal scale factor in x direction
	 * @param yScale decimal scale factor in y direction
	 */
	public void setScale(double xScale, double yScale)
	{
		this.scaleChanged = xScale != this.scaleX || yScale != this.scaleY;
		this.scaleX = xScale;
		this.scaleY = yScale;
		this.processWorkingImage();
		this.repaint();
	}
	public double getScaleX() { return this.scaleX; }
	public double getScaleY() { return this.scaleY; }
	
	public BufferedImage getImage()
	{
		return this.workingImage;
	}
	public BufferedImage getSelectionImage()
	{
		return ProcessImg.cropImage(this.workingImage, this.offsetX, this.offsetY,
				(int)Math.round(32 * this.xPanels), (int)Math.round(32 * this.yPanels));
	}
	
	public void resetAllFields()
	{
		xPanels = 1;
		yPanels = 1;
		
		scaleX = 1;
		scaleY = 1;
		
		offsetX = 0;
		offsetY = 0;
		
		processWorkingImage();
		
		repaint();
	}

}
