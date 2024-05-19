package processing.colors.search;

import processing.colors.Color;

public class SearchClusters {
	
	public static Cluster[] clusters;
	
	private static double distance(double r1, double g1, double b1, double r2, double g2, double b2) {
		return Math.sqrt(
					Math.pow(r2-r1, 2) + 
					Math.pow(g2-g1, 2) + 
					Math.pow(b2-b1, 2)
				);
	}
	
	private static int[] RGBfromHex(int c) {
		int[] rgb = {0,0,0};
		
		rgb[0] = c >>> 16 & 255;
		rgb[1] = c >>> 8 & 255;
		rgb[2] = c & 255;
		
		return rgb;
	}
	
	public static Color findNearestNeighbor(int rgb, Cluster[] clusters) 
	{
		int[] rgb2 = RGBfromHex(rgb);
		return findNearestNeighbor(rgb2[0],rgb2[1],rgb2[2], clusters);
	}
	
	public static Color findNearestNeighbor(int r, int g, int b, Cluster[] clusters) 
	{
		double min = Double.MAX_VALUE;
		Cluster cluster = null;
		for (Cluster c : clusters) {
			double[] pos = c.getCentroid();
			double dist = distance(r,g,b, pos[0], pos[1], pos[2]);
			
			if (dist < min)
			{
				min = dist;
				cluster = c;
			}
		}
		
		Color color = null;
		min = Double.MAX_VALUE;
		
		for (Color c : cluster.colors)
		{
			double dist = Math.sqrt(
					Math.pow(Math.abs(c.r) - Math.abs(r), 2) +
					Math.pow(Math.abs(c.g) - Math.abs(g), 2) +
					Math.pow(Math.abs(c.b) - Math.abs(b), 2)
				);
				
			if (dist < min)
			{
				min = dist;
				color = c;
			}
		}
		
		return color;
	}
}
