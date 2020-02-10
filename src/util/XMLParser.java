package util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class XMLParser {
	
	public static HashMap<String, String> parser(File xmlFile){
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = null;
		try {
			saxParser = factory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
		XMLParserHandel xmlParserHandel = new XMLParserHandel();
		try {
			saxParser.parse(xmlFile, xmlParserHandel);
		} catch (SAXException | IOException e) {
			e.printStackTrace();
		}
		return xmlParserHandel.getServletMap();
	}
}
