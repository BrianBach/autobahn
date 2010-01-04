package net.geant2.jra3.intradomain.builder;

import javax.swing.JDialog;
import java.awt.Dimension;
import javax.swing.WindowConstants;
import java.awt.Color;

/**
 * 
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class AboutDialog extends JDialog {

	/**
	 * This method initializes 
	 * 
	 */
	public AboutDialog() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
        this.setSize(new Dimension(426, 312));
        this.setEnabled(false);
        this.setBackground(new Color(51, 0, 153));
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
