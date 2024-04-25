package gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import components.DisplayImage;
import components.GuiBase;
import components.PreviewImage;
import components.actionListeners.MenuBarActionListener;
import components.filters.ImageDropTargetListener;
import components.filters.ImageFilter;
import gui.notification.Notification;
import processing.ProcessImg;
import processing.TransferableImage;
import processing.dithering.DitherTypes;

public class Base extends JFrame implements ActionListener, ChangeListener
{

	public JButton previewButton, pasteButton, copyButton; // resetButton, saveButton, openFileChooser  
	public JFileChooser fc;
	public JSlider hueSlider, brightnessSlider, saturationSlider, contrastSlider;
	public JLabel hueLabel, brightnessLabel, saturationLabel, contrastLabel;
	public DisplayImage displayImage;
	public JSpinner xPanels, yPanels, xScale, yScale; //  , xOffset, yOffset
	JRadioButton floydSteinbergRadioButton, riemersmaRadioButton, noneRadioButton;
	
	JMenuBar menuBar;
	public JMenu fileMenu, editMenu, viewMenu, helpMenu;
	JMenu ditherSubMenu;
	public JMenuItem loadImageMenuItem, saveImageMenuItem;
	
	public JMenuItem resetImageMenuItem, changeColorPaletteMenuItem, convertToMonochromeMenuItem, changeCanvasResolutionMenuItem;
	public JRadioButtonMenuItem floydSteinbergDitherMenuItem, riemersmaDitherMenuItem, noDitherMenuItem;
	
	public JMenuItem changeSelectionBoxColorMenuItem, onlyViewSelectionMenuItem, previewSelectionImageMenuItem;
	
	public JMenuItem helpMenuItem;
	
	// it might make more sense to make this a singleton since its hardcoded
	MenuBarActionListener menuBarActionListener = new MenuBarActionListener();
	
//	public SpinnerNumberModel xOffsetModel;
//	public SpinnerNumberModel yOffsetModel;
	
	private static JFrame frame;
	
	public static Point getFrameLocation() { return new Point(frame.getX(), frame.getY()); }
	
	public static JFrame getFrame() { return frame; } 
	
	public boolean resetting = false;
	
	public static final Base INSTANCE = new Base();
	
