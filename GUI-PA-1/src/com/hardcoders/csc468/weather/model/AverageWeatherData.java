package com.hardcoders.csc468.weather.model;

import com.hardcoders.csc468.weather.XMLImport.XmlWeatherDataPoint;

/**
 * Interface representing weather statistics over a given amount
 *      of time.
 * 
 * @author Johnny Ackerman
 */
public interface AverageWeatherData {
    
    /**
     * Gets the average temperature over a given amount of time
     * 
     * @return Average temperature
     */
    public double getAverageTemperature();
    
    /**
     * Gets the lowest temperature over a given time. Returns
     *  the whole dataPoint because the dataPoint holds the
     *  date and time that the value was taken at.
     * 
     * @return XmlWeatherDataPoint that holds the lowest temp
     */
    public XmlWeatherDataPoint getLowTemperature();
    
    /**
     * Gets the highest temperature over a given time. Returns
     *  the whole dataPoint because the dataPoint holds the
     *  date and time that the value was taken at.
     * 
     * @return XmlWeatherDataPoint that holds the highest temp
     */
    public XmlWeatherDataPoint getHighTemperature();
    
    /**
     * Gets the average Wind Speed over a given amount of time
     * 
     * @return Average Wind Speed
     */
    public double getAverageWindSpeed();
    
    /**
     * Gets the maximum Wind Speed over a given time. Returns
     *  the whole dataPoint because the dataPoint holds the
     *  date and time that the value was taken at.
     * 
     * @return XmlWeatherDataPoint that holds the Max Wind Speed
     */
    public XmlWeatherDataPoint getMaxWindSpeed();
    
    /**
     * Gets the Prevailing Wind Direction over a period of time
     * 
     * @see WindDirection - Enumerated type for wind directions
     * 
     * @return WindDirection Wind Direction
     */
    public WindDirection getPrevailingWindDirection();
    
    /**
     * Gets the Total Rain Fall for a given period of time
     * @return 
     */
    public double getTotalRainFall();
}
