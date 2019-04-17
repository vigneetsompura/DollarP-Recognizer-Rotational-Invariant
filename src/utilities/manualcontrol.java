package utilities;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class manualcontrol {

	public manualcontrol() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		DataHandling.writeObject(DataHandling.preprocessXML("Dataset\\",32), "Data32.obj");
	}

}
