package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * 
 * class just exists to stop having so much code in one file.
 * all this does is draws the background for images in the DisplayImage class
 *
 */
public class DisplayBackground {
	public int cellSize = 10;
	public double cellScale = 1;
	private TexturePaint tileBrush;
	
	Color cellColor1 = new Color(0xAAAAAA);
    Color cellColor2 = new Color(0x777777);
	
    public DisplayBackground()
    {
    	initTileBrush();
    }
    
    /**
     * creates the tile brush used to draw the checkerboard pattern for the
     * background
     */
    protected void initTileBrush()
    {
        int width = (int) (cellSize * 2 * this.cellScale);
        int height = (int) (cellSize * 2 * this.cellScale);
        
        

        // optimized BufferedImage of type RGB
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = result.getGraphics();

        // draw smallest pattern
        // O X
        // X O

        // draw X
        g.setColor(this.cellColor1);
        g.fillRect(cellSize, 0, cellSize, cellSize);
        g.fillRect(0, cellSize, cellSize, cellSize);

        // draw O
        g.setColor(this.cellColor2);
        g.fillRect(0, 0, cellSize, cellSize);
        g.fillRect(cellSize, cellSize, cellSize, cellSize);

        g.dispose();

        // tile brush for optimized drawing of the entire control area with the small
        // graphic generated above
        Rectangle2D bounds = new Rectangle2D.Float(0, 0, result.getWidth(), result.getHeight());

        this.tileBrush = new TexturePaint(result, bounds);
    }

    
    /**
     * draws the checkerboard background pattern
     * 
     * @param g2
     * @param width
     * @param height
     */
    public void drawBackground(Graphics2D g, int width, int height)
    {
        g.setPaint(this.tileBrush);
        g.fillRect(0, 0, width, height);
    }
    
}
