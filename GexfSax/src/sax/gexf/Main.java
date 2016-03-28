package sax.gexf;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Main {

	public static void main(String[] args) throws Exception {
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document doc = db.parse(new FileInputStream(new File("src/sax/gexf/sources/small-world-3.gexf")));
		
		NodeList elements = doc.getElementsByTagName("edge");
		System.out.println(elements.getLength());

		// Adds a new attribute. If an attribute with that name is already present 
	    // in the element, its value is changed to be that of the value parameter
		for (int i = 0; i < elements.getLength(); i++) {
			Element element = (Element) elements.item(i);
			String str = "" + i + "";
			element.setAttribute("id", str);
			System.out.println("id " + i + " added");
		}
		
		prettyPrint(doc);
		toGexf(doc);
		
	}
	
	public static final void toGexf(Document doc) throws Exception {
        TransformerFactory transformerFactory =
        TransformerFactory.newInstance();
        Transformer transformer =
        transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result =
        new StreamResult(new File("src/sax/gexf/sources/small-world-3.gexf"));
        transformer.transform(source, result);
        // Output to console for testing
        StreamResult consoleResult =
        new StreamResult(System.out);
        transformer.transform(source, consoleResult);
	}
	
	public static final void prettyPrint(Document xml) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		Writer out = new StringWriter();
		tf.transform(new DOMSource(xml), new StreamResult(out));
		System.out.println(out.toString());
	}
}
