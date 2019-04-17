package utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Vigneet Sompura
 *
 * Contains Gesture Templates of a User.
 */
public class User implements Serializable{
	/**
	 * Generated vesion ID.
	 */
	private static final long serialVersionUID = 5686494834782078741L;
	/**
	 * UserID
	 */
	private String userID;
	/**
	 * Map of Gesture type to List of Templates
	 */
	private HashMap<String, ArrayList<Template>> gestures;
	
	
	public User(String userID) {
		super();
		this.userID = userID;
		this.gestures = new HashMap<String, ArrayList<Template>>();
	}
	
	/**
	 * @param template
	 * 
	 * Adds template to user profile.
	 */
	public void addTemplate(Template template) {
		ArrayList<Template> t = gestures.getOrDefault(template.getType(), new ArrayList<Template>());
		t.add(template);
		gestures.put(template.getType(),t);
		//System.out.println(template.getID());
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public HashMap<String, ArrayList<Template>> getGestures() {
		return gestures;
	}

	public void setGestures(HashMap<String, ArrayList<Template>> gestures) {
		this.gestures = gestures;
	}
	
	
}
