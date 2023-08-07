package processing.colors;

/**
 * 
 * invididual color, contains Name, RGB, and lightness
 *
 */
public class Color 
{
	public final int r;
	public final int g;
	public final int b;
	public final String name;
//	public final Item item;
	public final int lightness;
	
	Color (String name, int lightness, int r, int g, int b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.name = name;
//		this.item = item;
		this.lightness = lightness;
	}
	
	Color(String name, int lightness, int hex)
	{
		this.r = (hex & 0x00FF0000) >> 16;
		this.g = (hex & 0x0000FF00) >> 8;
		this.b = hex & 0x000000FF;
		this.name = name;
//		this.item = item;
		this.lightness = lightness;
	}
	
	public int RGBAsHex()
	{
		int i = r;
		i = i << 8;
		i = i | g;
		i = i << 8;
		i = i | b;
		return i;
	}
	
	public String toString()
	{
		return this.name + (this.lightness + 2);
	}
}
