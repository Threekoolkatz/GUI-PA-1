package com.hardcoders.csc468.weather;
import com.hardcoders.csc468.weather.model.WeatherDataPoint;
import com.hardcoders.csc468.weather.model.WindDirection;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author 7143145
 */
public class XMLImport{
    
    static List<XmlWeatherDataPoint> yearOfPoints = new ArrayList<>();
    
    public static void read( String fileName){
        SAXBuilder builder = new SAXBuilder();
        File currentXmlFile = new File(fileName);
        
        try {

		Document document = (Document) builder.build(currentXmlFile);
		Element rootNode = document.getRootElement();
		List list = rootNode.getChildren("");
                
		for (int i = 0; i < list.size(); i++) {

		   Element node = (Element) list.get(i);
                   
                   XmlWeatherDataPoint newPoint;
                   //newPoint.time

		   System.out.println("" + node.getChildText(""));
		   System.out.println("" + node.getChildText(""));
		   System.out.println("" + node.getChildText(""));
		   System.out.println("" + node.getChildText(""));

		}

	  } catch (IOException io) {
		System.out.println(io.getMessage());
	  } catch (JDOMException jdomex) {
		System.out.println(jdomex.getMessage());
	  }
    }

    class XmlWeatherDataPoint implements WeatherDataPoint
    {
        @Override
        public Date getTimestamp() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Double getTemperature() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Double getHumidity() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Double getPressure() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Double getWindSpeed() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public WindDirection getWindDirection() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Double getWindGust() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Double getWindChill() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Double getHeatIndex() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Double getUVIndex() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Double getPercipitation() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
}
