package net.geant2.jra3.intradomain.builder;

import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

/**
 * Customizes the view in file choosers for AutoBAHN Topology Builder files 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class AutobahnTopologyFileView extends FileView {
	/**
	 * Icon for file
	 */
	Icon icon;
	
	public AutobahnTopologyFileView() {
		icon = new ImageIcon(getClass().getResource("/images/autobahnMarker.png"));
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.filechooser.FileView#getDescription(java.io.File)
	 */
	public String getDescription(File f) {
		return "Autobahn topology creator project";
	}
	/*
	 * (non-Javadoc)
	 * @see javax.swing.filechooser.FileView#getIcon(java.io.File)
	 */
	public Icon getIcon(File f) {
		if (f.isDirectory()) {
			return super.getIcon(f);
		}
		String extension = getExtension(f);
		if (extension != null && extension.equals("atp")) 
			return icon;
		return super.getIcon(f);
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
}
