package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import processing.ComponentLogicHelper;
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
	
	private MouseListener mouseListener = new MouseListener();
	
	private final DisplayImage self = this; 
	
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
		
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
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
		
		verifyAndResetSelectionBoxBounds();
		
		repaint();
	}
	
	private void verifyAndResetSelectionBoxBounds()
	{
		Point p = ComponentLogicHelper.getSelectionBoxMaxBounds(this);
		if (offsetX > p.x || offsetY > p.y)
		{
			offsetX = 0;
			offsetY = 0;
		}
	}
	
	private Dimension calcDimension()
	{
		// for this just base it off of height
		
		// calc width based on max height
		Dimension d = new Dimension();
		d.height = this.getHeight();
		
		double hwRatio = displayImage ? (double)this.workingImage.getWidth() / (double)this.workingImage.getHeight() : 
			(double)(xPanels) / (double)(yPanels);
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
			
			verifyAndResetSelectionBoxBounds();
			
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
	
	/**
	 * 
	 * @param d the output from calcDimension, which I believe is the dimension of the working 
	 * image when scaled to fill the display area
	 * @return
	 */
	private Point getImagePos(Dimension d)
	{
		return new Point(
				(int)((double)this.getX() + (double)this.getWidth() / 2.0 - (double)d.width / 2.0),
				(int)((double)this.getY() + (double)this.getHeight() / 2.0 - (double)d.height / 2.0)
				);
	}
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        Graphics2D g2d = (Graphics2D) g;
        
        Dimension d = calcDimension();
        
//        int imageXPos = (int)((double)this.getX() + (double)this.getWidth() / 2.0 - (double)d.width / 2.0);
//      	int imageYPos = (int)((double)this.getY() + (double)this.getHeight() / 2.0 - (double)d.height / 2.0);
//      	
      	Point imagePos = getImagePos(d);
      	
      	bg.drawBackground(g2d, this.getWidth(), this.getHeight());
        
        if (this.displayImage)
	        g2d.drawImage(workingImage, imagePos.x, imagePos.y, d.width + imagePos.x, d.height + imagePos.y,
	        		0, 0, workingImage.getWidth(), workingImage.getHeight(),
	        		null);
        
        this.drawSelectionBox(g2d, imagePos.x, imagePos.y);

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
	
	public boolean isDisplayingImage() { return this.displayImage; }
	
	
	private class MouseListener extends MouseAdapter {
		
		// mouse move events appear to contain no info on what button is pressed, so keep track here
		boolean btn1Down = false;
		int mouseX = 0;
		int mouseY = 0;
		int xOff = 0;
		int yOff = 0;
		int prevModifier = 0;
		
		@Override
		public void mousePressed(MouseEvent e)
		{
			if (e.getButton() != MouseEvent.BUTTON1)
				return;
			
			System.out.println("mouse pressed");
			
			// check if mouse is inside bounds of selection box
			Point imagePos = getImagePos(calcDimension());
			int scaledX = convertScreenValToImageVal(e.getX());
			int scaledImageX = convertScreenValToImageVal(imagePos.x);
			if (!(scaledX <= (offsetX + xPanels * 32 + scaledImageX) && scaledX >= (offsetX + scaledImageX)))
				return;
				
			int scaledY = convertScreenValToImageVal(e.getY());
			int scaledImageY = convertScreenValToImageVal(imagePos.y);
			if (!(scaledY <= (offsetY + yPanels * 32 + scaledImageY) && scaledY >= offsetY + scaledImageY))
				return;
			
			btn1Down = true;
			mouseX = e.getX();
			mouseY = e.getY();
			xOff = offsetX;
			yOff = offsetY;
			prevModifier = e.getModifiersEx();
		}
		
		@Override
		public void mouseReleased(MouseEvent e)
		{
			if (e.getButton() != MouseEvent.BUTTON1)
				return;
			
			btn1Down = false;
		}
		
		/**
		 * converts a screen size relative value to an image size relative value, so 0->0, but 500 might ->128
		 * @param x screen pixel value
		 * @return x value relative to the working image size
		 */
		public int convertScreenValToImageVal(int x)
		{
			double componentFactor = Math.max(self.getWidth(), self.getHeight());
			double imageFactor = Math.max(self.workingImage.getWidth(), self.workingImage.getHeight());
			
			double scale = imageFactor / componentFactor;
			
			return (int)Math.round((double)x * scale);
		}
		
		
		
		@Override
		public void mouseDragged(MouseEvent e)
		{
			if (!btn1Down)
				return;
			
			// need to keep the current xoffset and yoffset in mind
			// then, when user clicks, take their current mouse pos, will act as anchor point
			// if they move their mouse x+=5, (so mouseX - e.getPox == 5), set xoffset = origxoffset + 5
			// need to cap it as well, so don't let xoffset exceed whatever the max at that point should be
			
			Point max = ComponentLogicHelper.getSelectionBoxMaxBounds(self);
			
			int convertedX = convertScreenValToImageVal(mouseX - e.getX());
			int convertedY = convertScreenValToImageVal(mouseY - e.getY());
			
			// will need to change this so that, once a modifier key is pressed
			// mouseX. mouseY, xOff, and yOff are all reset as if the mouse was clicked again
			// this will be to prevent pressing a modifier key from moving the selection box a ton
			if (prevModifier != e.getModifiersEx())
			{
				mouseX = e.getX();
				mouseY = e.getY();
				xOff = offsetX;
				yOff = offsetY;
				prevModifier = e.getModifiersEx();
			}
			
			if (e.getModifiersEx() == 1088)
			{
				convertedX *= 0.5;
				convertedY *= 0.5;
			}
			else if (e.getModifiersEx() == 1152)
			{
				convertedX *= 2;
				convertedY *= 2;
			}
			
			int xOffset = xOff - convertedX;
			int yOffset = yOff - convertedY;
			
			if (xOffset < 0)
				xOffset = 0;
			else if (xOffset > max.x)
				xOffset = max.x;
			
			if (yOffset < 0)
				yOffset = 0;
			else if (yOffset > max.y)
				yOffset = max.y;
			
			offsetX = xOffset;
			offsetY = yOffset;
			
			self.repaint();
		}
		
		
		@Override
		public void mouseMoved(MouseEvent e)
		{
//			System.out.println("" + e.getX() + ", " + e.getY());
//			System.out.println("" + e.getXOnScreen() + ", " + e.getYOnScreen());
//			System.out.println("----------");
		}
	}
}
