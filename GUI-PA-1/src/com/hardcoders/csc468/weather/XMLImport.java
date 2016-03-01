package com.hardcoders.csc468.weather;
import com.hardcoders.csc468.weather.model.WeatherDataPoint;
import com.hardcoders.csc468.weather.model.WindDirection;
//import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Converts XML files to an XmlWeatherDataPoint.
 * 
 * @see XmlWeatherDataPoint
 * 
 * @author Johnny Ackerman
 */
public class XMLImport{
    //TODO make Implement comparable for sorting!!!!!!!!!
    List<XmlWeatherDataPoint> yearOfPoints = new ArrayList<>();
    
    /**
     * Used to test functionality
     * @param args 
     */
    public static void main(String[] args)
    {
        XMLImport aClass = new XMLImport();
        aClass.read("../weatherData/2010-01.xml");
    }
    
    /**
     * Takes a list of files and reads in their data individually, then
     *  returns a list of all XmlWeatherDataPoints read in
     * @param fileNames
     * @return yearOfPoints - all the points read in from files
     */
    public List<XmlWeatherDataPoint> readAll( List<File> fileNames )
    {
        yearOfPoints.clear();
        for (File file : fileNames)
        {
            read( file.toString());
        }
        return yearOfPoints;
    }
    
    /**
     * Reads the XML data from a single XML file. uses the JDOM library to
     *  parse though the XML tags
     * 
     * @see JDOM
     * 
     * @param fileName 
     */
    public void read( String fileName){
        SAXBuilder builder = new SAXBuilder();
        File currentXmlFile = new File(fileName);
        
        try {

		Document document = (Document) builder.build(currentXmlFile);
		Element rootNode = document.getRootElement();
		List list = rootNode.getChildren("weather");
                
		for (int i = 0; i < list.size(); i++) {
                    
                    XmlWeatherDataPoint newPoint = new XmlWeatherDataPoint();

		   Element node = (Element) list.get(i);
                   
                   String date = node.getChildText("date");
                   String time = node.getChildText("time");
                   String dateTime = date.trim() + " " + time.trim() + "M";
                   DateFormat newFormat = new SimpleDateFormat("MM/dd/yy hh:mma");
                   Date newDate = newFormat.parse(dateTime);
                   
                   newPoint.setTimestamp(newDate);
                   
                 
                   newPoint.setTempurature(stringToDouble(node.getChildText("temperature")));
                   newPoint.setHumditiy(stringToDouble(node.getChildText("humidity")));
                   newPoint.setPressure(stringToDouble(node.getChildText("barometer")));
                   newPoint.setWindSpeed(stringToDouble(node.getChildText("windspeed")));
                   
                   
                   //newPoint.windDIrection = stringToDouble(node.getChildText("windDirection"));
                   String temp = node.getChildText("winddirection");
                   //System.out.println(temp + " is in temp");
                   if( temp == null)
                   {
                       //System.out.println("temp is null");
                   }
                   else
                   {
                       temp = temp.trim();
                   }
                   newPoint.setWindDirection(WindDirection.fromString(temp));
                   
                   newPoint.setWindGust(stringToDouble(node.getChildText("windgust")));
                   newPoint.setWindChill(stringToDouble(node.getChildText("windchill")));
                   newPoint.setHeatIndex(stringToDouble(node.getChildText("heatindex")));
                   newPoint.setUVIndex(stringToDouble(node.getChildText("rainfall")));
                   newPoint.setPercipitation(stringToDouble(node.getChildText("rainfall")));
                   
                   yearOfPoints.add(newPoint);
                   //yearOfPoints.add(i, newPoint);

		}

	  } catch (IOException | JDOMException io) {
		System.out.println(io.getMessage());
	  } catch (ParseException ex) {
            Logger.getLogger(XMLImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Converts a string to its double equivalent
     * @param text
     * @return 
     */
    public double stringToDouble(String text){
        double temp = 0;
        try{
            temp = Double.parseDouble(text);
        }
        catch(NumberFormatException e){
        System.out.println("Number Format Exception, Inside string to Double");
        };
        return temp;
    }

    /**
     * Adds functionality to the WeatherDataPoint model. This is used to
     *  store the read in information in an easy to pass structure.
     * 
     * @see WeatherDataPoint
     */
    public class XmlWeatherDataPoint implements WeatherDataPoint {
        private Date          timestamp;
        private Double        temperature;
        private Double        humidity;
        private Double        barometer;
        private Double        windSpeed;
        private WindDirection windDirection;
        private Double        windGust;
        private Double        windChill;
        private Double        heatIndex;
        private Double        uvIndex;
        private Double        rainFall;
        
        /**
         * initializes all values to null because any given tag can be missing
         */
        public XmlWeatherDataPoint() {
            timestamp = null;
            temperature = null;
            humidity = null;
            barometer = null;
            windSpeed = null;
            windDirection = null;
            windGust = null;
            windChill = null;
            heatIndex = null;
            uvIndex = null;
            rainFall = null;
        }
        
        
        @Override
        public Date getTimestamp() {
            return timestamp;
        }

        /**
         * sets the timestamp
         * @param timestamp 
         */
        private void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }
        
        @Override
        public Double getTemperature() {
            return temperature;
        }
        
        @Override
        public TemperatureDataPointAdapter getTemperatureAsDataPoint() {
            return new TemperatureDataPointAdapter(this);
        }
        
        /**
         * sets the temperature
         * @param temperature 
         */
        private void setTempurature(Double temperature) {
            this.temperature = temperature;
        }

        @Override
        public Double getHumidity() {
            return humidity;
        }

        @Override
        public HumidityDataPointAdapter getHumidityAsDataPoint() {
            return new HumidityDataPointAdapter(this);
        }
        
        /**
         * sets the humidity
         * 
         * @param humidity 
         */
        private void setHumditiy(Double humidity) {
            this.humidity = humidity;
        }
        
        @Override
        public Double getPressure() {
            return barometer;
        }
        
        @Override
        public PressureDataPointAdapter getPressureAsDataPoint() {
            return new PressureDataPointAdapter(this);
        }
        
        /**
         * sets the barometric pressure
         * @param pressure 
         */
        public void setPressure(Double pressure) {
            this.barometer = pressure;
        }

        @Override
        public Double getWindSpeed() {
            return windSpeed;
        }
        
        @Override
        public WindSpeedDataPointAdapter getWindSpeedAsDataPoint() {
            return new WindSpeedDataPointAdapter(this);
        }
        
        /**
         * sets the wind speed
         * @param windSpeed 
         */
        public void setWindSpeed(Double windSpeed) {
            this.windSpeed = windSpeed;
        }

        @Override
        public WindDirection getWindDirection() {
            return windDirection;
        }
        
        @Override
        public WindDirectionDataPointAdapter getWindDirectionAsDataPoint() {
            return new WindDirectionDataPointAdapter(this);
        }

        /**
         * sets the windDirection
         * 
         * @see WindDirection
         * 
         * @param windDirection 
         */
        private void setWindDirection(WindDirection windDirection) {
            this.windDirection = windDirection;
        }
        
        @Override
        public Double getWindGust() {
            return windGust;
        }
        
        @Override
        public WindGustDataPointAdapter getWindGustAsDataPoint() {
            return new WindGustDataPointAdapter(this);
        }
        
        /**
         * sets the wind gusts
         * @param windGust 
         */
        private void setWindGust(Double windGust) {
            this.windGust = windGust;
        }

        @Override
        public Double getWindChill() {
            return windChill;
        }
        
        @Override
        public WindChillDataPointAdapter getWindChillAsDataPoint() {
            return new WindChillDataPointAdapter(this);
        }
        
        /**
         * sets the wind chill
         * @param windChill 
         */
        private void setWindChill(Double windChill) {
            this.windChill = windChill;
        }

        @Override
        public Double getHeatIndex() {
            return heatIndex;
        }
        
        @Override
        public HeatIndexDataPointAdapter getHeatIndexAsDataPoint() {
            return new HeatIndexDataPointAdapter(this);
        }
        
        /**
         * sets the heat index
         * @param heatIndex 
         */
        private void setHeatIndex(Double heatIndex) {
            this.heatIndex = heatIndex;
        }

        @Override
        public Double getUVIndex() {
            return uvIndex;
        }
        
        @Override
        public UVIndexDataPointAdapter getUVIndexAsDataPoint() {
            return new UVIndexDataPointAdapter(this);
        }
        
        /**
         * sets the UV index
         * @param uvIndex 
         */
        private void setUVIndex(Double uvIndex) {
            this.uvIndex = uvIndex;
        }

        @Override
        public Double getPercipitation() {
            return rainFall;
        }
        
        @Override
        public PercipitationDataPointAdapter getPercipitationAsDataPoint() {
            return new PercipitationDataPointAdapter(this);
        }
        
        /**
         * sets the rainfall
         * @param percipitation 
         */
        private void setPercipitation(Double percipitation) {
            this.rainFall = percipitation;
        }
    }
}
