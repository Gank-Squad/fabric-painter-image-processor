package components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class PreviewImage  extends JComponent {
	
	BufferedImage image;
	public PreviewImage()
	{
		this.image = null;
	}
	public PreviewImage(BufferedImage image)
	{
		this.image = image;
	}
	
	private Dimension calcDimension()
	{
		// for this just base it off of height
		
		// calc width based on max height
		Dimension d = new Dimension();
		d.height = this.getHeight();
		
		double hwRatio = (double)this.image.getWidth() / (double)this.image.getHeight();
		d.width = (int)Math.round((double)this.getHeight() * hwRatio);
		
		if (d.width > this.getWidth())
		{
			// calc height, width should be component width
			d.width = this.getWidth();
			d.height = (int)Math.round((double)this.getWidth() / hwRatio);
		}
		
		return d;
	}

	/**
	 * returns the dimension the window should be set to to properly contain the image,
	 * given the specified sideLength
	 * @param sideLength
	 * @return Dimension that has the proper width and height
	 */
	public Dimension getDisplayValue(int sideLength)
	{
		Dimension d = new Dimension();
		d.height = sideLength;
		
		double hwRatio = (double)this.image.getWidth() / (double)this.image.getHeight();
		d.width = (int)Math.round((double)d.height * hwRatio);
		
		if (d.width > sideLength)
		{
			// calc height, width should be component width
			d.width = this.getWidth();
			d.height = (int)Math.round((double)d.width / hwRatio);
		}
		System.out.println(d.width + " -- " + d.height);
		return d;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		if (this.image == null)
			return;
		
		Graphics2D g2d = (Graphics2D)g;
		
		Dimension d = calcDimension();
		g2d.drawImage(this.image, this.getX(), this.getY(), this.getX() + d.width, this.getY() + d.height,
				0, 0, this.image.getWidth(), this.image.getHeight(), null);
		
	}
	
	

}
