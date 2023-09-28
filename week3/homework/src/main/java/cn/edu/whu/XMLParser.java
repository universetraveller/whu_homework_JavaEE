package cn.edu.whu;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class XMLParser {

	public static URL getFileURL(String filePath) throws Exception{
		return Thread.currentThread().getContextClassLoader().getResource(filePath);
	}
	public static Document parse(URL url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		return document;
	}
	public static Document getDocument(String filePath) throws Exception{
		return parse(getFileURL(filePath));
	}
}
