package processing.colors.search;

import java.util.HashSet;
import java.util.Set;

import processing.colors.Color;

/**
 * 
 * A cluster will consist of an ArrayList of colours. It will additionally contain some basic logic, like a linear search of the
 * colors contained inside the arraylist, and will keep track of any changes
 *
 */
public class Cluster {
//	private ArrayList<Color> colors = new ArrayList<>();
	public Set<Color> colors = new HashSet<Color>();
	private double[] centroid = { 0.0, 0.0, 0.0 }; 
	public boolean changesMade = false;
	
	public Cluster(Color c) {
		colors.add(c);
		updateCentroid();
		changesMade = true;
	}
	public Cluster() {
		
	}
	
	public void addToCluster(Color c) {
		changesMade = colors.add(c) || changesMade;
	}
	public void removeFromCluster(Color c) {
		changesMade = colors.remove(c) || changesMade;
	}
	
	public double[] getCentroid() {
		return new double[] { centroid[0], centroid[1], centroid[2] };
	}
	
	public void updateCentroid() {
		int r = 0;
		int g = 0;
		int b = 0;
		for (Color c : colors) {
			r += c.r;
			g += c.g;
			b += c.b;
		}
		centroid[0] = (double)r / (double)colors.size();
		centroid[1] = (double)g / (double)colors.size();
		centroid[2] = (double)b / (double)colors.size();
	}
	
	@Override
	public String toString() {
		String s = "Centroid: " + centroid[0] + ", " + centroid[1] + ", " + centroid[2] + "\n";
		
		for (Color c : colors) {
			s += c.toString() +  ", ";
		}
		s += "\n";
		
		return s;
	}
}
