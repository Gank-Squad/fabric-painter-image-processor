package debug;

import processing.colors.Color;
import processing.colors.Colors;

public class Debug {
	
	public static void printColorRGB() {
		for (Color c : Colors.colorArr) {
			System.out.println("{ " + c.r + ", " + c.g + ", " + c.b + " },");
		}
	}
}
