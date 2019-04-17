package application;
	
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utilities.DataHandling;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Canvas basecanvas = (Canvas) loader.getNamespace().get("basecanvas");
			basecanvas.setCursor(Cursor.CROSSHAIR);
	        DropShadow ds = new DropShadow();
	        ds.setOffsetY(0.5);
	        ds.setOffsetX(0.5);
	        ds.setColor(Color.GRAY);
	        basecanvas.setEffect(ds);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		java.nio.file.Path p = java.nio.file.Paths.get("");
    	System.out.println(p.toAbsolutePath().toString());
    	File f = new File("Data32.obj");
    	if(!f.exists()) {
    		DataHandling.writeObject(DataHandling.preprocessXML("Dataset\\",32), "Data32.obj");
    	}
		launch(args);
	}
}
