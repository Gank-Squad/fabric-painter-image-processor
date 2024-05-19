package processing.colors.search;

import java.util.ArrayList;
import java.util.HashSet;

import processing.colors.Color;
import processing.colors.Colors;

/**
 *
 *	The purpose of this class is to generate color clusters, in order to make the searching of colors faster than a linear search.
 *	Only 255 colors total, so might be a bit tough to beat, but will see.
 *
 */
public class ClusterGenerator {
	
	public static ArrayList<Cluster> clusters;
	
	/**
	 * This uses euclidian distance between two rgb points. It calculates the square and square root, since speed in this situation matters less than accuracy
	 * @param r1 
	 * @param g1
	 * @param b1
	 * @param r2
	 * @param g2
	 * @param b2
	 * @return the euclidian distance between the rgb points
	 */
	public static double distance(double r1, double g1, double b1, double r2, double g2, double b2) {
		return Math.sqrt(
					Math.pow(r2-r1, 2) + 
					Math.pow(g2-g1, 2) + 
					Math.pow(b2-b1, 2)
				);
	}
	
	/**
	 * 
	 * @param r red value of point to be compared to
	 * @param g green value of point to be compared to
	 * @param b blue value of point to be compared to
	 * @param rgb array of rgb values, 
	 * @return
	 */
	public static double distance(int r, int g, int b, ArrayList<int[]> rgb) {
		double avg = 0;
		
		for (int[] i : rgb) {
			avg += Math.sqrt(
						Math.pow(r - i[0], 2) + 
						Math.pow(g - i[1], 2) +
						Math.pow(b - i[2], 2)
					);
		}
		
		return avg / rgb.size();
	}
	public static double distance(Color c, ArrayList<Color> rgb) {
		double avg = 0;
		
		for (Color i : rgb) {
			avg += Math.sqrt(
						Math.pow(c.r - i.r, 2) + 
						Math.pow(c.g - i.g, 2) +
						Math.pow(c.b - i.b, 2)
					);
		}
		
		return avg / rgb.size();
	}
	
	public static double distance(Color c, Cluster cluster) {
		double[] pos = cluster.getCentroid();
		return Math.sqrt(
				Math.pow(c.r - pos[0], 2) + 
				Math.pow(c.g - pos[1], 2) +
				Math.pow(c.b - pos[2], 2)
			);
	}
	
	public static double distance(Cluster c1, Cluster c2) {
		double[] pos1 = c1.getCentroid();
		double[] pos2 = c2.getCentroid();
		
		return Math.sqrt(Math.pow(pos1[0] - pos2[0], 2) + Math.pow(pos1[1] - pos2[1], 2) + Math.pow(pos1[2] - pos2[2], 2));
	}
	
	/**
	 * Finds the k furthest colors from c all all previously selected colors. Extremely inefficient algorithm
	 * @param k number of furthest colors returned
	 * @param c starting color/point
	 * @return
	 */
	private static ArrayList<Color> getFurthestColors(int k, Color c) {
		// going with the naive implementation, since I don't think it matters
		double max = 0;
		Color maxColor = null;
		ArrayList<Color> colors = new ArrayList<Color>(k+1);
		colors.add(c);
		// iterate through all colors, calculating the distance, once all colors are gone through, keep the one with the maximum distance, add to the array
		
		for (int i = 0; i < k; i++) {
			
			for (Color col : Colors.colorArr) {
				double dist = distance(col, colors);
				if (dist > max && !colors.contains(col)) {
					max = dist;
					maxColor = col;
				}
			}
			colors.add(maxColor);
			max = 0;
		}
		
		return colors;
	}
	
	/**
	 * Creates a list of clusters
	 * @param k the number of clusters 
	 * @return a list of clusters
	 */
	public static ArrayList<Cluster> generateClusters(int k) {
		
		ArrayList<Cluster> clusters = new ArrayList<Cluster>(k);
		
		// choose random starting color
//		ArrayList<Color> clustroids = getFurthestColors(k-1, Colors.colorArr[(int)(Math.random() * Colors.colorArr.length)]); 
		ArrayList<Color> clustroids = getFurthestColors(k-1, Colors.colorArr[3]); 
//		System.out.println(clustroids);
		for (Color c : clustroids) {
			clusters.add(new Cluster(c));
		}
		
		boolean exit = true;
		// create an arbitrary maximum limit on iterations in case the clusters never converge for whatever reason
		int maxout = 0;
		for (; maxout < 100 && exit; maxout++) {
			
			exit = false;
			double min = Double.MAX_VALUE;
			Cluster nearestCluster = null;
			
			for (Color c : Colors.colorArr) 
			{
				min = Double.MAX_VALUE;
				nearestCluster = null;
				for (Cluster cluster : clusters) 
				{
					double dist = distance(c, cluster);
					
					if (dist < min) {
						min = dist;
						nearestCluster = cluster;
					}
				}
				
				// remove colour from all other clusters
				for (Cluster cluster : clusters)
				{
					// make sure to not accidentally set the changed flag
					if (cluster != nearestCluster)
						cluster.removeFromCluster(c);
				}
				nearestCluster.addToCluster(c);
				
				
			}
			
			for (Cluster cluster : clusters)
			{
				cluster.updateCentroid();
				exit = exit || cluster.changesMade;
				cluster.changesMade = false;
			}
		}
		
//		System.out.println("made " + maxout + " iterations");
	
		return clusters;
	}
	
	
	public static double avgDistanceToCentroid(ArrayList<Cluster> clusters) {
		double interCluster = 0;
		double cluster = 0;
		
		for (Cluster c : clusters) {
			
			for (Color col : c.colors) {
				interCluster += distance(col, c);
			}
			interCluster /= c.colors.size();
			cluster += interCluster;
		}
		
		return cluster / clusters.size();
	}
	
}
