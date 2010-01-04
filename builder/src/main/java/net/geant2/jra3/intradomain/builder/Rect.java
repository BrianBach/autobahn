package net.geant2.jra3.intradomain.builder;

import java.awt.Rectangle;
/**
 * Custom rectangle object
 * 
 * @author Lucas Dolata <ldolata@man.poznan.pl>
 *
 */
public class Rect {
	/**
	 * X position on the screen
	 */
	double x=0;
	/**
	 * Y position on the screen
	 */
	double y=0;
	/**
	 * Width of rectangle
	 */
	double width=0;
	/**
	 * Height of rectangle
	 */
	double heigth=0;
	/**
	 * Creates Rect object
	 */
	public Rect (){
	}
	/**
	 * Creates Rect object using other Rect object infomation
	 * @param rect rect object
	 */
	public Rect (Rectangle rect){
		x= rect.getX();
		y= rect.getMaxY();
		width = rect.getWidth();
		heigth= rect.getHeight();
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeigth() {
		return heigth;
	}
	public void setHeigth(double heigth) {
		this.heigth = heigth;
	}
}
