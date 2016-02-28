package com.hardcoders.csc468.weather;

import com.hardcoders.csc468.weather.XMLImport.XmlWeatherDataPoint;
import com.hardcoders.csc468.weather.model.AverageWeatherData;
import com.hardcoders.csc468.weather.model.WindDirection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 *
 * @author 7143145
 */
public class AverageData {
    
    //Points passed into the class
    private List<XmlWeatherDataPoint> currentDataPoints;
    
    //List of all daily calculations
    private List<CalculatedAverageWeatherData> dailyValues;
    
    //List of all weekly calculations
    private List<CalculatedAverageWeatherData> weeklyValues;
    
    //List of all monthly calculations
    private List<CalculatedAverageWeatherData> monthlyValues;
    
    //List of all yearly Calculations
    private List<CalculatedAverageWeatherData> yearlyValues;
    
    //Calculation off all data values
    private CalculatedAverageWeatherData allDataValues;
    
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
    }
    
    public void calculateData( List<XmlWeatherDataPoint> passedInData ) {
        //sets datapoints to global-to-class variable
        currentDataPoints = passedInData;
        
        //actually Calculate data
        crunchData();
    }
    
    private void crunchData(){
        //check if there is data to calculate
        if( currentDataPoints == null){
            System.err.println
                ("Weather data Points passed to AverageData() is null");
            return;
        }
        
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(currentDataPoints.get(0).getTimestamp());
        
        //Initializes the first day
        int currentDay = tempCalendar.get(Calendar.DAY_OF_MONTH);
        
        //initializes current week no value needed due to special case
        int currentDayOfWeek; 
        
        //Initializes the current month
        int currentMonth = tempCalendar.get(Calendar.MONTH);
        
        //Initializes the current Year
        int currentYear = tempCalendar.get(Calendar.YEAR);
        
        yearCount = 1;
        monthCount = 1;
        weekCount = 1;
        dayCount = 1;
        
        //all xml points in a given day
        List<XmlWeatherDataPoint> tempList = new ArrayList<>();
        
        //structure given from day calculations
        CalculatedAverageWeatherData tempDayValue = 
                new CalculatedAverageWeatherData();
        
        //structure given from month calculations
        CalculatedAverageWeatherData tempMonthValue = 
                new CalculatedAverageWeatherData();
        
        //Temporary list of days in a given week
        List<CalculatedAverageWeatherData>
                tempWeekDaysList = new ArrayList<>();
        //Temporary list of days in a given month
        List<CalculatedAverageWeatherData> 
                tempMonthDaysList = new ArrayList<>();
        //Temporary list of days in a given year
        List<CalculatedAverageWeatherData> 
                tempYearMonthsList = new ArrayList<>();
        
        // loops though all passed in xml points
        for( XmlWeatherDataPoint currentPoint : currentDataPoints ) {
            tempCalendar.setTime(currentPoint.getTimestamp());
            
            if( currentDay != tempCalendar.get(Calendar.DAY_OF_MONTH)){
                dayCount++;
                currentDay = tempCalendar.get(Calendar.DAY_OF_MONTH);
                tempDayValue = 
                        calculateAverageFromXmlWeatherDataPoints( tempList );
                dailyValues.add(tempDayValue);
                tempWeekDaysList.add(tempDayValue);
                tempMonthDaysList.add(tempDayValue);
                tempList.clear();
            }
            currentDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);
            if( currentDayOfWeek == tempCalendar.get(Calendar.SUNDAY)){
                weekCount++;
                weeklyValues.add(calculateAverageCalculatedAverageWeatherData(
                        tempWeekDaysList));
                tempWeekDaysList.clear();
            }
            if( currentMonth != tempCalendar.get(Calendar.MONTH)){
                monthCount++;
                currentMonth = tempCalendar.get(Calendar.MONTH);
                tempMonthValue = calculateAverageCalculatedAverageWeatherData(
                        tempMonthDaysList);
                monthlyValues.add(tempMonthValue);
                tempYearMonthsList.add(tempMonthValue);
                tempMonthDaysList.clear();
            }
            if( currentYear != tempCalendar.get(Calendar.YEAR)){
                yearCount++;
                currentYear = tempCalendar.get(Calendar.YEAR);
                yearlyValues.add(calculateAverageCalculatedAverageWeatherData(
                        tempYearMonthsList));
                tempMonthDaysList.clear();
            }
            tempList.add(currentPoint);
        }
        
        if( !tempList.isEmpty()){
            tempDayValue = 
                    calculateAverageFromXmlWeatherDataPoints( tempList );
            dailyValues.add(tempDayValue);
            tempWeekDaysList.add(tempDayValue);
            tempMonthDaysList.add(tempDayValue);
            tempList.clear();
        }
        if( tempWeekDaysList.get(0) != null ){
            weeklyValues.add(calculateAverageCalculatedAverageWeatherData(
                    tempWeekDaysList));
            tempWeekDaysList.clear();
        }
        if( !tempMonthDaysList.isEmpty()){
            tempMonthValue = calculateAverageCalculatedAverageWeatherData(
                    tempMonthDaysList);
            monthlyValues.add(tempMonthValue);
            tempYearMonthsList.add(tempMonthValue);
            tempMonthDaysList.clear();
        }
        if( !tempYearMonthsList.isEmpty()){
            yearlyValues.add(calculateAverageCalculatedAverageWeatherData(
                    tempYearMonthsList));
            tempYearMonthsList.clear();
        }        
    }
    
    /**
     * calculateAverageFromXmlWeatherDataPoints - takes an XMLWeatherDataPoint
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
    private CalculatedAverageWeatherData 
        calculateAverageFromXmlWeatherDataPoints(List<XmlWeatherDataPoint> 
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
        //TODO CALCULATE THIS MESS
        
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
        
    private CalculatedAverageWeatherData 
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
        //TODO CALCULATE THIS MESS
        
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
        
        
    public List<CalculatedAverageWeatherData> getDailyCalculations(){
        return dailyValues;
    }   
    
    public List<CalculatedAverageWeatherData> getWeeklyCalculations(){
        return weeklyValues;
    }
    
    public List<CalculatedAverageWeatherData> getMonthlyCalculations(){
        return monthlyValues;
    }
    
    public List<CalculatedAverageWeatherData> getYearlyCalculations() {
        return yearlyValues;
    }
    
    public CalculatedAverageWeatherData getAllDataValues(
            List<XmlWeatherDataPoint> passedInData) {
        allDataValues = (calculateAverageFromXmlWeatherDataPoints(passedInData));
        return allDataValues;
    }
    
    public class CalculatedAverageWeatherData implements AverageWeatherData {
        
        private double averageTemperature;
        private XmlWeatherDataPoint highTempPoint;
        private XmlWeatherDataPoint lowTempPoint;
        private double averageWindSpeed;
        private XmlWeatherDataPoint maxWindGustPoint;
        private WindDirection prevalingWindDirection;
        private double totalRainFall;
        
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
            return this.averageWindSpeed;
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
