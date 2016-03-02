package com.hardcoders.csc468.weather;

import com.hardcoders.csc468.weather.XMLImport.XmlWeatherDataPoint;
import com.hardcoders.csc468.weather.model.AverageWeatherData;
import static com.hardcoders.csc468.weather.model.WindDirection.*;
import com.hardcoders.csc468.weather.model.WindDirection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Calculates average temperature and windspeed, high/low temp, max wind speed
 *  and prevalent wind direction
 * 
 * @author Johnny Ackerman
 */
public class AverageData {
    
    //Points passed into the class
    private List<XmlWeatherDataPoint> currentDataPoints;
    
    //List of all daily calculations
    private final List<CalculatedAverageWeatherData> dailyValues;
    
    //List of all weekly calculations
    private final List<CalculatedAverageWeatherData> weeklyValues;
    
    //List of all monthly calculations
    private final List<CalculatedAverageWeatherData> monthlyValues;
    
    //List of all yearly Calculations
    private final List<CalculatedAverageWeatherData> yearlyValues;
    
    //Calculation off all data values
    private CalculatedAverageWeatherData allDataValues;
    
    
    /**
     * Constructor initializes lists
     */
    public AverageData() {
        allDataValues = new CalculatedAverageWeatherData();
        currentDataPoints = new ArrayList<>();
        dailyValues = new ArrayList<>();
        weeklyValues = new ArrayList<>();
        monthlyValues = new ArrayList<>();
        yearlyValues = new ArrayList<>();
    }
    
    /**
     * initializes currentDataPoints with passed in data and processes the 
     *  information
     * 
     * @param passedInData list of XmlWeatherDataPoints
     */
    public void calculateData( List<XmlWeatherDataPoint> passedInData ) {
        //sets datapoints to global-to-class variable
        currentDataPoints = passedInData;
        
        //actually Calculate data
        crunchData();
    }
    
    /**
     * fills in Daily values, Monthly values, weekly values, and yearly values
     *  while calculating all the needed data items 
     * 
     */
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
        int currentWeekOfYear = tempCalendar.get(Calendar.WEEK_OF_YEAR);
        
        //Initializes the current month
        int currentMonth = tempCalendar.get(Calendar.MONTH);
        
        //Initializes the current Year
        int currentYear = tempCalendar.get(Calendar.YEAR);
        
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
            
            //Handles Day List
            if( currentDay != tempCalendar.get(Calendar.DAY_OF_MONTH)){
                currentDay = tempCalendar.get(Calendar.DAY_OF_MONTH);
                tempDayValue = 
                        calculateAverageFromXmlWeatherDataPoints( tempList );
                dailyValues.add(tempDayValue);
                tempWeekDaysList.add(tempDayValue);
                tempMonthDaysList.add(tempDayValue);
                tempList.clear();
            }
            
            //Handles week List (special case)
            if( currentWeekOfYear != tempCalendar.get(Calendar.WEEK_OF_YEAR)){
                currentWeekOfYear = tempCalendar.get(Calendar.WEEK_OF_YEAR);
                weeklyValues.add(calculateAverageCalculatedAverageWeatherData(
                        tempWeekDaysList));
                tempWeekDaysList.clear();
            }
            
            //Handles Month List
            if( currentMonth != tempCalendar.get(Calendar.MONTH)){
                currentMonth = tempCalendar.get(Calendar.MONTH);
                tempMonthValue = calculateAverageCalculatedAverageWeatherData(
                        tempMonthDaysList);
                monthlyValues.add(tempMonthValue);
                tempYearMonthsList.add(tempMonthValue);
                tempMonthDaysList.clear();
            }
            
            // Handles Year List
            if( currentYear != tempCalendar.get(Calendar.YEAR)){
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
        int tempCount = 0;
        int windSpeedCount = 0;
        
        
        WindDirectionCounter prevailingCounter = new WindDirectionCounter();
        
        for( XmlWeatherDataPoint workingPoint : workingList)
        {            
        //Collect temperature
            if(workingPoint.getTemperature() != null){
                tempWorkingAverageDataPoint.averageTemperature 
                        += workingPoint.getTemperature();
                tempCount++;
            }
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
            if(workingPoint.getWindSpeed() != null){
                tempWorkingAverageDataPoint.averageWindSpeed 
                        += workingPoint.getWindSpeed();
                windSpeedCount++;
            }
        
        //Find max windGust with date/time occurrence
            if((tempWorkingAverageDataPoint.getMaxWindGust() == null) ||
                    (tempWorkingAverageDataPoint.getMaxWindGust().getWindGust()
                    < workingPoint.getWindGust()))
            {
                tempWorkingAverageDataPoint.maxWindGustPoint = workingPoint;
            }
        
        //count up prevailing wind direction
        prevailingCounter.count(workingPoint.getWindDirection());
        
        //Collect rainfall
            tempWorkingAverageDataPoint.totalRainFall
                    += workingPoint.getPercipitation();
        }
        
        //Calculate average temperature
        tempWorkingAverageDataPoint.averageTemperature = 
                tempWorkingAverageDataPoint.getAverageTemperature() 
                        / tempCount;
        
        //Calculate average windspeed
        tempWorkingAverageDataPoint.averageWindSpeed = 
                tempWorkingAverageDataPoint.getAverageTemperature() 
                        / windSpeedCount;
        
        //Determine prevailing wind direction
        tempWorkingAverageDataPoint.prevalingWindDirection 
                = prevailingCounter.getPrevailingWind();
        
        return tempWorkingAverageDataPoint;
    }
       
