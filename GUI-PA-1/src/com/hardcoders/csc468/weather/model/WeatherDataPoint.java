package com.hardcoders.csc468.weather.model;

import com.hardcoders.csc468.weather.graph.DataPoint;
import java.util.Date;

/**
 * Interface representing a single weather data point.
 *
 * @author Daniel Andrus <danieleandrus@mines.sdsmt.edu>
 */
public interface WeatherDataPoint {
    
    /**
     * Gets the time and date the data point was taken.
     * 
     * @return The time and date the data point was taken.
     */
    public Date getTimestamp();

    /**
     * Gets the temperature in degrees Farenheit.
     * 
     * @return The temperature in degrees Farenheit.
     */
    public Double getTemperature();
    
    /**
     * Gets the percent relative humidity.
     * 
     * @return The percent relative humidity.
     */
    public Double getHumidity();
    
    /**
     * Gets the barometric pressure measured in inches of mercury.
     * 
     * @return The barometric pressure measured in inches of mercury.
     */
    public Double getPressure();
    
    /**
     * Gets the wind speed in miles per hour.
     * 
     * @return Gets the wind speed in miles per hour. This value will never be
     * negative.
     */
    public Double getWindSpeed();
    
    /**
     * Gets the wind direction rounded to the nearest 16th cardinal
     * direction (22.5 degrees).
     * 
     * @return The wind direction rounded to the nearest 16th cardinal
     * direction.
     */
    public WindDirection getWindDirection();
    
    /**
     * Gets the highest wind speed since last measurement in miles per
     * hour.
     * 
     * @return The highest wind speed since last measurement.
     */
    public Double getWindGust();
    
    /**
     * Gets the wind chill factor in degrees.
     * 
     * @return Gets the wind chill factor in degrees Farenheit; this value will
     * never be positive.
     */
    public Double getWindChill();
    
    /**
     * Gets the computed heat index.
     * 
     * Calculates the heat index using temperature, humidity, wind speed, and
     * other factors.
     * 
     * @return The calculated heat index.
     */
    public Double getHeatIndex();
    
    /**
     * Gets the UV index.
     * 
     * @return The UV index. This value ranges between 0 and 15.
     */
    public Double getUVIndex();
    
    /**
     * Gets the amount of percipitation since the last measurement
     * rounded to the nearest 0.01 inches.
     * 
     * @return The amount of percipitation since the last measurement rounded
     * to the nearest 0.01 inches.
     */
    public Double getPercipitation();
    
    /**
     * Abstract DataPointAdapter that allows this {@link WeatherDataPoint} class
     * to be usable with {@link Graph}s.
     * 
     * @param <RangeType> The data type of the range of this data type.
     */
    public abstract class DataPointAdapter<RangeType> implements DataPoint<Date, RangeType> {
        
        /**
         * The {@link WeatherDataPoint} object this DataPoint adapter is linked
         * to.
         */
        private final WeatherDataPoint weatherDataPoint;
        
        /**
         * Constructor that takes a non-null WeatherDataPoint object to attach
         * to.
         * 
         * @param weatherDataPoint The {@link WeatherDataPoint} object to link
         * to.
         */
        public DataPointAdapter(WeatherDataPoint weatherDataPoint) {
            if (weatherDataPoint == null) throw new IllegalArgumentException("weatherDataPoint cannot be null");
            this.weatherDataPoint = weatherDataPoint;
        }
        
        @Override
        public Date getDomainPosition() {
            return weatherDataPoint.getTimestamp();
        }

        @Override
        public Double getDomainPercentage(Date lowerBound, Date upperBound) {
            Double time = (double) weatherDataPoint.getTimestamp().getTime() - (double) lowerBound.getTime();
            return (time) / (upperBound.getTime() - lowerBound.getTime());
        }
    }
}
