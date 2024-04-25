package gui.notification;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.Base;

public class Notification {
	
	/**
	 * Create alert window containing the panel passed in, with default title of "Alert!", created at (0,0)
	 * @param panel the JPanel that will be displayed to the user, can insert whatever you want into it
	 */
	public static void createAlert(JPanel panel)
	{
		createAlert(panel, "Alert!", null, null);
	}
	
	/**
	 * Create alert window containing the panel passed in, with title specified, created at (0,0)
	 * @param panel the JPanel that will be displayed to the user, can insert whatever you want into it
	 * @param title the title of the window
	 */
	public static void createAlert(JPanel panel, String title)
	{
		createAlert(panel, title, null, null);
	}
	
	/**
	 * Create alert window containing the panel passed in, with title specified, at specified location
	 * @param panel the JPanel that will be displayed to the user, can insert whatever you want into it
	 * @param title the title of the window
	 * @param location the exact location (not relative) the notification window should be created, may be {@code null}
	 * @param dim the dimension of the alert window, (250,250) by default, may be {@code null}
	 */
	public static void createAlert(JPanel panel, String title, Point location, Dimension dim)
	{
		if (panel == null)
			return;
		if (title == null)
			title = "Alert!";
		if (dim == null)
			dim = new Dimension(250,250);
		
		JDialog frame = new JDialog(Base.getFrame(), title);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(panel, BorderLayout.NORTH);
		
		if (location != null)
			frame.setLocation(location.x, location.y);
		
		frame.setSize(dim.width, dim.height);
		
		frame.setVisible(true);
	}
	
	public static void createAlert(String message, String title)
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(10,10,10,10));
		
		panel.add(new JLabel(message), BorderLayout.CENTER);
		panel.add(new JLabel(message), BorderLayout.CENTER);
		panel.add(new JLabel(message), BorderLayout.CENTER);
		
		
		createAlert(panel,title,null, null);
	}
	public static void createAlert(String message, String title, Point location)
	{
		
	}
}