    /**
     * calculateAverageCalculatedAverageWeatherData - takes an 
     *  CalculatedAverageWeatherData point List and calculates the needed 
     *  information ( average temperature, high/low temperature, average wind 
     *  speed, max wind gust, prevailing wind direction and total rainfall.
     *  Then stores that data in a CalculatedAverageWeatherData class
     * 
     * @see CalculatedAverageWeatherData
     * 
     * @param workingList-CalculatedAverageWeatherData list being worked on
     * @return tempWorkingAverageDataPoint - 
     *      filled in CalculatedAverageWeatherData class structure
     */
    private CalculatedAverageWeatherData 
        calculateAverageCalculatedAverageWeatherData(
                List<CalculatedAverageWeatherData> workingList )
    {
        CalculatedAverageWeatherData tempWorkingAverageDataPoint 
                = new CalculatedAverageWeatherData();
        int pointCount = 0;
        
        //used for prevalent wind
        WindDirectionCounter prevailingCounter = new WindDirectionCounter();
        
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
        
        //count up prevailing wind direction
        prevailingCounter.count(workingPoint.getPrevailingWindDirection());
        
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
        
        //Determine prevailing wind direction
        tempWorkingAverageDataPoint.prevalingWindDirection 
                = prevailingCounter.getPrevailingWind();
        
        return tempWorkingAverageDataPoint;
    }
        
    /**
     * Gets the Daily calculations for the given data set
     * 
     * @see CalculatedAverageWeatherData
     * 
     * @return dailyValues - list of CalculatedAverageWeatherData structure
     */    
    public List<CalculatedAverageWeatherData> getDailyCalculations(){
        return dailyValues;
    }   
    
    /**
     * Gets the weekly calculations for the given data set
     * 
     * @see CalculatedAverageWeatherData
     * 
     * @return weeklyValues - list of CalculatedAverageWeatherData structure
     */  
    public List<CalculatedAverageWeatherData> getWeeklyCalculations(){
        return weeklyValues;
    }
    
    /**
     * Gets the monthly calculations for the given data set
     * 
     * @see CalculatedAverageWeatherData
     * 
     * @return monthlyValues - list of CalculatedAverageWeatherData structure
     */  
    public List<CalculatedAverageWeatherData> getMonthlyCalculations(){
        return monthlyValues;
    }
    
    /**
     * Gets the Yearly calculations for the given data set
     * 
     * @see CalculatedAverageWeatherData
     * 
     * @return yearlyValues - list of CalculatedAverageWeatherData structure
     */  
    public List<CalculatedAverageWeatherData> getYearlyCalculations() {
        return yearlyValues;
    }
    
    /**
     * Takes in a data set of XmlWeatherDataPoint and calculates a
     *  CalculatedAverageWeatherData structure
     * 
     * @see CalculatedAverageWeatherData
     * 
     * @return dailyValues - list of CalculatedAverageWeatherData structure
     */  
    public CalculatedAverageWeatherData getAllDataValues(
            List<XmlWeatherDataPoint> passedInData) {
        allDataValues = (calculateAverageFromXmlWeatherDataPoints(passedInData));
        return allDataValues;
    }
    
    /**
     * CalculatedAverageWeatherData class that holds implemention for the
     *  AverageWeatherData abstract class. The data stored here is the
     *  calculated values for temperature, wind speed, wind direction, and
     *  rainfall.
     * 
     * @see AverageWeatherData
     */
    public class CalculatedAverageWeatherData implements AverageWeatherData {
        
        private double averageTemperature;
        private XmlWeatherDataPoint highTempPoint;
        private XmlWeatherDataPoint lowTempPoint;
        private double averageWindSpeed;
        private XmlWeatherDataPoint maxWindGustPoint;
        private WindDirection prevalingWindDirection;
        private double totalRainFall;
        
