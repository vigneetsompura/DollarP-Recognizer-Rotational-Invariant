package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Vigneet Sompura
 *
 * Contains methods for preparing data from XML files.
 */
public class DataHandling {

	/**
	 * @param path Path to DataSet Folder
	 * @param N sampling rate 
	 * @return Returns the object representing Dataset in form of 
	 *         Array of Users and their corresponding data after performing normalization. 
	 * @throws ParserConfigurationException 
	 * @throws SAXException
	 * @throws IOException
	 */
	public static ArrayList<User> preprocessXML(String path, int N) throws ParserConfigurationException, SAXException, IOException{
		ArrayList<User> user = new ArrayList<User>();
		File folder = new File(path);
		File[] subfolders = folder.listFiles();
		
		for(File subfolder: subfolders) {
			User u = new User(subfolder.getName());
			for(File file: subfolder.listFiles()) {
				Document doc = DataHandling.getXMLDoc(file);
				Element gesture = (Element) doc.getElementsByTagName("Gesture").item(0);
				String type = gesture.getAttribute("Name").split("~")[0];
				String id = gesture.getAttribute("Name").split("~")[1];
				Template t = new Template(type,id);
				NodeList strokes = doc.getElementsByTagName("Stroke");
				for(int j=0;j<strokes.getLength();j++) {
					Element stroke = (Element) strokes.item(j);
					int strokeNo = Integer.parseInt(stroke.getAttribute("index"));
					NodeList points = stroke.getElementsByTagName("Point");
					for(int k=0;k<points.getLength();k++) {
						Element point = (Element) points.item(k);
						int x = Integer.parseInt(point.getAttribute("X"));
						int y = Integer.parseInt(point.getAttribute("Y"));
						t.addPoint(new Point(x,y,strokeNo));
					}
				}
				t = DollarPOriginal.normalize(t, N);
				u.addTemplate(t);
			}
			user.add(u);
		}
		return user;
	}
	
	/**
	 * @param path Path to DataSet folder
	 * @return Returns the object representing Dataset in form of Array of Users and their corresponding data
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static ArrayList<User> parseXML(String path) throws ParserConfigurationException, SAXException, IOException{
		ArrayList<User> user = new ArrayList<User>();
		File folder = new File(path);
		File[] subfolders = folder.listFiles();
		for(File subfolder: subfolders) {
			User u = new User(subfolder.getName());
			for(File file: subfolder.listFiles()) {
				Document doc = DataHandling.getXMLDoc(file);
				Element gesture = (Element) doc.getElementsByTagName("Gesture").item(0);
				String type = gesture.getAttribute("Name").split("~")[0];
				String id = gesture.getAttribute("Name").split("~")[1];
				Template t = new Template(type,id);
				//System.out.println(t.getID());
				NodeList strokes = doc.getElementsByTagName("Stroke");
				for(int j=0;j<strokes.getLength();j++) {
					Element stroke = (Element) strokes.item(j);
					int strokeNo = Integer.parseInt(stroke.getAttribute("index"));
					NodeList points = stroke.getElementsByTagName("Point");
					for(int k=0;k<points.getLength();k++) {
						Element point = (Element) points.item(k);
						int x = Integer.parseInt(point.getAttribute("X"));
						int y = Integer.parseInt(point.getAttribute("Y"));
						t.addPoint(new Point(x,y,strokeNo));
					}
				}
				u.addTemplate(t);
			}
			user.add(u);
		}
		return user;
	}
	
	/**
	 * returns XML file converted to parseable document.
	 * 
	 * @param file 
	 * @return Document
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private static Document getXMLDoc(File file) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	
	/**
	 * Writes the serializable object to a file
	 * 
	 * @param g Serializable object to be written
	 * @param path Destination path
	 */
	public static void writeObject(Serializable g,String path) {
		try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(path))){
			o.writeObject(g);
			System.out.println("Done");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Reads the object stored in a file.
	 * @param filepath Path to object File 
	 * @return Object
	 */
	public static Object ReadObject(String filepath) {
		 
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Object obj = objectIn.readObject();
            System.out.println("The Object has been read from the file");
            objectIn.close();
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
	
	
}
