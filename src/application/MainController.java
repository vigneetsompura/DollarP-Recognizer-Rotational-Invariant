package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class MainController implements Initializable{
	
	private int userID;
	final Line sampleLine = new Line(0, 0, 140, 0);
    private ArrayList<Stroke> strokes = new ArrayList<Stroke>();
    private GraphicsContext gC;
    private int currentStrokeID = 1;
    private Stroke currentStroke;
    private GestureType currentGestureType;
    private ArrayList<GestureType> gestureTypes = new ArrayList<GestureType>();
    
    
    @FXML
    private Canvas basecanvas;
    
    @FXML
    private ImageView imagecontainer;
    
    @FXML
    private Label gesturetypeholder;
    
    @FXML
    private AnchorPane rootpane;
	
	class Point{
		int x, y;
		public Point (int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	class Stroke{
		int index;
		ArrayList<Point> points = new ArrayList<Point>();
		
		public Stroke(int index) {
			this.index = index;
		}
		
		public void addPoint(Point point) {
			points.add(point);
		}
 	}
	
	class GestureType{
		String name;
		int index;
		public GestureType(String name) {
			this.name = name;
			index = 1;
		}
		
		public String toString() {
			return name+"~"+((index<10)?"0":"")+index;
		}
	}
	
	@FXML
	public void canvasMousePressed(MouseEvent event) {
	    
	    if(event.getButton()== MouseButton.PRIMARY){
	        gC.beginPath();
	        gC.moveTo(event.getX(), event.getY());
	        currentStroke = new Stroke(currentStrokeID++);
	        currentStroke.addPoint(new Point((int) event.getX(), (int) event.getY()));
	        gC.lineTo(event.getX(), event.getY());
	        gC.stroke();
	        gC.closePath();
	    }else if(event.getButton()==MouseButton.SECONDARY){
	    	clearCanvas();
	    }
	    
	}
	 
	public void clearCanvas(){
	       gC.clearRect(0, 0, basecanvas.getWidth(), basecanvas.getHeight());
	       strokes.clear();
	       currentStrokeID = 1;
	}
	
	
	
	 @FXML
    public void canvasMouseReleased(MouseEvent event){
		if(event.getButton() == MouseButton.PRIMARY) {
		strokes.add(currentStroke);
        gC.setStroke(randomColor());
		}
    }
    
    @FXML
    public void canvasMouseDragged(MouseEvent event) {
        if(event.getButton()== MouseButton.PRIMARY){
            gC.lineTo(event.getX(), event.getY());
            currentStroke.addPoint(new Point((int) event.getX(), (int) event.getY()));
            gC.stroke();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	Random random = new Random();
    	userID = random.nextInt(200);
        gC = basecanvas.getGraphicsContext2D();
        gC.setLineWidth(3);
        gC.setStroke(randomColor());
        imagecontainer.setImage(new Image("/application/Assets/Dataset.png"));
    }    
 
    public Color randomColor(){
        Random rand = new Random();
        return Color.web("rgb("+rand.nextInt(256)+", "+rand.nextInt(256)+", "+rand.nextInt(256)+")");
    }
}
