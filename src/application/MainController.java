package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import utilities.DataHandling;
import utilities.DollarP;
import utilities.Point;
import utilities.Result;
import utilities.Template;
import utilities.User;


public class MainController implements Initializable{
	
	final Line sampleLine = new Line(0, 0, 140, 0);
    private ArrayList<Point> points = new ArrayList<Point>();
    private GraphicsContext gC;
    private ArrayList<Template> t;
    Template candidate;
    
    @FXML
    private Canvas basecanvas;
    
    @FXML
    private ImageView imagecontainer;
    
    @FXML
    private Label gesturetypeholder, resultContainer;
    
    @FXML
    private AnchorPane rootpane;
	
	
	
    @FXML
    public void canvasMousePressed(MouseEvent event) {
        
        if(event.getButton()== MouseButton.PRIMARY){
            gC.beginPath();
            gC.moveTo(event.getX(), event.getY());
            points.add(new Point((int) event.getX(), (int) event.getY()));
            gC.lineTo(event.getX(), event.getY());
            gC.stroke();
            gC.closePath();
        }else if(event.getButton()==MouseButton.SECONDARY){
            clearCanvas();
        }
        
    }
    
    public void clearCanvas(){
        gC.clearRect(0, 0, basecanvas.getWidth(), basecanvas.getHeight());
        points.clear();
    }
    
    
    public void recognize() {
    	Result r = DollarP.recognize(new Template(points), t, 0.02);
    	resultContainer.setText("Result:  "+r.getTemp().getType()+":"+r.getScore());
    	
//    	candidate  = DollarP.findTemplateWithMinimumBoundingBox(new Template(points));
//    	gC.beginPath();
//    	clearCanvas();
//    	gC.moveTo(candidate.getPoints().get(0).getX(), candidate.getPoints().get(0).getY());
//    	gC.lineTo(candidate.getPoints().get(0).getX(), candidate.getPoints().get(0).getY());
//    	gC.stroke();
//    	for(int i=1; i<candidate.getPoints().size();i++) {
//    		if(candidate.getPoints().get(i).getStrokeID()== candidate.getPoints().get(i-1).getStrokeID()) {
//    		gC.lineTo(candidate.getPoints().get(i).getX(), candidate.getPoints().get(i).getY());
//    		gC.stroke();
//    		}
//    	}
    	
    }
    
    @FXML
    public void canvasMouseReleased(MouseEvent event){
        gC.setStroke(randomColor());
    }
    
    @FXML
    public void canvasMouseDragged(MouseEvent event) {
        if(event.getButton()== MouseButton.PRIMARY){
            gC.lineTo(event.getX(), event.getY());
            points.add(new Point((int) event.getX(), (int) event.getY()));
            gC.stroke();
        }
    }
    
    public ArrayList<User> getData() throws ParserConfigurationException, SAXException, IOException{
    	ArrayList<User> u = null;
    	try {
    		u = (ArrayList<User>) DataHandling.ReadObject("Data32.obj");
    	}catch(Exception e) {
    		e.printStackTrace();
    		DataHandling.writeObject(DataHandling.preprocessXML("Dataset\\",32), "Data32.obj");
    		u = getData();
    	}
		return u;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	Random random = new Random();
    	ArrayList<User> u = null;
		try {
			u = getData();
			System.out.println(u.size());
			System.out.println(u.get(0).getGestures().size());
			
		} catch (Exception e) {
			e.printStackTrace();
		} 

    	t = new ArrayList<Template>();
    	for(String type: u.get(0).getGestures().keySet()) {
    		for(int i=0;i<10;i++) {
	    		User user = u.get(random.nextInt(u.size()));
	    		ArrayList<Template> temp = user.getGestures().get(type);
	    		t.add(temp.get(random.nextInt(temp.size())));
    		}
    	}
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
