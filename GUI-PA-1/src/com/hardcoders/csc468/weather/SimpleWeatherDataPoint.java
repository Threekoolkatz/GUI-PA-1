package com.hardcoders.csc468.weather;

import com.hardcoders.csc468.weather.model.WeatherDataPoint;
import com.hardcoders.csc468.weather.model.WindDirection;
import java.util.Date;

/**
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 */
public class SimpleWeatherDataPoint implements WeatherDataPoint {

    private final Date timestamp;
    private final Double data;
    
    
    public SimpleWeatherDataPoint(Date timestamp, Double data) {
        this.timestamp = timestamp;
        this.data = data;
    }
    
    
    @Override
    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public Double getTemperature() {
        return data;
    }

    @Override
    public TemperatureDataPointAdapter getTemperatureAsDataPoint() {
        return new TemperatureDataPointAdapter(this);
    }

    @Override
    public Double getHumidity() {
        return data;
    }

    @Override
    public HumidityDataPointAdapter getHumidityAsDataPoint() {
        return new HumidityDataPointAdapter(this);
    }

    @Override
    public Double getPressure() {
        return data;
    }

    @Override
    public PressureDataPointAdapter getPressureAsDataPoint() {
        return new PressureDataPointAdapter(this);
    }

    @Override
    public Double getWindSpeed() {
        return data;
    }

    @Override
    public WindSpeedDataPointAdapter getWindSpeedAsDataPoint() {
        return new WindSpeedDataPointAdapter(this);
    }

    @Override
    public WindDirection getWindDirection() {
        return WindDirection.NORTH;
    }

    @Override
    public WindDirectionDataPointAdapter getWindDirectionAsDataPoint() {
        return new WindDirectionDataPointAdapter(this);
    }

    @Override
    public Double getWindGust() {
        return data;
    }

    @Override
    public WindGustDataPointAdapter getWindGustAsDataPoint() {
        return new WindGustDataPointAdapter(this);
    }

    @Override
    public Double getWindChill() {
        return data;
    }

    @Override
    public WindChillDataPointAdapter getWindChillAsDataPoint() {
        return new WindChillDataPointAdapter(this);
    }

    @Override
    public Double getHeatIndex() {
        return data;
    }

    @Override
    public HeatIndexDataPointAdapter getHeatIndexAsDataPoint() {
        return new HeatIndexDataPointAdapter(this);
    }

    @Override
    public Double getUVIndex() {
        return data;
    }

    @Override
    public UVIndexDataPointAdapter getUVIndexAsDataPoint() {
         return new UVIndexDataPointAdapter(this);
    }

    @Override
    public Double getPercipitation() {
        return data;
    }

    @Override
    public PercipitationDataPointAdapter getPercipitationAsDataPoint() {
        return new PercipitationDataPointAdapter(this);
    }
}