        /**
         * Constructor - initializes values incase anything is missing.
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
    
    /**
     * Class developed to handle prevailing wind directions for a set of data
     * 
     * @see WindDirection
     * 
     * @author Johnny Ackerman
     */
    public class WindDirectionCounter {
        
        int E, ENE, NEE, NE, NNE, N, NW, NWW, WNW, W, WSW, SWW, SW, SSW, S, SSE,
                SE, SEE, ESE;

        public WindDirectionCounter() {
            reset();
        }
        
        /**
         * if the counter needs to be reset, places all counts down to 0
         */
        public void reset() {
            this.ESE = 0;
            this.SEE = 0;
            this.SE = 0;
            this.SSE = 0;
            this.S = 0;
            this.SSW = 0;
            this.SW = 0;
            this.SWW = 0;
            this.WSW = 0;
            this.W = 0;
            this.WNW = 0;
            this.NWW = 0;
            this.NW = 0;
            this.N = 0;
            this.NNE = 0;
            this.NE = 0;
            this.NEE = 0;
            this.ENE = 0;
            this.E = 0;
        }
        
        /**
         * Increments the number of times any direction has been encountered
         * 
         * @see WindDirection
         * 
         * @param direction - direction being counted
         */
        public void count( WindDirection direction) {
            if( direction == null ){
                return;
            }
            switch (direction) {
                case EAST:
                    E++;
                    break;
                case EAST_NORTH_EAST:
                    ENE++;
                    break;
                case NORTH_EAST_EAST:
                    NEE++;
                    break;
                case NORTH_EAST:
                    NE++;
                    break;
                case NORTH_NORTH_EAST:
                    NNE++;
                    break;
                case NORTH:
                    N++;
                    break;
                case NORTH_WEST:
                    NW++;
                    break;
                case NORTH_WEST_WEST:
                    NWW++;
                    break;
                case WEST_NORTH_WEST:
                    WNW++;
                    break;
                case WEST:
                    W++;
                    break;
                case WEST_SOUTH_WEST:
                    WSW++;
                    break;
                case SOUTH_WEST_WEST:
                    SWW++;
                    break;
                case SOUTH_WEST:
                    SW++;
                    break;
                case SOUTH_SOUTH_WEST:
                    SSW++;
                    break;
                case SOUTH:
                    S++;
                    break;
                case SOUTH_SOUTH_EAST:
                    SSE++;
                    break;
                case SOUTH_EAST:
                    SE++;
                    break;
                case SOUTH_EAST_EAST:
                    SEE++;
                    break;
                case EAST_SOUTH_EAST:
                    ESE++;
                    break;
                default:
                    break;
        
            }
        } 
        
        /**
         * determines which direction was encountered the most
         * 
         * @see WindDirection
         * 
         * @return returns the prevalent WindDirection
         */
        public WindDirection getPrevailingWind() {
            int max = -2953838;
            if (max < E) max = E;
            if (max < ENE) max = ENE;
            if (max < NEE) max = NEE;
            if (max < NE) max = NE;
            if (max < NNE) max = NNE;
            if (max < N) max = N;
            if (max < NNE) max = NNE;
            if (max < NW) max = NW;
            if (max < NWW) max = NWW;
            if (max < WNW) max = WNW;
            if (max < W) max = W;
            if (max < WSW) max = WSW;
            if (max < SWW) max = SWW;
            if (max < SW) max = SW;
            if (max < SSW) max = SSW;
            if (max < S) max = S;
            if (max < SSE) max = SSE;
            if (max < SE) max = SE;
            if (max < SEE) max = SEE;
            if (max < ESE) max = ESE;
            
            if (max == E)  return EAST;
            if (max == ENE) return EAST_NORTH_EAST;
            if (max == NEE) return NORTH_EAST_EAST;
            if (max == NE)  return NORTH_EAST;
            if (max == NNE) return NORTH_NORTH_EAST;
            if (max == N)   return NORTH;
            if (max == NNE) return NORTH_NORTH_EAST;
            if (max == NW) return NORTH_WEST;
            if (max == NWW) return NORTH_WEST_WEST;
            if (max == WNW) return WEST_NORTH_WEST;
            if (max == W)   return WEST;
            if (max == WSW) return WEST_SOUTH_WEST;
            if (max == SWW) return SOUTH_WEST_WEST;
            if (max == SW)  return SOUTH_WEST;
            if (max == SSW) return SOUTH_SOUTH_WEST;
            if (max == S)   return SOUTH;
            if (max == SSE) return SOUTH_SOUTH_EAST;
            if (max == SE)  return SOUTH_EAST;
            if (max == SEE) return SOUTH_EAST_EAST;
            if (max == ESE) return EAST_SOUTH_EAST;
            return null;
        }
    }        
}
