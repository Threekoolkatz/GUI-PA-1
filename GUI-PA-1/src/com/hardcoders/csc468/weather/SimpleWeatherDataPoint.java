package com.hardcoders.csc468.weather;

import com.hardcoders.csc468.weather.model.WeatherDataPoint;
import com.hardcoders.csc468.weather.model.WindDirection;
import java.util.Date;

/**
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 */
public class SimpleWeatherDataPoint implements WeatherDataPoint {

    private final Date timestamp;
    private final Double temperature;
    
    
    public SimpleWeatherDataPoint(Date timestamp, Double temperature) {
        this.timestamp = timestamp;
        this.temperature = temperature;
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

    @Override
    public Double getHumidity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HumidityDataPointAdapter getHumidityAsDataPoint() {
        return new HumidityDataPointAdapter(this);
    }

    @Override
    public Double getPressure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PressureDataPointAdapter getPressureAsDataPoint() {
        return new PressureDataPointAdapter(this);
    }

    @Override
    public Double getWindSpeed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WindSpeedDataPointAdapter getWindSpeedAsDataPoint() {
        return new WindSpeedDataPointAdapter(this);
    }

    @Override
    public WindDirection getWindDirection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WindDirectionDataPointAdapter getWindDirectionAsDataPoint() {
        return new WindDirectionDataPointAdapter(this);
    }

    @Override
    public Double getWindGust() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WindGustDataPointAdapter getWindGustAsDataPoint() {
        return new WindGustDataPointAdapter(this);
    }

    @Override
    public Double getWindChill() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public WindChillDataPointAdapter getWindChillAsDataPoint() {
        return new WindChillDataPointAdapter(this);
    }

    @Override
    public Double getHeatIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HeatIndexDataPointAdapter getHeatIndexAsDataPoint() {
        return new HeatIndexDataPointAdapter(this);
    }

    @Override
    public Double getUVIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UVIndexDataPointAdapter getUVIndexAsDataPoint() {
         return new UVIndexDataPointAdapter(this);
    }

    @Override
    public Double getPercipitation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PercipitationDataPointAdapter getPercipitationAsDataPoint() {
        return new PercipitationDataPointAdapter(this);
    }
    
}
