package components.filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class DirectoryFilter extends FileFilter {

	public static final String png = "png";
	public static final String jpg = "jpg";
	public static final String jpeg = "jpeg";
	
	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
		{
			return true;
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return "All Directories";
	}

}
