package com.hardcoders.csc468.weather;

import com.hardcoders.csc468.weather.graph.RealInteractiveLineGraph;
import com.hardcoders.csc468.weather.model.WeatherDataPoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 */
public class WeathermanLineGraph extends RealInteractiveLineGraph {

    private final List<WeatherDataPoint> dataPoints;
    private final List<WeatherDataPoint.DoubleDataPointAdapter>[] dataAdapters;
    
    private static final int DATA_FIELDS         = 9;
    private static final int FIELD_TEMPERATURE   = 0;
    private static final int FIELD_HUMIDITY      = 1;
    private static final int FIELD_PRESSURE      = 2;
    private static final int FIELD_WIND_SPEED    = 3;
    private static final int FIELD_WIND_GUST     = 4;
    private static final int FIELD_WIND_CHILL    = 5;
    private static final int FIELD_HEAT_INDEX    = 6;
    private static final int FIELD_UV_INDEX      = 7;
    private static final int FIELD_PERCIPITATION = 8;
    
    public WeathermanLineGraph() {
        super();
        
        dataPoints = new ArrayList<>();
        dataAdapters = new List[DATA_FIELDS];
        for (int i = 0; i < DATA_FIELDS; i++) {
            dataAdapters[i] = new ArrayList<>();
        }
    }
    
    public void addWeatherDataPoint(WeatherDataPoint dataPoint) {
        if (dataPoint == null) return;
        
        addWeatherDataPoints(Collections.singleton(dataPoint));
    }
    
    public void addWeatherDataPoints(Collection<? extends WeatherDataPoint> dataPoints) {
        if (dataPoints == null || dataPoints.isEmpty()) {
            return;
        }
        
        this.dataPoints.addAll(dataPoints);
        
        for (WeatherDataPoint dataPoint : dataPoints) {
            dataAdapters[FIELD_TEMPERATURE]  .add(dataPoint.getTemperatureAsDataPoint());
            dataAdapters[FIELD_HUMIDITY]     .add(dataPoint.getHumidityAsDataPoint());
            dataAdapters[FIELD_PRESSURE]     .add(dataPoint.getPressureAsDataPoint());
            dataAdapters[FIELD_WIND_SPEED]   .add(dataPoint.getWindSpeedAsDataPoint());
            dataAdapters[FIELD_WIND_GUST]    .add(dataPoint.getWindGustAsDataPoint());
            dataAdapters[FIELD_WIND_CHILL]   .add(dataPoint.getWindChillAsDataPoint());
            dataAdapters[FIELD_HEAT_INDEX]   .add(dataPoint.getHeatIndexAsDataPoint());
            dataAdapters[FIELD_UV_INDEX]     .add(dataPoint.getUVIndexAsDataPoint());
            dataAdapters[FIELD_PERCIPITATION].add(dataPoint.getPercipitationAsDataPoint());
        }
    }
    
    public WeatherDataPoint getSelectedDataPoint() {
        return null;
    }
    
    public void showTemperatureData() {
        
    }
    
    public void showHumidityData() {
        
    }
    
    public void showPressureData() {
        
    }
    
    public void showWindSpeedData() {
        
    }
    
    public void showWindGustData() {
        
    }
    
    public void showWindChillData() {
        
    }
    
    public void showHeatIndexData() {
        
    }
    
    public void showUVIndexData() {
        
    }
    
    public void showPercipitationData() {
        
    }
 }
