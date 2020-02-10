package util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParserHandel extends DefaultHandler {
	HashMap<String, String> servletNameClassMap;
	HashMap<String, String> servletNameURLMap;
	String nowString, str1, str2;
	public XMLParserHandel() {
		super();
		servletNameClassMap = new HashMap<String, String>();
		servletNameURLMap = new HashMap<String, String>();
	}
	public HashMap<String, String> getServletMap(){
		HashMap<String, String> map = new HashMap<String, String>();
		Set<String> nameSet = servletNameClassMap.keySet();
		Iterator<String> it = nameSet.iterator();
		while(it.hasNext()){
			String name = it.next();
			map.put(servletNameClassMap.get(name), servletNameURLMap.get(name));
		}
		return map;
	}
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
	}
	 
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if(qName.equals("servlet-name")){
			str1 = new String(nowString);
		}else if(qName.equals("servlet-class") || qName.equals("url-pattern")){
			str2 = new String(nowString);
		}else if(qName.equals("servlet")){
			servletNameClassMap.put(str1, str2);
		}else if(qName.equals("servlet-mapping")){
			servletNameURLMap.put(str1, str2);
		}
	}
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	    super.characters(ch, start, length);
	    nowString = new String(ch,start,length).trim();
	}

}
