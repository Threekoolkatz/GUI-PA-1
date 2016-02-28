package com.hardcoders.csc468.weather;

import com.hardcoders.csc468.weather.graph.DataPoint;
import com.hardcoders.csc468.weather.graph.RealInteractiveLineGraph;
import com.hardcoders.csc468.weather.model.WeatherDataPoint;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 */
public class WeathermanLineGraph extends RealInteractiveLineGraph {

    private final List<WeatherDataPoint> dataPoints;
    private final List<WeatherDataPoint.DoubleDataPointAdapter>[] dataAdapters;
    private       Double   domainMinValue;
    private       Double   domainMaxValue;
    private final Double[] rangeMinValue;
    private final Double[] rangeMaxValue;
    private       Double   domainLowerBound;
    private       Double   domainUpperBound;
    private final Double[] rangeLowerBound;
    private final Double[] rangeUpperBound;
    
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
    
    private int activeField;
    private WeatherDataPoint selectedDataPoint;
    private WeatherDataPoint highlightedDataPoint;
    private boolean isDragging;
    
    public WeathermanLineGraph() {
        super();
        
        dataPoints = new ArrayList<>();
        dataAdapters = new List[DATA_FIELDS];
        domainMinValue = null;
        domainMaxValue = null;
        rangeMinValue = new Double[DATA_FIELDS];
        rangeMaxValue = new Double[DATA_FIELDS];
        domainLowerBound = null;
        domainUpperBound = null;
        rangeLowerBound = new Double[DATA_FIELDS];
        rangeUpperBound = new Double[DATA_FIELDS];
        
        for (int i = 0; i < DATA_FIELDS; i++) {
            dataAdapters[i] = new ArrayList<>();
            rangeMinValue[i] = null;
            rangeMaxValue[i] = null;
            rangeLowerBound[i] = null;
            rangeUpperBound[i] = null;
        }
        
        activeField = FIELD_TEMPERATURE;
        selectedDataPoint = null;
        highlightedDataPoint = null;
        isDragging = false;
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
            
            // Adjust domain max/min
            double timestamp = (double) dataPoint.getTimestamp().getTime();
            if (timestamp < domainMinValue) domainMinValue = timestamp;
            if (timestamp > domainMaxValue) domainMaxValue = timestamp;
            
            registerDataAdapter(FIELD_TEMPERATURE, dataPoint.getTemperatureAsDataPoint());
            registerDataAdapter(FIELD_HUMIDITY, dataPoint.getHumidityAsDataPoint());
            registerDataAdapter(FIELD_PRESSURE, dataPoint.getPressureAsDataPoint());
            registerDataAdapter(FIELD_WIND_SPEED, dataPoint.getWindSpeedAsDataPoint());
            registerDataAdapter(FIELD_WIND_GUST, dataPoint.getWindGustAsDataPoint());
            registerDataAdapter(FIELD_WIND_CHILL, dataPoint.getWindChillAsDataPoint());
            registerDataAdapter(FIELD_HEAT_INDEX, dataPoint.getHeatIndexAsDataPoint());
            registerDataAdapter(FIELD_UV_INDEX, dataPoint.getUVIndexAsDataPoint());
            registerDataAdapter(FIELD_PERCIPITATION, dataPoint.getPercipitationAsDataPoint());
        }
        