	public static void main(String[] args)
	{
		frame = new JFrame("Fabric Painter Image Processor");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if (ProcessImg.verifyImageMagick())
		{
			INSTANCE.createMenuBar(frame);
			INSTANCE.addComponentsToPane(frame.getContentPane());
		}
		else
		{
			JLabel label = new JLabel("ERROR: ImageMagick is required and must be "
					+ "added to path or in the same directory as this app -- "
					+ "https://imagemagick.org/script/download.php");
			frame.add(label);
		}
		
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void createMenuBar(JFrame frame)
	{
		this.menuBar = new JMenuBar();
		this.fileMenu = new JMenu("File");
		this.editMenu = new JMenu("Edit");
		this.viewMenu = new JMenu("View");
		this.helpMenu = new JMenu("Help");
//		JMenuItem loadImageMenuItem, saveImageMenuItem;
//		JMenuItem resetImageMenuItem, changeColorPaletteMenuItem, convertToMonochromeMenuItem, changeCanvasResolutionMenuItem;
//		JMenuItem changeSelectionBoxColorMenuItem, onlyViewSelectionMenuItem;
//		JMenuItem helpMenuItem;
		this.loadImageMenuItem = new JMenuItem("Load Image");
		this.loadImageMenuItem.addActionListener(this.menuBarActionListener);
		this.saveImageMenuItem = new JMenuItem("Save Image");
		this.saveImageMenuItem.addActionListener(this.menuBarActionListener);
		this.fileMenu.add(this.loadImageMenuItem);
		this.fileMenu.add(this.saveImageMenuItem);
		
		
		this.resetImageMenuItem = new JMenuItem("Reset Image");
		this.resetImageMenuItem.addActionListener(this.menuBarActionListener);
		
		this.changeColorPaletteMenuItem = new JMenuItem("Change Color Palette");
		this.changeColorPaletteMenuItem.addActionListener(this.menuBarActionListener);
		
		// I think it might be best to keep this a separate option for the time being
		// I am not familiar enough with how converting to monochrome works
		// so I'm not sure if just changing to a monochrome colorpalette would suffice, but probably not
		this.convertToMonochromeMenuItem = new JMenuItem("Convert to Monochrome");
		this.convertToMonochromeMenuItem.addActionListener(this.menuBarActionListener);
		
		this.ditherSubMenu = new JMenu("Dither Type");
			this.floydSteinbergDitherMenuItem = new JRadioButtonMenuItem("Floyd Steinberg");
			this.floydSteinbergDitherMenuItem.setSelected(true);
			this.floydSteinbergDitherMenuItem.addActionListener(this.menuBarActionListener);
			this.ditherSubMenu.add(this.floydSteinbergDitherMenuItem);
			
			this.riemersmaDitherMenuItem = new JRadioButtonMenuItem("Riemersma");
			this.riemersmaDitherMenuItem.addActionListener(menuBarActionListener);
			this.ditherSubMenu.add(this.riemersmaDitherMenuItem);
			
			this.noDitherMenuItem = new JRadioButtonMenuItem("None");
			this.noDitherMenuItem.addActionListener(menuBarActionListener);
			this.ditherSubMenu.add(this.noDitherMenuItem);
		
		ButtonGroup ditherButtonGroup = new ButtonGroup();
		ditherButtonGroup.add(this.floydSteinbergDitherMenuItem);
		ditherButtonGroup.add(this.riemersmaDitherMenuItem);
		ditherButtonGroup.add(this.noDitherMenuItem);
		
		
		this.editMenu.add(this.ditherSubMenu);
		this.editMenu.add(this.resetImageMenuItem);
		this.editMenu.add(this.changeColorPaletteMenuItem);
		this.editMenu.add(this.convertToMonochromeMenuItem);
		
		
		this.previewSelectionImageMenuItem = new JMenuItem("Preview Image");
		this.previewSelectionImageMenuItem.addActionListener(this.menuBarActionListener);
		
		this.viewMenu.add(this.previewSelectionImageMenuItem);
		
		this.helpMenuItem = new JMenuItem("help button");
		this.helpMenuItem.addActionListener(this.menuBarActionListener);
		
		this.helpMenu.add(this.helpMenuItem);
		
		this.menuBar.add(fileMenu);
		this.menuBar.add(editMenu);
		this.menuBar.add(viewMenu);
		this.menuBar.add(helpMenu);
		
		frame.setJMenuBar(menuBar);
	}
	

	
	public void addComponentsToPane(Container pane)
	{
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new GridBagLayout());
		settingsPanel.setBorder(null);
		JPanel displayPanel = new JPanel();
		displayPanel.setLayout(new BorderLayout());
		displayPanel.setBorder(null);
		
		displayPanel.add(new JTextArea(10,10));
		
		GridBagConstraints c = new GridBagConstraints();	
		displayImage = GuiBase.createDisplayImage();
		displayPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		displayPanel.add(displayImage, BorderLayout.CENTER);
		displayPanel.setTransferHandler(new TransferHandler("text"));
		
		DropTarget dropTarget = new DropTarget(displayPanel, DnDConstants.ACTION_COPY_OR_MOVE, new ImageDropTargetListener());
        dropTarget.setDefaultActions(DnDConstants.ACTION_COPY_OR_MOVE);

		
		JLabel label = new JLabel("x panels ", SwingConstants.RIGHT);
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 0;
		settingsPanel.add(label, c);
		
		label = new JLabel("y panels ", SwingConstants.RIGHT);
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 7;
		c.gridy = 0;
		settingsPanel.add(label, c);
		
		label = new JLabel("x scale (%) ", SwingConstants.RIGHT);
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 1;
		settingsPanel.add(label, c);
		
		label = new JLabel("y scale (%) ", SwingConstants.RIGHT);
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 7;
		c.gridy = 1;
		settingsPanel.add(label, c);
		
		
		fc = GuiBase.createFileChooser();
		fc.setFileFilter(new ImageFilter());
		
		
		SpinnerNumberModel panelModel = new SpinnerNumberModel(1, 1, 64, 1);
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 6;
		c.gridy = 0;
		this.xPanels = new JSpinner(panelModel);
		this.xPanels.addChangeListener(this);
		settingsPanel.add(xPanels, c);
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 8;
		c.gridy = 0;
		panelModel = new SpinnerNumberModel(1, 1, 64, 1);
		this.yPanels = new JSpinner(panelModel);
		this.yPanels.addChangeListener(this);
		settingsPanel.add(yPanels, c);
		
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 6;
		c.gridy = 1;
		panelModel = new SpinnerNumberModel(100.0, 50.0, 500.0, 5.0);
		this.xScale = new JSpinner(panelModel);
		this.xScale.addChangeListener(this);
		settingsPanel.add(xScale, c);
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 8;
		c.gridy = 1;
		panelModel = new SpinnerNumberModel(100.0, 50.0, 500.0, 5.0);
		this.yScale = new JSpinner(panelModel);
		this.yScale.addChangeListener(this);
		settingsPanel.add(yScale, c);
		
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 6;
		c.gridy = 4;
		this.pasteButton = new JButton();
		this.pasteButton.setText("Paste");
		this.pasteButton.addActionListener(this);
		settingsPanel.add(this.pasteButton, c);
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 5;
		c.gridy = 4;
		this.copyButton = new JButton();
		this.copyButton.setText("Copy");
		this.copyButton.addActionListener(this);
		settingsPanel.add(this.copyButton, c);
		
		
		this.previewButton = new JButton();
		this.previewButton.setText("Preview");
		this.previewButton.addActionListener(this);
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 7;
		c.gridy = 4;
		c.gridwidth = 2;
		
		settingsPanel.add(this.previewButton, c);
		
		
		this.hueSlider = new JSlider(0,200, 100);
		this.hueSlider.setPaintTicks(true);
		this.hueSlider.setPaintLabels(true);
		this.hueSlider.setMinorTickSpacing(10);
		this.hueSlider.addChangeListener(this);
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 6;
		c.gridy = 5;
		c.gridwidth = 3;
		settingsPanel.add(this.hueSlider, c);
		
		this.hueLabel = new JLabel();
		this.hueLabel.setText("Hue: " + this.hueSlider.getValue());
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 5;
		c.gridwidth = 1;
		settingsPanel.add(this.hueLabel, c);
		
		this.saturationSlider = new JSlider(0,200, 100);
		this.saturationSlider.setPaintTicks(true);
		this.saturationSlider.setPaintLabels(true);
		this.saturationSlider.setMinorTickSpacing(10);
		this.saturationSlider.addChangeListener(this);
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 6;
		c.gridy = 6;
		c.gridwidth = 3;
		settingsPanel.add(this.saturationSlider, c);
		
		this.saturationLabel = new JLabel();
		this.saturationLabel.setText("Saturation: " + this.saturationSlider.getValue());
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 6;
		c.gridwidth = 1;
		settingsPanel.add(this.saturationLabel, c);
		
		this.brightnessSlider = new JSlider(0,200, 100);
		this.brightnessSlider.setPaintTicks(true);
		this.brightnessSlider.setPaintLabels(true);
		this.brightnessSlider.setMinorTickSpacing(10);
		this.brightnessSlider.addChangeListener(this);
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 6;
		c.gridy = 7;
		c.gridwidth = 3;
		settingsPanel.add(this.brightnessSlider, c);
		
		this.brightnessLabel = new JLabel();
		this.brightnessLabel.setText("Brightness: " + this.brightnessSlider.getValue());
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 7;
		c.gridwidth = 1;
		settingsPanel.add(this.brightnessLabel, c);
		
		this.contrastSlider = new JSlider(0,100, 0);
		this.contrastSlider.setPaintTicks(true);
		this.contrastSlider.setPaintLabels(true);
		this.contrastSlider.setMinorTickSpacing(10);
		this.contrastSlider.addChangeListener(this);
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 6;
		c.gridy = 8;
		c.gridwidth = 3;
		settingsPanel.add(this.contrastSlider, c);
		
		this.contrastLabel = new JLabel();
		this.contrastLabel.setText("Contrast: " + this.contrastSlider.getValue());
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 8;
		c.gridwidth = 1;
		settingsPanel.add(this.contrastLabel, c);
		
		JSplitPane splitPane = new JSplitPane(SwingConstants.VERTICAL, displayPanel, settingsPanel);
		splitPane.setBorder(null);
		splitPane.setResizeWeight(1.0);
		pane.add(splitPane);
		
	}
	
