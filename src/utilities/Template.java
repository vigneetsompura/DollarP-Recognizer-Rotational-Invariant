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
	 * Mapping for Gesture Types.
	 */
	HashMap<String, Character> typeEnum = new HashMap<String, Character>();
	/**
	 * Gesture type of the Template 
	 */
	String type = "";
	/**
	 * ID of the Template
	 */
	String ID ="0";
	/**
	 * List of points
	 */
	ArrayList<Point> points;
	
	/**
	 * initializes typeEnum field
	 */
	public void typeEnum() {
		typeEnum.put("arrowhead", 'A');
		typeEnum.put("asterisk", 'B');
		typeEnum.put("D", 'D');
		typeEnum.put("exclamation_point", 'E');
		typeEnum.put("five_point_star", 'F');
		typeEnum.put("H", 'H');
		typeEnum.put("half_note", 'G');
		typeEnum.put("I", 'I');
		typeEnum.put("line", 'L');
		typeEnum.put("N", 'N');
		typeEnum.put("null", 'O');
		typeEnum.put("P", 'P');
		typeEnum.put("pitchfork", 'Q');
		typeEnum.put("six_point_star", 'S');
		typeEnum.put("T", 'T');
		typeEnum.put("X", 'X');
	}
	
	public Template(String type) {
		this.type = type;
		points = new ArrayList<Point>();
		typeEnum();
	}
	
	public Template(String type, String id) {
		this.type = type;
		this.ID = id;
		points = new ArrayList<Point>();
		typeEnum();
	}
	
	public Template(String type,ArrayList<Point> points) {
		this.type = type;
		this.points = points;
		typeEnum();
	}
	
	public Template(ArrayList<Point> points) {
		this.type = "";
		this.points = points;
		typeEnum();
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
	
	public char getTypeEnum() {
		return typeEnum.get(type);
	}
	
	/**
	 * Adds specided point at the end of the list of points in template.
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
