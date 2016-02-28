package com.hardcoders.csc468.weather;

import com.hardcoders.csc468.weather.model.AverageWeatherData;
import com.hardcoders.csc468.weather.model.WindDirection;
import java.util.List;


/**
 *
 * @author 7143145
 */
public class AverageData {
    List<XMLImport.XmlWeatherDataPoint> currentDataPoints;
    
    public void dailyMeanValues(){
        //set loop time values
        //ex 12:01AM to 11:59PM <-- might be easier by checking day
        
        //call average temperature
        //call high/low with date/time occurrence
        //call average windspeed
        //call max windspeed with date/time occurrence
        //call prevailing wind direction
        //call Total rainfall
        
    }
    
    public void weeklyMeanValues(){
        //set loop time values
        //ex monday though sunday <--- how will i know this???
        
        //call average temperature
        //call high/low with date/time occurrence
        //call average windspeed
        //call max windspeed with date/time occurrence
        //call prevailing wind direction
        //call rainfall
    }
    
    public void monthlyMeanValues(){
        //set loop time values
        //ex 01 <-- january
        
        //call average temperature
        //call high/low with date/time occurrence
        //call average windspeed
        //call max windspeed with date/time occurrence
        //call prevailing wind direction
        //call rainfall
        
    }
    
    public void yearlyMeanValues() {
        //set loop time values
        //ex 2010
        
        //call average temperature
        //call high/low with date/time occurrence
        //call average windspeed
        //call max windspeed with date/time occurrence
        //call prevailing wind direction
        //call rainfall
    }
    
    public class ListAverageWeatherData implements AverageWeatherData {
        
        /**
         *
         */
        public ListAverageWeatherData(){

        }

        @Override
        public double getAverageTemperature() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public XMLImport.XmlWeatherDataPoint getLowTemperature() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public XMLImport.XmlWeatherDataPoint getHighTemperature() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getAverageWindSpeed() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public XMLImport.XmlWeatherDataPoint getMaxWindSpeed() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public WindDirection getPrevailingWindDirection() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getTotalRainFall() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
