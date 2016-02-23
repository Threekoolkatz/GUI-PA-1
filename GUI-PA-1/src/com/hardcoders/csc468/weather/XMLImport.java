package com.hardcoders.csc468.weather;
import com.hardcoders.csc468.weather.model.WeatherDataPoint;
import com.hardcoders.csc468.weather.model.WindDirection;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
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
    
    public static void main(String[] args)
    {
        XMLImport aClass = new XMLImport();
        aClass.read("../weatherData/2010-01.xml");
    }
    
    public void read( String fileName){
        SAXBuilder builder = new SAXBuilder();
        File currentXmlFile = new File(fileName);
        XmlWeatherDataPoint newPoint = new XmlWeatherDataPoint();
        
        try {

		Document document = (Document) builder.build(currentXmlFile);
		Element rootNode = document.getRootElement();
		List list = rootNode.getChildren("weather");
                
		for (int i = 0; i < list.size(); i++) {

		   Element node = (Element) list.get(i);
                 
                   newPoint.temperature = stringToDouble(node.getChildText("temperature"));
                   newPoint.humidity = stringToDouble(node.getChildText("humidity"));
                   newPoint.barometer = stringToDouble(node.getChildText("barometer"));
                   newPoint.windSpeed = stringToDouble(node.getChildText("windspeed"));
                   //newPoint.windDIrection = stringToDouble(node.getChildText("windDirection"));
                   newPoint.windGust = stringToDouble(node.getChildText("windgust"));
                   newPoint.windChill = stringToDouble(node.getChildText("windchill"));
                   newPoint.heatIndex = stringToDouble(node.getChildText("heatindex"));
                   newPoint.uvIndex = stringToDouble(node.getChildText("rainfall"));
                   newPoint.rainFall = stringToDouble(node.getChildText("rainfall"));
                   
		   //System.out.println("Date: " + node.getChildText("date"));
		   //System.out.println("time: " + node.getChildText("time"));
		   //System.out.println("humidity: " + node.getChildText("humidity"));
		   //System.out.println("barometer: " + node.getChildText("barometer"));
                   yearOfPoints.add(newPoint);

		}

	  } catch (IOException | JDOMException io) {
		System.out.println(io.getMessage());
	  }
    }
    
    public double stringToDouble(String text){
        double temp = 0;
        try{
            temp = Double.parseDouble(text);
        }
        catch(NumberFormatException e){};
        return temp;
    }

    public class XmlWeatherDataPoint implements WeatherDataPoint
    {
        XmlWeatherDataPoint() {
            
        }
        @Override
        public Date getTimestamp() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Double getTemperature() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
        public void setTempurature(double temperature) {
            this.temperature = temperature;
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
        
        
        Date Timestamp = new Date();
        double temperature;
        double humidity;
        double barometer;
        double windSpeed;
        //windDirection;
        double windGust;
        double windChill;
        double heatIndex;
        double uvIndex;
        double rainFall;
    }
    
}
