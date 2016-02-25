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
                   newPoint.setWindDirection(WindDirection.fromString(temp));
                   
                   newPoint.setWindGust(stringToDouble(node.getChildText("windgust")));
                   newPoint.setWindChill(stringToDouble(node.getChildText("windchill")));
                   newPoint.setHeatIndex(stringToDouble(node.getChildText("heatindex")));
                   newPoint.setUVIndex(stringToDouble(node.getChildText("rainfall")));
                   newPoint.setPercipitation(stringToDouble(node.getChildText("rainfall")));
                   
		   //System.out.println("Date: " + node.getChildText("date"));
		   //System.out.println("time: " + node.getChildText("time"));
		   //System.out.println("humidity: " + node.getChildText("humidity"));
		   //System.out.println("barometer: " + node.getChildText("barometer"));
                   yearOfPoints.add(newPoint);

		}

	  } catch (IOException | JDOMException io) {
		System.out.println(io.getMessage());
	  } catch (ParseException ex) {
            Logger.getLogger(XMLImport.class.getName()).log(Level.SEVERE, null, ex);
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
        
        private void setWindGust(Double windGust) {
            this.windGust = windGust;
        }

        @Override
        public Double getWindChill() {
            return windChill;
        }
        
        public WindChillDataPointAdapter getWindChillAsDataPoint() {
            return new WindChillDataPointAdapter(this);
        }
        
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
        
        private void setPercipitation(Double percipitation) {
            this.rainFall = percipitation;
        }
    }
}
