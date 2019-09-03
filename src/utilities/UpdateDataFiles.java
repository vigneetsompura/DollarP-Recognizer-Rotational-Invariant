package utilities;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class UpdateDataFiles {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		DataHandling.writeObject(DataHandling.preprocessXML("mmg\\",32), "mmg32Original.obj");
	}

}
