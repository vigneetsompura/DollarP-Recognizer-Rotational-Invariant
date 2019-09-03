package utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Vigneet Sompura
 *
 * Container for a Gesture
 */
public class Template implements Serializable{
	/**
	 * Generated versionID
	 */
	private static final long serialVersionUID = 8230633969409461766L;
	
	/**
	 * Gesture type of the Template 
	 */
	private String type = "";
	/**
	 * ID of the Template
	 */
	private String ID ="0";
	/**
	 * List of points
	 */
	private ArrayList<Point> points;
	
	/**
	 * record of rotation from original gesture.
	 * */
	private int rotation = 0;
	
	
	public Template(String type) {
		this.type = type;
		points = new ArrayList<Point>();
	}
	
	public Template(String type, String id) {
		this.type = type;
		this.ID = id;
		points = new ArrayList<Point>();
	}
	
	public Template(String type,ArrayList<Point> points) {
		this.type = type;
		this.points = points;

	}
	
	public Template(ArrayList<Point> points) {
		this.type = "";
		this.points = points;
	}

	/** 
	 * @return clone of the template
	 */
	public Template clone() {
		Template temp = new Template(type+"", ID+"");
		for(Point p: points) {
			temp.addPoint(new Point(p.getX(),p.getY(),p.getStrokeID()));
		}
		return temp;
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
	public String getTypeEnum() {
		return type;
	}
	
	/**
	 * Adds specified point at the end of the list of points in template.
	 * @param point Point to be added.
	 */
	public void addPoint(Point point) {
		this.points.add(point);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		while(rotation>180) {
			rotation-=360;
		}
		this.rotation = rotation;
	}

	
	/**
	 * Prints all the points of a Template
	 */
	public void printInfo() {
		System.out.println(type+": "+ points.size()+" points");
		for(Point p:points) {
			System.out.println("("+p.getX()+","+p.getY()+","+p.getStrokeID()+")");
		}
	}
	
	
	
}
