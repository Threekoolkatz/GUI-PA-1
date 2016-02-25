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
     * Gets a {@link Graph} compatible {@link DataPoint} object that
     * uses the temperature field as the range value.
     * 
     * @return {@link DataPoint} object linked to this class' temperature field.
     */
    public TemperatureDataPointAdapter getTemperatureAsDataPoint();
    
    /**
     * Gets the percent relative humidity.
     * 
     * @return The percent relative humidity.
     */
    public Double getHumidity();
    
    /**
     * Gets a {@link Graph} compatible {@link DataPoint} object that uses the
     * humidity field as the range value.
     * 
     * @return {@link DataPoint} object linked to this class' humidity field.
     */
    public HumidityDataPointAdapter getHumidityAsDataPoint();
    
    /**
     * Gets the barometric pressure measured in inches of mercury.
     * 
     * @return The barometric pressure measured in inches of mercury.
     */
    public Double getPressure();
    
    /**
     * Gets a {@link Graph} compatible {@link DataPoint} object that uses the
     * barometric pressure field as the range value.
     * 
     * @return {@link DataPoint} object linked to this class' barometric
     * pressure field.
     */
    public PressureDataPointAdapter getPressureAsDataPoint();
    
    /**
     * Gets the wind speed in miles per hour.
     * 
     * @return Gets the wind speed in miles per hour. This value will never be
     * negative.
     */
    public Double getWindSpeed();
    
    /**
     * Gets a {@link Graph} compatible {@link DataPoint} object that uses the
     * wind speed field as the range value.
     * 
     * @return {@link DataPoint} object linked to this class' wind speed field.
     */
    public WindSpeedDataPointAdapter getWindSpeedAsDataPoint();
    
    /**
     * Gets the wind direction rounded to the nearest 16th cardinal
     * direction (22.5 degrees).
     * 
     * @return The wind direction rounded to the nearest 16th cardinal
     * direction.
     */
    public WindDirection getWindDirection();
    
    /**
     * Gets a {@link Graph} compatible {@link DataPoint} object that uses the
     * wind direction field as the range value.
     * 
     * @return {@link DataPoint} object linked to this class' wind direction
     * field.
     */
    public WindDirectionDataPointAdapter getWindDirectionAsDataPoint();
    
    /**
     * Gets the highest wind speed since last measurement in miles per
     * hour.
     * 
     * @return The highest wind speed since last measurement.
     */
    public Double getWindGust();
    
    /**
     * Gets a {@link Graph} compatible {@link DataPoint} object that uses the
     * wind gust field as the range value.
     * 
     * @return {@link DataPoint} object linked to this class' wind gust field.
     */
    public WindGustDataPointAdapter getWindGustAsDataPoint();
    
    /**
     * Gets the wind chill factor in degrees.
     * 
     * @return Gets the wind chill factor in degrees Farenheit; this value will
     * never be positive.
     */
    public Double getWindChill();
    
    /**
     * Gets a {@link Graph} compatible {@link DataPoint} object that uses the
     * wind chill field as the range value.
     * 
     * @return {@link DataPoint} object linked to this class' wind chill field.
     */
    public WindChillDataPointAdapter getWindChillAsDataPoint();
    
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
     * Gets a {@link Graph} compatible {@link DataPoint} object that uses the
     * heat index field as the range value.
     * 
     * @return {@link DataPoint} object linked to this class' heat index field.
     */
    public HeatIndexDataPointAdapter getHeatIndexAsDataPoint();
    
    /**
     * Gets the UV index.
     * 
     * @return The UV index. This value ranges between 0 and 15.
     */
    public Double getUVIndex();
    
    /**
     * Gets a {@link Graph} compatible {@link DataPoint} object that uses the
     * UV index field as the range value.
     * 
     * @return {@link DataPoint} object linked to this class' UV index field.
     */
    public UVIndexDataPointAdapter getUVIndexAsDataPoint();
    
    /**
     * Gets the amount of percipitation since the last measurement
     * rounded to the nearest 0.01 inches.
     * 
     * @return The amount of percipitation since the last measurement rounded
     * to the nearest 0.01 inches.
     */
    public Double getPercipitation();
    
    /**
     * Gets a {@link Graph} compatible {@link DataPoint} object that uses the
     * percipitation field as the range value.
     * 
     * @return {@link DataPoint} object linked to this class' percipitation
     * field.
     */
    public PercipitationDataPointAdapter getPercipitationAsDataPoint();
    
    /**
     * Abstract DataPointAdapter that allows this {@link WeatherDataPoint} class
     * to be usable with {@link Graph}s.
     * 
     * @param <RangeType> The data type of the range of this data type.
     */
    public abstract class DataPointAdapter<RangeType> implements DataPoint<Double, RangeType> {
        
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
        
        /**
         * Gets the underlying linked {@code WeatherDataPoint} object.
         * 
         * @return The linked {@code WeatherDataPoint}.
         */
        public WeatherDataPoint getLinkedObject() {
            return weatherDataPoint;
        }
        
        @Override
        public Double getDomainPosition() {
            return (double) weatherDataPoint.getTimestamp().getTime();
        }

        @Override
        public Double getDomainPercentage(Double lowerBound, Double upperBound) {
            
            // If any value is null or if bounds are equivalent, place data
            // point in the center
            if (lowerBound == null
                    || upperBound == null
                    || getDomainPosition() == null
                    || lowerBound == upperBound) {
                
                return 0.5;
            }
            
            // Calculate the percentage position across the domain
            return (double) (getDomainPosition() - lowerBound) / (double) (upperBound - lowerBound);
        }
        
        @Override
        public int compareTo(Object o) {
            if (!(o instanceof DataPointAdapter)) {
                return 1;
            } else {
                return getDomainPosition().compareTo(((DataPointAdapter) o).getDomainPosition());
            }
        }
    }
    
    /**
     * Abstract {@link DataPointAdapter} that specifically deals with doubles.
     */
    public abstract class DoubleDataPointAdapter extends DataPointAdapter<Double> {

        /**
         * Constructor that accepts a {@link WeatherDataPoint} to link to.
         * 
         * @param weatherDataPoint The object to link to.
         */
        public DoubleDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }
        
        @Override
        public Double getRangePercentage(Double lowerBound, Double upperBound) {
            
            // If any value is null or the bounds are equivalent, place data
            // point in the center.
            if (lowerBound == null
                    || upperBound == null
                    || getRangeValue() == null
                    || upperBound == lowerBound) {
                
                return 0.5;
            }
            
            // Calculate the percentage position across the range
            return (getRangeValue() - lowerBound) / (upperBound - lowerBound);
        }
    }
    
    /**
     * Temperature {@link DataPointAdapter} to allow this
     * {@link WeatherDataPoint} to be displayed on a {@link Graph}.
     */
    public class TemperatureDataPointAdapter extends DoubleDataPointAdapter {
        
        /**
         * Constructor that accepts a {@link WeatherDataPoint} to link to.
         * 
         * @param weatherDataPoint The object to link to.
         */
        public TemperatureDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }

        @Override
        public Double getRangeValue() {
            return getLinkedObject().getTemperature();
        }
    }
    
    /**
     * Humidity {@link DataPointAdapter} to allow this
     * {@link WeatherDataPoint} to be displayed on a {@link Graph}.
     */
    public class HumidityDataPointAdapter extends DoubleDataPointAdapter {
        
        /**
         * Constructor that accepts a {@link WeatherDataPoint} to link to.
         * 
         * @param weatherDataPoint The object to link to.
         */
        public HumidityDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }

        @Override
        public Double getRangeValue() {
            return getLinkedObject().getHumidity();
        }
    }
    
    /**
     * Barometric pressure {@link DataPointAdapter} to allow this
     * {@link WeatherDataPoint} to be displayed on a {@link Graph}.
     */
    public class PressureDataPointAdapter extends DoubleDataPointAdapter {
        
        /**
         * Constructor that accepts a {@link WeatherDataPoint} to link to.
         * 
         * @param weatherDataPoint The object to link to.
         */
        public PressureDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }

        @Override
        public Double getRangeValue() {
            return getLinkedObject().getPressure();
        }
    }
    
    /**
     * Wind speed {@link DataPointAdapter} to allow this
     * {@link WeatherDataPoint} to be displayed on a {@link Graph}.
     */
    public class WindSpeedDataPointAdapter extends DoubleDataPointAdapter {
        
        /**
         * Constructor that accepts a {@link WeatherDataPoint} to link to.
         * 
         * @param weatherDataPoint The object to link to.
         */
        public WindSpeedDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }

        @Override
        public Double getRangeValue() {
            return getLinkedObject().getWindSpeed();
        }
    }
    
    /**
     * Wind direction {@link DataPointAdapter} to allow this
     * {@link WeatherDataPoint} to be displayed on a {@link Graph}.
     */
    public class WindDirectionDataPointAdapter extends DataPointAdapter<WindDirection> {
        
        public WindDirectionDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }
        
        @Override
        public WindDirection getRangeValue() {
            return getLinkedObject().getWindDirection();
        }
        
        @Override
        public Double getRangePercentage(WindDirection lowerBound, WindDirection upperBound) {
            if (lowerBound.ordinal() == upperBound.ordinal()) return 0.0;
            return (getRangeValue().ordinal() - lowerBound.ordinal()) / (double) (upperBound.ordinal() - lowerBound.ordinal());
        }
    }
    
    /**
     * Wind gust {@link DataPointAdapter} to allow this
     * {@link WeatherDataPoint} to be displayed on a {@link Graph}.
     */
    public class WindGustDataPointAdapter extends DoubleDataPointAdapter {
        
        /**
         * Constructor that accepts a {@link WeatherDataPoint} to link to.
         * 
         * @param weatherDataPoint The object to link to.
         */
        public WindGustDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }

        @Override
        public Double getRangeValue() {
            return getLinkedObject().getWindGust();
        }
    }
    
    /**
     * Wind chill {@link DataPointAdapter} to allow this
     * {@link WeatherDataPoint} to be displayed on a {@link Graph}.
     */
    public class WindChillDataPointAdapter extends DoubleDataPointAdapter {
        
        /**
         * Constructor that accepts a {@link WeatherDataPoint} to link to.
         * 
         * @param weatherDataPoint The object to link to.
         */
        public WindChillDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }

        @Override
        public Double getRangeValue() {
            return getLinkedObject().getWindChill();
        }
    }
    
    /**
     * Heat index {@link DataPointAdapter} to allow this
     * {@link WeatherDataPoint} to be displayed on a {@link Graph}.
     */
    public class HeatIndexDataPointAdapter extends DoubleDataPointAdapter {
        
        /**
         * Constructor that accepts a {@link WeatherDataPoint} to link to.
         * 
         * @param weatherDataPoint The object to link to.
         */
        public HeatIndexDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }

        @Override
        public Double getRangeValue() {
            return getLinkedObject().getHeatIndex();
        }
    }
    
    /**
     * UV index {@link DataPointAdapter} to allow this
     * {@link WeatherDataPoint} to be displayed on a {@link Graph}.
     */
    public class UVIndexDataPointAdapter extends DoubleDataPointAdapter {
        
        /**
         * Constructor that accepts a {@link WeatherDataPoint} to link to.
         * 
         * @param weatherDataPoint The object to link to.
         */
        public UVIndexDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }

        @Override
        public Double getRangeValue() {
            return getLinkedObject().getUVIndex();
        }
    }
    
    /**
     * Percipitation {@link DataPointAdapter} to allow this
     * {@link WeatherDataPoint} to be displayed on a {@link Graph}.
     */
    public class PercipitationDataPointAdapter extends DoubleDataPointAdapter {
        
        /**
         * Constructor that accepts a {@link WeatherDataPoint} to link to.
         * 
         * @param weatherDataPoint The object to link to.
         */
        public PercipitationDataPointAdapter(WeatherDataPoint weatherDataPoint) {
            super(weatherDataPoint);
        }

        @Override
        public Double getRangeValue() {
            return getLinkedObject().getPercipitation();
        }
    }
}
