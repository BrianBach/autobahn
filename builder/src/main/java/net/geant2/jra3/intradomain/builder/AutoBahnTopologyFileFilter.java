package net.geant2.jra3.intradomain.builder;
import java.io.File;
import javax.swing.filechooser.FileFilter;


/**
 * Filter use for file choosers dialog boxes. Allows showing only 
 * file with extension .atp
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class AutoBahnTopologyFileFilter extends FileFilter {
	/**
	 * Filtered extension
	 */
	public String extension = "atp";
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}
		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals(extension)) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}
	/**
	 * Gets the file extension
	 * 
	 * @param f file object
	 * @return	file extension
	 */
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}
	/**
	 * Description for file with this extension
	 */
	public String getDescription() {
		return "AutoBahn topology builder file";
	}

}
