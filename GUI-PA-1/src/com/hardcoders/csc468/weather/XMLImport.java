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

        @Override
        public Double getTemperature() {
            return temperature;
        }
        
        @Override
        public TemperatureDataPointAdapter getTemperatureAsDataPoint() {
            return new TemperatureDataPointAdapter(this);
        }
        
        private void setTempurature(double temperature) {
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
        
        private void setHumditiy(double humidity) {
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
        
        public void setPressure(double pressure) {
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
        
        public void setWindSpeed(double windSpeed) {
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
        
        private void setWindGust(double windGust) {
            this.windGust = windGust;
        }

        @Override
        public Double getWindChill() {
            return windChill;
        }
        
        public WindChillDataPointAdapter getWindChillAsDataPoint() {
            return new WindChillDataPointAdapter(this);
        }
        
        private void setWindChill(double windChill) {
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
        
        private void setHeatIndex(double heatIndex) {
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
        
        private void setUVIndex(double uvIndex) {
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
        
        private void setPercipitation(double percipitation) {
            this.rainFall = percipitation;
        }
    }
}
