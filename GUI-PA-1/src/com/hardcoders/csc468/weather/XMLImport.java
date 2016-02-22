package com.hardcoders.csc468.weather;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author 7143145
 */
public class XMLImport {
    public static void read( String fileName){
        SAXBuilder builder = new SAXBuilder();
        File currentXmlFile = new File(fileName);
        
        try {

		Document document = (Document) builder.build(currentXmlFile);
		Element rootNode = document.getRootElement();
		List list = rootNode.getChildren("staff");

		for (int i = 0; i < list.size(); i++) {

		   Element node = (Element) list.get(i);

		   System.out.println("First Name : " + node.getChildText("firstname"));
		   System.out.println("Last Name : " + node.getChildText("lastname"));
		   System.out.println("Nick Name : " + node.getChildText("nickname"));
		   System.out.println("Salary : " + node.getChildText("salary"));

		}

	  } catch (IOException io) {
		System.out.println(io.getMessage());
	  } catch (JDOMException jdomex) {
		System.out.println(jdomex.getMessage());
	  }
    }
    
}