	public void createImageFrame()
	{
		
		JFrame frame = new JFrame("Image");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		BufferedImage image = this.displayImage.getSelectionImage();
		
		System.out.println("" + image.getWidth() + ", " + image.getHeight());
		
		PreviewImage p = new PreviewImage(image);
		
		int sideLength = 750;
		
		// not sure why this code doesn't work properly but its good enough
		Dimension d = new Dimension();
		d.height = sideLength;
		
		double hwRatio = (double)image.getWidth() / (double)image.getHeight();
		d.width = (int)Math.round((double)sideLength * hwRatio);
		
		if (d.width > sideLength)
		{
			d.width = sideLength;
			d.height = (int)Math.round((double)sideLength / hwRatio);
		}
		
		frame.setBounds(0,0,d.width,d.height);
		
		frame.add(p, BorderLayout.CENTER);
		
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (resetting)
		{
			// do nothing, just wait until its done resetting
		}
		// other components go here
		else if (e.getSource() == this.floydSteinbergRadioButton)
		{
			this.displayImage.setDitherType(DitherTypes.FloydSteinberg);
		}
		else if (e.getSource() == this.riemersmaRadioButton)
		{
			this.displayImage.setDitherType(DitherTypes.Riemersma);
		}
		else if (e.getSource() == this.noneRadioButton)
		{
			this.displayImage.setDitherType(DitherTypes.None);
		}
		else if (e.getSource() == this.previewButton)
		{
			if (this.displayImage.getSelectionImage() != null)
			{
				createImageFrame();
			}
		}
		else if (e.getSource() == this.pasteButton)
		{
			System.out.println("paste button pressed");
			Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
			
			try
			{
				BufferedImage image = (BufferedImage)cb.getData(DataFlavor.imageFlavor);
				this.displayImage.updateImage(image);
			} catch (Exception ex)
			{
				System.out.println("Something went wrong pasting from clipboard, likely not recognized as an image");
				Notification.createAlert("Failed to paste from clipboard!", "Error");
			}
		}
		else if (e.getSource() == this.copyButton)
		{
			
			Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
			
			try
			{
				System.out.println("copying image to clipboard");
				cb.setContents(new TransferableImage(this.displayImage.getSelectionImage()), null);
			}
			catch (Exception ex)
			{
				System.out.println("Something went wrong copying image to clipboard");
				ex.printStackTrace();
			}
		}
		
		
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == this.xPanels || e.getSource() == this.yPanels)
		{
			this.displayImage.setPanels((int)(Integer)this.xPanels.getValue(), (int)(Integer)this.yPanels.getValue());
		}
		else if (e.getSource() == this.xScale || e.getSource() == this.yScale)
		{
			this.displayImage.setScale((double)(Double)this.xScale.getValue() / 100.0, (double)(Double)this.yScale.getValue() / 100.0);
		}
		else if (e.getSource() == this.hueSlider) 
		{
			this.hueLabel.setText("Hue: " + this.hueSlider.getValue());
			this.displayImage.setHue(this.hueSlider.getValue());
		}
		else if (e.getSource() == this.saturationSlider) 
		{
			this.saturationLabel.setText("Saturation: " + this.saturationSlider.getValue());
			this.displayImage.setSaturation(this.saturationSlider.getValue());
		}
		else if (e.getSource() == this.brightnessSlider) 
		{
			this.brightnessLabel.setText("Brightness: " + this.brightnessSlider.getValue());
			this.displayImage.setBrightness(this.brightnessSlider.getValue());
		}
		else if (e.getSource() == this.contrastSlider) 
		{
			this.contrastLabel.setText("Contrast: " + this.contrastSlider.getValue());
			this.displayImage.setContrast(this.contrastSlider.getValue());
		}
	}

	
}