        forceRedrawLater();
    }
    
    private void registerDataAdapter(final int FIELD, WeatherDataPoint.DoubleDataPointAdapter adapter) {
        double value = adapter.getRangeValue();
        
        dataAdapters[FIELD].add(adapter);
        if (rangeMinValue[FIELD] == null || value < rangeMinValue[FIELD]) rangeMinValue[FIELD] = value;
        if (rangeMaxValue[FIELD] == null || value > rangeMaxValue[FIELD]) rangeMaxValue[FIELD] = value;
    }
    
    public WeatherDataPoint getSelectedDataPoint() {
        return null;
    }
    
    public void showTemperatureData() {
        setActiveField(FIELD_TEMPERATURE);
    }
    
    public void showHumidityData() {
        setActiveField(FIELD_HUMIDITY);
    }
    
    public void showPressureData() {
        setActiveField(FIELD_PRESSURE);
    }
    
    public void showWindSpeedData() {
        setActiveField(FIELD_WIND_SPEED);
    }
    
    public void showWindGustData() {
        setActiveField(FIELD_WIND_GUST);
    }
    
    public void showWindChillData() {
        setActiveField(FIELD_WIND_CHILL);
    }
    
    public void showHeatIndexData() {
        setActiveField(FIELD_HEAT_INDEX);
    }
    
    public void showUVIndexData() {
        setActiveField(FIELD_UV_INDEX);
    }
    
    public void showPercipitationData() {
        setActiveField(FIELD_PERCIPITATION);
    }
    
    private void setActiveField(final int FIELD) {
        
        // Short circuit if attempting to switch to currently active field
        if (activeField == FIELD) return;
        
        activeField = FIELD;
        forceRedrawLater();
    }
    
    private int getActiveField() {
        return activeField;
    }
    
    
    @Override
    public List<? extends DataPoint<Double, Double>> getDataPoints() {
        return new ArrayList<>(dataAdapters[getActiveField()]);
    }
    
    @Override
    public Double getDomainLowerBound() {
        super.getDomainLowerBound();
        return (domainLowerBound == null ? domainMinValue : domainLowerBound);
    }
    
    @Override
    public Double getDomainUpperBound() {
        super.getDomainUpperBound();
        return (domainUpperBound == null ? domainMaxValue : domainUpperBound);
    }
    
    @Override
    public Double getRangeLowerBound() {
        super.getRangeLowerBound();
        int field = getActiveField();
        return (rangeLowerBound[field] == null ? rangeMinValue[field] : rangeLowerBound[field]);
    }
    
    @Override
    public Double getRangeUpperBound() {
        super.getRangeUpperBound();
        int field = getActiveField();
        return (rangeUpperBound[field] == null ? rangeMaxValue[field] : rangeUpperBound[field]);
    }
    
    @Override
    public void setDomainLowerBound(Double bound) {
        super.setDomainLowerBound(bound);
        domainLowerBound = bound;
    }
    
    @Override
    public void setDomainUpperBound(Double bound) {
        super.setDomainUpperBound(bound);
        domainUpperBound = bound;
    }
    
    @Override
    public void setRangeLowerBound(Double bound) {
        super.setRangeLowerBound(bound);
        rangeLowerBound[getActiveField()] = bound;
    }
    
    @Override
    public void setRangeUpperBound(Double bound) {
        super.setRangeUpperBound(bound);
        rangeUpperBound[getActiveField()] = bound;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (highlightedDataPoint != null) {
            selectedDataPoint = highlightedDataPoint;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        isDragging = false;
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        selectedDataPoint = null;
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        
        if (isDragging) return;
        
        // Short-circuit if no data points are registered
        List<WeatherDataPoint.DoubleDataPointAdapter> adapters = dataAdapters[getActiveField()];
        if (adapters.isEmpty()) return;
        
        int x = e.getX();
        
        // Translate x coordinate to approximate time
        double lowerBound = getDomainLowerBound();
        double upperBound = getDomainUpperBound();
        double domainVal = lowerBound + (upperBound - lowerBound) * ((double) x / (double) getWidth());
        
        // Binary search for nearest selected data point
        int top, bot, mid;
        top = adapters.size() - 1;
        bot = 0;
        do {
            mid = (top + bot) / 2;
            double midDomain = adapters.get(mid).getDomainPosition();
            if (midDomain == domainVal) {
                break;
            } else if (midDomain < domainVal) {
                bot = mid + 1;
            } else if (midDomain > domainVal) {
                top = mid - 1;
            }
        } while (bot <= top);
        
        // Search immediately surrounding data points for nearest highlighted point
        int highlighted = mid;
        WeatherDataPoint.DoubleDataPointAdapter candidates[] = {null, null, null};
        
        // Find point just below
        for (int i = highlighted - 1; i >= 0 && i < adapters.size() && candidates[0] == null; i--) {
            if (adapters.get(i).getRangeValue() != null) {
                candidates[0] = adapters.get(i);
            }
        }
        
        // Set point just at (if value is not null)
        if (highlighted >= 0 && highlighted < adapters.size()
                && adapters.get(highlighted).getRangeValue() != null) {
            candidates[1] = adapters.get(highlighted);
        }
        
        // Find point just above
        for (int i = highlighted + 1; i >= 0 && i < adapters.size() && candidates[2] == null; i++) {
            if (adapters.get(i).getRangeValue() != null) {
                candidates[2] = adapters.get(i);
            }
        }
        
        // Determine which candidate point is nearest to the cursor
        WeatherDataPoint.DoubleDataPointAdapter highlightedAdapter = null;
        for (WeatherDataPoint.DoubleDataPointAdapter adapter : candidates) {
            
            if (adapter == null) continue;
            
            if (highlightedAdapter == null
                    || Math.abs(domainVal - adapter.getDomainPosition()) < Math.abs(domainVal - highlightedAdapter.getDomainPosition())) {
                highlightedAdapter = adapter;
            }
        }
        
        // Only mark point as highlighted if it is visible
        if (highlightedAdapter == null) return;
        double pDomain = highlightedAdapter.getDomainPercentage(domainLowerBound, domainUpperBound);
        double pRange  = highlightedAdapter.getRangePercentage(getRangeLowerBound(), getRangeUpperBound());
        if (pDomain >= 0.0 && pDomain <= 1.0 && pRange >= 0.0 && pRange <= 1.0) {
            this.highlightedDataPoint = highlightedAdapter.getLinkedObject();
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        isDragging = true;
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        WeatherDataPoint points[] = {selectedDataPoint, highlightedDataPoint};
        
        for (WeatherDataPoint dataPoint : points) {
            if (dataPoint == null) continue;

            // Get adapter of specific data point
            WeatherDataPoint.DoubleDataPointAdapter adapter;
            switch (getActiveField()) {
                case FIELD_TEMPERATURE:
                    adapter = dataPoint.getTemperatureAsDataPoint();
                    break;

                case FIELD_HUMIDITY:
                    adapter = dataPoint.getHumidityAsDataPoint();
                    break;

                case FIELD_PRESSURE:
                    adapter = dataPoint.getPressureAsDataPoint();
                    break;

                case FIELD_WIND_SPEED:
                    adapter = dataPoint.getWindSpeedAsDataPoint();
                    break;

                case FIELD_WIND_GUST:
                    adapter = dataPoint.getWindGustAsDataPoint();
                    break;

                case FIELD_WIND_CHILL:
                    adapter = dataPoint.getWindChillAsDataPoint();
                    break;

                case FIELD_HEAT_INDEX:
                    adapter = dataPoint.getHeatIndexAsDataPoint();
                    break;

                case FIELD_UV_INDEX:
                    adapter = dataPoint.getUVIndexAsDataPoint();
                    break;

                case FIELD_PERCIPITATION:
                    adapter = dataPoint.getPercipitationAsDataPoint();
                    break;

                default:
                    adapter = null;
                    break;
            }

            if (adapter != null && adapter.getRangeValue() != null) {
                int x = (int) (getWidth() * adapter.getDomainPercentage(getDomainLowerBound(), getDomainUpperBound()));
                g.drawLine(x, 0, x, getHeight());
            }
        }
        
    }
}
