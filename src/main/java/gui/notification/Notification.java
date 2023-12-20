package gui.notification;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Notification {
	
	/**
	 * Create alert window containing the panel passed in, with default title of "Alert!", created at (0,0)
	 * @param panel the JPanel that will be displayed to the user, can insert whatever you want into it
	 */
	public static void createAlert(JPanel panel)
	{
		createAlert(panel, "Alert!", null);
	}
	
	/**
	 * Create alert window containing the panel passed in, with title specified, created at (0,0)
	 * @param panel the JPanel that will be displayed to the user, can insert whatever you want into it
	 * @param title the title of the window
	 */
	public static void createAlert(JPanel panel, String title)
	{
		createAlert(panel, title, null);
	}
	
	/**
	 * Create alert window containing the panel passed in, with title specified, at specified location
	 * @param panel the JPanel that will be displayed to the user, can insert whatever you want into it
	 * @param title the title of the window
	 * @param location the exact location (not relative) the notification window should be created
	 */
	public static void createAlert(JPanel panel, String title, Point location)
	{
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(panel);
		
		if (location != null)
			frame.setLocation(location.x, location.y);
		
		frame.setVisible(true);
	}
}
