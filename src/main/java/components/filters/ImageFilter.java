package components.filters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImageFilter extends FileFilter {

	public static final String png = "png";
	public static final String jpg = "jpg";
	public static final String jpeg = "jpeg";
	
	@Override
	public boolean accept(File f) {
		if (f.isDirectory())
		{
			return true;
		}
		String extension = getExtension(f);
		
		if (extension == null)
			return false;
		
		if (extension.equals(png)
			|| extension.equals(jpg) 
			|| extension.equals(jpeg)
			)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return "All Valid Images";
	}
	
	public static String getExtension(File f)
	{
		String name = f.getName();
		
		int i = name.lastIndexOf(".");
		
		if (i > 0 && i < name.length() - 1)
			return name.substring(i + 1).toLowerCase();
		return null;
	}
	
}
