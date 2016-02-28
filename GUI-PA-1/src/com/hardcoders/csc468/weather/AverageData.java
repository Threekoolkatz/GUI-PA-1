package com.hardcoders.csc468.weather;

import com.hardcoders.csc468.weather.XMLImport.XmlWeatherDataPoint;
import com.hardcoders.csc468.weather.model.AverageWeatherData;
import com.hardcoders.csc468.weather.model.WindDirection;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author 7143145
 */
public class AverageData {
    List<XmlWeatherDataPoint> currentDataPoints;
    List<CalculatedAverageWeatherData> dailyValues;
    List<CalculatedAverageWeatherData> weeklyValues;
    List<CalculatedAverageWeatherData> monthlyValues;
    List<CalculatedAverageWeatherData> yearlyValues;
    CalculatedAverageWeatherData allDataValues;
    
    public AverageData() {
        allDataValues = new CalculatedAverageWeatherData();
        currentDataPoints = new ArrayList<>();
        dailyValues = new ArrayList<>();
        weeklyValues = new ArrayList<>();
        monthlyValues = new ArrayList<>();
        yearlyValues = new ArrayList<>();
    }
    
    public void calculateDataArray( List<XmlWeatherDataPoint> passedInData ) {
        currentDataPoints = passedInData;
        
        //This is to test calculatedAverage -- should be commented out later.
        //Will be happy if used. //Could use as a default
        allDataValues = (calculateAverage(passedInData));
        
        
        dailyMeanValues();
        weeklyMeanValues();
        monthlyMeanValues();
        yearlyMeanValues();
    }
    
    public void dailyMeanValues(){
        List<CalculatedAverageWeatherData> workingDataPoints =new ArrayList<>();
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
        //pass day values list
        
        //call average temperature
        //call high/low with date/time occurrence
        //call average windspeed
        //call max windspeed with date/time occurrence
        //call prevailing wind direction
        //call rainfall
        
    }
    
    public void yearlyMeanValues() {
        //set loop time values
        //pass month values list
        
        //call average temperature
        //call high/low with date/time occurrence
        //call average windspeed
        //call max windspeed with date/time occurrence
        //call prevailing wind direction
        //call rainfall
    }
    
    public CalculatedAverageWeatherData 
        calculateAverage(List<XmlWeatherDataPoint> workingList )
    {
        CalculatedAverageWeatherData tempWorkingAverageDataPoint 
                = new CalculatedAverageWeatherData();
        int pointCount = 0;
        
        for( XmlWeatherDataPoint workingPoint : workingList)
        {
            //increament number of points processed
            pointCount++;
            
        //Collect temperature
            tempWorkingAverageDataPoint.averageTemperature 
                    += workingPoint.getTemperature();
        //Find high/low with date/time occurrence
        
            //check if current high point is higher than workingPoint
            if((tempWorkingAverageDataPoint.highTempPoint == null) ||
                    (tempWorkingAverageDataPoint.highTempPoint.getTemperature() 
                    < workingPoint.getTemperature()))
            {
                tempWorkingAverageDataPoint.highTempPoint = workingPoint;
            }
            
            //check if current low point is less than workingPoint
            if((tempWorkingAverageDataPoint.lowTempPoint == null) ||
                    (tempWorkingAverageDataPoint.lowTempPoint.getTemperature() 
                    > workingPoint.getTemperature()))
            {
                tempWorkingAverageDataPoint.lowTempPoint = workingPoint;
            }
            
        //Collect windspeed
            tempWorkingAverageDataPoint.averageWindSpeed 
                    += workingPoint.getWindSpeed();
        
        //Find max windGust with date/time occurrence
            if((tempWorkingAverageDataPoint.maxWindGustPoint == null) ||
                    (tempWorkingAverageDataPoint.maxWindGustPoint.getWindGust()
                    < workingPoint.getWindGust()))
            {
                tempWorkingAverageDataPoint.highTempPoint = workingPoint;
            }
        
        //Determine prevailing wind direction
        //Yikes
        
        //Collect rainfall
            tempWorkingAverageDataPoint.totalRainFall
                    += workingPoint.getPercipitation();
        }
        
        //Calculate average temperature
        tempWorkingAverageDataPoint.averageTemperature = 
                tempWorkingAverageDataPoint.averageTemperature / pointCount;
        
        //Calculate average windspeed
        tempWorkingAverageDataPoint.averageWindSpeed = 
                tempWorkingAverageDataPoint.averageWindSpeed / pointCount;
        
        return tempWorkingAverageDataPoint;
    }
    
    public class CalculatedAverageWeatherData implements AverageWeatherData {
        
        double averageTemperature;
        XmlWeatherDataPoint highTempPoint;
        XmlWeatherDataPoint lowTempPoint;
        double averageWindSpeed;
        XmlWeatherDataPoint maxWindGustPoint;
        WindDirection prevalingWindDirection;
        double totalRainFall;
        
        /**
         *
         */
        public CalculatedAverageWeatherData(){
            averageTemperature = 0;
            highTempPoint = null;
            lowTempPoint = null;
            averageWindSpeed = 0;
            maxWindGustPoint = null;
            prevalingWindDirection = null;
            totalRainFall = 0;
        }

        @Override
        public double getAverageTemperature() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public XmlWeatherDataPoint getLowTemperature() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public XmlWeatherDataPoint getHighTemperature() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getAverageWindSpeed() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override //Make sure to call this Wind Speed during output
        public XmlWeatherDataPoint getMaxWindGust() {
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
