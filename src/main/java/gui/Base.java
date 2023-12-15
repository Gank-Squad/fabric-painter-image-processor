package gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import components.DisplayImage;
import components.GuiBase;
import components.PreviewImage;
import components.filters.ImageDropTargetListener;
import components.filters.ImageFilter;
import processing.ImageToInstructions;
import processing.ProcessImg;
import processing.dithering.DitherTypes;

public class Base extends JFrame implements ActionListener, ChangeListener
{

	JButton openFileChooser, resetButton, saveButton, previewButton;
	JFileChooser fc;
	public DisplayImage displayImage;
	JSpinner xPanels, yPanels, xScale, yScale, xOffset, yOffset;
	JRadioButton floydSteinbergRadioButton, riemersmaRadioButton, noneRadioButton;
	private boolean resetting = false;
	
	public static final Base INSTANCE = new Base();
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Fabric Painter Image Processor");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		Base base = INSTANCE;
		INSTANCE.addComponentsToPane(frame.getContentPane());
		
		frame.pack();
		frame.setVisible(true);
	}
	
	public void addComponentsToPane(Container pane)
	{
//		pane.setLayout(new GridBagLayout());
		
		JPanel settingsPanel = new JPanel();
		settingsPanel.setLayout(new GridBagLayout());
		settingsPanel.setBorder(null);
		JPanel displayPanel = new JPanel();
//		displayPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
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
		
		
		label = new JLabel("Dither Type ", SwingConstants.RIGHT);
		label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 3;
		settingsPanel.add(label, c);
		
		label = new JLabel("x offset ", SwingConstants.RIGHT);
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 2;
		settingsPanel.add(label, c);
		
		label = new JLabel("y offset ", SwingConstants.RIGHT);
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 7;
		c.gridy = 2;
		settingsPanel.add(label, c);
		
		fc = GuiBase.createFileChooser();
		fc.setFileFilter(new ImageFilter());
		
		openFileChooser = new JButton();
		openFileChooser.setText("select image");
		openFileChooser.addActionListener(this);
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 4;
		settingsPanel.add(openFileChooser, c);
		
		
		
		SpinnerModel panelModel = new SpinnerNumberModel(1, 1, 64, 1);
		
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
		c.gridy = 2;
		panelModel = new SpinnerNumberModel(0, -128, 128, 1);
		this.xOffset = new JSpinner(panelModel);
		this.xOffset.addChangeListener(this);
		settingsPanel.add(this.xOffset, c);
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 8;
		c.gridy = 2;
		panelModel = new SpinnerNumberModel(0, -128, 128, 1);
		this.yOffset = new JSpinner(panelModel);
		this.yOffset.addChangeListener(this);
		settingsPanel.add(this.yOffset, c);
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 6;
		c.gridy = 3;
		this.floydSteinbergRadioButton = new JRadioButton();
		this.floydSteinbergRadioButton.setText("Floyd Steinberg");
		this.floydSteinbergRadioButton.setSelected(true);
		this.floydSteinbergRadioButton.addActionListener(this);
		settingsPanel.add(this.floydSteinbergRadioButton, c);
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 7;
		c.gridy = 3;
		this.riemersmaRadioButton = new JRadioButton();
		this.riemersmaRadioButton.setText("Riemersma");
		this.riemersmaRadioButton.addActionListener(this);
		settingsPanel.add(this.riemersmaRadioButton, c);
		
		c = new GridBagConstraints();
		c.weightx = 0.5;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 8;
		c.gridy = 3;
		this.noneRadioButton = new JRadioButton();
		this.noneRadioButton.setText("None");
		this.noneRadioButton.addActionListener(this);
		settingsPanel.add(this.noneRadioButton, c);
		
		ButtonGroup DitherButtonGroup = new ButtonGroup();
		DitherButtonGroup.add(floydSteinbergRadioButton);
		DitherButtonGroup.add(riemersmaRadioButton);
		DitherButtonGroup.add(noneRadioButton);
		
		
		this.resetButton = new JButton();
		this.resetButton.setText("Reset");
		this.resetButton.addActionListener(this);
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 6;
		c.gridy = 4;
		settingsPanel.add(this.resetButton, c);
		
		this.saveButton = new JButton();
		this.saveButton.setText("Save");
		this.saveButton.addActionListener(this);
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 7;
		c.gridy = 4;
		settingsPanel.add(this.saveButton, c);
		
		this.previewButton = new JButton();
		this.previewButton.setText("Preview");
		this.previewButton.addActionListener(this);
		c = new GridBagConstraints();
		c.weightx = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 8;
		c.gridy = 4;
		
		settingsPanel.add(this.previewButton, c);
		
		JSplitPane splitPane = new JSplitPane(SwingConstants.VERTICAL, displayPanel, settingsPanel);
		splitPane.setBorder(null);
		splitPane.setResizeWeight(1.0);
		pane.add(splitPane);
		
	}
	
	public void createImageFrame()
	{
		
		JFrame frame = new JFrame("Image");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		BufferedImage image = this.displayImage.getSelectionImage();
		
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
		
		frame.add(p);
		
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (resetting)
		{
			// do nothing, just wait until its done resetting
		}
		else if (e.getSource() == openFileChooser)
		{
			FileDialog fd = new FileDialog(this, "Select Image");
			fd.setMode(FileDialog.LOAD);
			fd.setVisible(true);
			
			try {
				if (fd.getFile() != null)
				{
//					System.out.println(fd.getDirectory() + fd.getFile());
//					displayImage.updateImage(ImageIO.read(new File(fd.getDirectory() + fd.getFile())));
					displayImage.updateImage(ProcessImg.loadImage(new File(fd.getDirectory() + fd.getFile())));
				}
					
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			
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
		else if (e.getSource() == this.saveButton)
		{
			int returnVal = fc.showSaveDialog(Base.this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				try
				{
					File file = new File(fc.getSelectedFile().getAbsolutePath());
					
					// make sure it has the png file extension
					String ext = ImageFilter.getExtension(file); 
					if (ext == null || !ext.equals("png"))
						file = new File(fc.getSelectedFile().getAbsolutePath() + ".png");
					
					ImageIO.write(this.displayImage.getSelectionImage(), "png", file);
					
					File dir = new File(file.getParentFile().getAbsolutePath() + File.separator + file.getName() + 
							ImageToInstructions.instructionDirectoryAppension);
					System.out.println(dir.getAbsolutePath());
					ImageToInstructions.convertImageToInstructions(this.displayImage.getSelectionImage(), dir);
				}
				catch (Exception ex)
				{
					System.out.println("failed to save file: " + fc.getSelectedFile().getAbsoluteFile());
				}
			}
		}
		else if (e.getSource() == this.previewButton)
		{
			if (this.displayImage.getSelectionImage() != null)
			{
				createImageFrame();
			}
		}
		else if (e.getSource() == this.resetButton)
		{
			
			System.out.println("resetting");
			
			this.resetting = true;	
			this.xPanels.setValue(1);
			this.yPanels.setValue(1);
			this.xOffset.setValue(0);
			this.yOffset.setValue(0);
			this.xScale.setValue(100.0);
			this.yScale.setValue(100.0);
			this.resetting = false;
			
			this.displayImage.resetAllFields();
		}
		
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == this.xPanels || e.getSource() == this.yPanels)
		{
			this.displayImage.setPanels((int)(Integer)this.xPanels.getValue(), (int)(Integer)this.yPanels.getValue());
		}
		else if (e.getSource() == this.xOffset || e.getSource() == this.yOffset)
		{
			this.displayImage.setOffset((int)(Integer)this.xOffset.getValue(), -(int)(Integer)this.yOffset.getValue());
		}
		else if (e.getSource() == this.xScale || e.getSource() == this.yScale)
		{
			this.displayImage.setScale((double)(Double)this.xScale.getValue() / 100.0, (double)(Double)this.yScale.getValue() / 100.0);
		}
	}

	
}
