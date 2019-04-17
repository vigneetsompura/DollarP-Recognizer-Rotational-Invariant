package utilities;

import java.io.Serializable;

/**
 * @author Vigneet Sompura
 * Container for a point
 */
public class Point implements Serializable{
	/**
	 * Generated versionID
	 */
	private static final long serialVersionUID = -1811981217671040448L;
	/**
	 *  X and Y coordinates of points.
	 */
	private double x,y;
	/**
	 * Stroke Number
	 */
	private int strokeID;
	
	
	
	public Point(double x, double y, int strokeID) {
		super();
		this.x = x;
		this.y = y;
		this.strokeID = strokeID;
	}
	
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
		this.strokeID = 0;
	}
	
	public Point(int x, int y, int strokeID) {
		super();
		this.x = x;
		this.y = y;
		this.strokeID = strokeID;
	}
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.strokeID = 0;
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
	public int getStrokeID() {
		return strokeID;
	}
	public void setStrokeID(int strokeID) {
		this.strokeID = strokeID;
	}
	
	
	
}
