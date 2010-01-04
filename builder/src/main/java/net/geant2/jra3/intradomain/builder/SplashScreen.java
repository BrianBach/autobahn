package net.geant2.jra3.intradomain.builder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

/**
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class SplashScreen extends JWindow {
  private int duration;

  public SplashScreen(int d) {
    duration = d;
  }

  // A simple little method to show a title screen in the center
  // of the screen for the amount of time given in the constructor
  public void showSplash() {
    JPanel content = (JPanel) getContentPane();
    content.setBackground(Color.white);
    System.out.println ("Ciekawe");
    ImageIcon icon=null;
    try{
    	icon = new ImageIcon(getClass().getResource(
		"/images/splash.png"));
    }catch (Exception e){
    	e.printStackTrace();
    }
    System.out.println ("Ciekawe");
    //Set the window's bounds, centering the window
    if (icon== null)
    	return;
    int width = icon.getIconWidth();
    int height = icon.getIconHeight();
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width - width) / 2;
    int y = (screen.height - height) / 2;
    setBounds(x, y, width, height);

    // Build the splash screen
    
    JLabel label = new JLabel(icon);
    content.add(label, BorderLayout.CENTER);
    Color oraRed = new Color(0x22, 0x22, 0x60, 0xff);
    content.setBorder(BorderFactory.createLineBorder(oraRed, 4));

    // Display it
    setVisible(true);

    // Wait a little while, maybe while loading resources
    try {
      Thread.sleep(duration);
    } catch (Exception e) {
    }

    setVisible(false);
  }

  public void showSplashAndExit() {
    showSplash();
    System.exit(0);
  }
}