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
    
    int yearCount;
    int monthCount;
    int weekCount;
    int dayCount;
    
    public AverageData() {
        allDataValues = new CalculatedAverageWeatherData();
        currentDataPoints = new ArrayList<>();
        dailyValues = new ArrayList<>();
        weeklyValues = new ArrayList<>();
        monthlyValues = new ArrayList<>();
        yearlyValues = new ArrayList<>();
        yearCount = 1;
        monthCount = 1;
        weekCount = 1;
        dayCount = 1;
    }
    
    public void calculateDataArray( List<XmlWeatherDataPoint> passedInData ) {
        currentDataPoints = passedInData;
        
        //This is to test calculatedAverage -- should be commented out later.
        //Will be happy if used. //Could use as a default
        allDataValues = (calculateAverageFromXmlWeatherDataPoint(passedInData));
        
        
        dailyMeanValues();
        weeklyMeanValues();
        monthlyMeanValues();
        yearlyMeanValues();
    }
    
    public void dailyMeanValues(){
        List<CalculatedAverageWeatherData> workingDataPoints =new ArrayList<>();
        //set loop time values
        //ex 12:01AM to 11:59PM <-- might be easier by checking day
        if( currentDataPoints == null){
            System.err.println
                ("Weather data Points passed to AverageData() is null");
            return;
        }
        
        //currentDataPoints.get(0).getTimestamp().
        
        
        for( XmlWeatherDataPoint currentPoint : currentDataPoints ) {
            
        }
        
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
    
    /**
     * calculateAverageFromXmlWeatherDataPoint - takes an XMLWeatherDataPoint
     *  List and calculates the needed information ( average temperature, high/
     *  low temperature, average wind speed, max wind gust, prevailing wind
     *  direction and total rainfall.Then stores that data in a
     *  CalculatedAverageWeatherData class
     * 
     * @see CalculatedAverageWeatherData
     * 
     * @param workingList-XmlWeatherDataPoint list being worked on
     * @return tempWorkingAverageDataPoint - 
     *      filled in CalculatedAverageWeatherData class structure
     */
    public CalculatedAverageWeatherData 
        calculateAverageFromXmlWeatherDataPoint(List<XmlWeatherDataPoint> 
                workingList )
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
            if((tempWorkingAverageDataPoint.getHighTemperature() == null) ||
                    (tempWorkingAverageDataPoint.getHighTemperature().getTemperature() 
                    < workingPoint.getTemperature()))
            {
                tempWorkingAverageDataPoint.highTempPoint = workingPoint;
            }
            
            //check if current low point is less than workingPoint
            if((tempWorkingAverageDataPoint.getLowTemperature() == null) ||
                    (tempWorkingAverageDataPoint.getLowTemperature().getTemperature() 
                    > workingPoint.getTemperature()))
            {
                tempWorkingAverageDataPoint.lowTempPoint = workingPoint;
            }
            
        //Collect windspeed
            tempWorkingAverageDataPoint.averageWindSpeed 
                    += workingPoint.getWindSpeed();
        
        //Find max windGust with date/time occurrence
            if((tempWorkingAverageDataPoint.getMaxWindGust() == null) ||
                    (tempWorkingAverageDataPoint.getMaxWindGust().getWindGust()
                    < workingPoint.getWindGust()))
            {
                tempWorkingAverageDataPoint.maxWindGustPoint = workingPoint;
            }
        
        //Determine prevailing wind direction
        //Yikes
        
        //Collect rainfall
            tempWorkingAverageDataPoint.totalRainFall
                    += workingPoint.getPercipitation();
        }
        
        //Calculate average temperature
        tempWorkingAverageDataPoint.averageTemperature = 
                tempWorkingAverageDataPoint.getAverageTemperature() 
                        / pointCount;
        
        //Calculate average windspeed
        tempWorkingAverageDataPoint.averageWindSpeed = 
                tempWorkingAverageDataPoint.getAverageTemperature() 
                        / pointCount;
        
        return tempWorkingAverageDataPoint;
    }
        
    public CalculatedAverageWeatherData 
        calculateAverageCalculatedAverageWeatherData(
                List<CalculatedAverageWeatherData> workingList )
    {
        CalculatedAverageWeatherData tempWorkingAverageDataPoint 
                = new CalculatedAverageWeatherData();
        int pointCount = 0;
        
        for( CalculatedAverageWeatherData workingPoint : workingList)
        {
            //increament number of points processed
            pointCount++;
            
        //Collect temperature
            tempWorkingAverageDataPoint.averageTemperature 
                    += workingPoint.getAverageTemperature();
        //Find high/low with date/time occurrence
        
            //check if current high point is higher than workingPoint
            if((tempWorkingAverageDataPoint.getHighTemperature() == null) ||
                    (tempWorkingAverageDataPoint.getHighTemperature()
                            .getTemperature() 
                    < workingPoint.getHighTemperature().getTemperature()))
            {
                tempWorkingAverageDataPoint.highTempPoint 
                        = workingPoint.getHighTemperature();
            }
            
            //check if current low point is less than workingPoint
            if((tempWorkingAverageDataPoint.getLowTemperature() == null) ||
                    (tempWorkingAverageDataPoint.getLowTemperature()
                            .getTemperature() 
                    > workingPoint.getLowTemperature().getTemperature()))
            {
                tempWorkingAverageDataPoint.lowTempPoint 
                        = workingPoint.getLowTemperature();
            }
            
        //Collect windspeed
            tempWorkingAverageDataPoint.averageWindSpeed 
                    += workingPoint.getAverageWindSpeed();
        
        //Find max windGust with date/time occurrence
            if((tempWorkingAverageDataPoint.getMaxWindGust() == null) ||
                    (tempWorkingAverageDataPoint.getMaxWindGust().getWindGust()
                    < workingPoint.getMaxWindGust().getWindGust()))
            {
                tempWorkingAverageDataPoint.highTempPoint 
                        = workingPoint.getHighTemperature();
            }
        
        //Determine prevailing wind direction
        //Yikes
        
        //Collect rainfall
            tempWorkingAverageDataPoint.totalRainFall
                    += workingPoint.getTotalRainFall();
        }
        
        //Calculate average temperature
        tempWorkingAverageDataPoint.averageTemperature = 
                tempWorkingAverageDataPoint.getAverageTemperature() / pointCount;
        
        //Calculate average windspeed
        tempWorkingAverageDataPoint.averageWindSpeed = 
                tempWorkingAverageDataPoint.getAverageWindSpeed() / pointCount;
        
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
            return this.averageTemperature;
        }

        @Override
        public XmlWeatherDataPoint getLowTemperature() {
            return this.lowTempPoint;
        }

        @Override
        public XmlWeatherDataPoint getHighTemperature() {
            return this.highTempPoint;
        }

        @Override
        public double getAverageWindSpeed() {
            return this.averageTemperature;
        }

        @Override //Make sure to call this Wind Speed during output
        public XmlWeatherDataPoint getMaxWindGust() {
            return this.maxWindGustPoint;
        }

        @Override
        public WindDirection getPrevailingWindDirection() {
            return this.prevalingWindDirection;
        }

        @Override
        public double getTotalRainFall() {
            return this.totalRainFall;
        }
    }
}
