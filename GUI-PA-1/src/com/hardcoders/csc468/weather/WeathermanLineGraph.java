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
 * Handles the Line Graph. Uses multiple listeners to update the graph
 *  whenever a change occurs. Also allows for clicking on the graph to show
 *  data for a given point, as well as displaying tool tips.
 * 
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
    
    /**
     * Initializes needed graph data
     */
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
    
    /**
     * Adds the information of a single dataPoint to the graph
     * @param dataPoint 
     */
    public void addWeatherDataPoint(WeatherDataPoint dataPoint) {
        if (dataPoint == null) return;
        
        addWeatherDataPoints(Collections.singleton(dataPoint));
    }
    
    /**
     * Takes a list of dataPoints and registers their data to the graph.
     * 
     * @param dataPoints 
     */
    public void addWeatherDataPoints(Collection<? extends WeatherDataPoint> dataPoints) {
        if (dataPoints == null || dataPoints.isEmpty()) {
            return;
        }
        
        this.dataPoints.addAll(dataPoints);
        
        for (WeatherDataPoint dataPoint : dataPoints) {
            
            // Adjust domain max/min
            double timestamp = (double) dataPoint.getTimestamp().getTime();
            if (domainMinValue == null || timestamp < domainMinValue) domainMinValue = timestamp;
            if (domainMaxValue == null || timestamp > domainMaxValue) domainMaxValue = timestamp;
            
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
    
    /**
     * Registers a single field to the graph for display
     * @param FIELD
     * @param adapter 
     */
    private void registerDataAdapter(final int FIELD, WeatherDataPoint.DoubleDataPointAdapter adapter) {
        double value = adapter.getRangeValue();
        
        dataAdapters[FIELD].add(adapter);
        if (rangeMinValue[FIELD] == null || value < rangeMinValue[FIELD]) rangeMinValue[FIELD] = value;
        if (rangeMaxValue[FIELD] == null || value > rangeMaxValue[FIELD]) rangeMaxValue[FIELD] = value;
    }
    
    /**
     * returns the information at the currently selected data point.
     * 
     * @return 
     */
    public WeatherDataPoint getSelectedDataPoint() {
        return selectedDataPoint;
    }
    
    /**
     * Displays the data related to temperature
     */
    public void showTemperatureData() {
        setActiveField(FIELD_TEMPERATURE);
    }
    
    /**
     * Displays the data related to humidity
     */
    public void showHumidityData() {
        setActiveField(FIELD_HUMIDITY);
    }
    
    /**
     * Displays the data related to barometric pressure
     */
    public void showPressureData() {
        setActiveField(FIELD_PRESSURE);
    }
    
    /**
     * Displays the data related to Wind Speed
     */
    public void showWindSpeedData() {
        setActiveField(FIELD_WIND_SPEED);
    }
    
    /**
     * Displays the data related to wind gusts
     */
    public void showWindGustData() {
        setActiveField(FIELD_WIND_GUST);
    }
    
    /**
     * Displays the data related to wind chill
     */
    public void showWindChillData() {
        setActiveField(FIELD_WIND_CHILL);
    }
    
    /**
     * Displays the data related to the heat index
     */
    public void showHeatIndexData() {
        setActiveField(FIELD_HEAT_INDEX);
    }
    
    /**
     * Displays the data related to the UV index
     */
    public void showUVIndexData() {
        setActiveField(FIELD_UV_INDEX);
    }
    
    /**
     * Displays the data related to rainfall
     */
    public void showPercipitationData() {
        setActiveField(FIELD_PERCIPITATION);
    }
    
    /**
     * Changes the graph to display any data related to the passed in field
     * 
     * @param FIELD 
     */
    private void setActiveField(final int FIELD) {
        
        // Short circuit if attempting to switch to currently active field
        if (activeField == FIELD) return;
        
        activeField = FIELD;
        
        setDomainUpperBound(null);
        setDomainLowerBound(null);
        setRangeUpperBound(null);
        setRangeLowerBound(null);
        
        forceRedrawLater();
    }
    
    /**
     * returns the currently active field
     * 
     * @return activeField
     */
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
        
        Double lowerBound = (domainLowerBound == null ? domainMinValue : domainLowerBound);
        Double upperBound = (domainUpperBound == null ? domainMaxValue : domainUpperBound);
        
        if (lowerBound != null && upperBound != null
                && upperBound.compareTo(lowerBound) < 0) {
            return upperBound;
        } else {
            return lowerBound;
        }
    }
    
    @Override
    public Double getDomainUpperBound() {
        super.getDomainUpperBound();
        
        Double lowerBound = (domainLowerBound == null ? domainMinValue : domainLowerBound);
        Double upperBound = (domainUpperBound == null ? domainMaxValue : domainUpperBound);
        
        if (lowerBound != null && upperBound != null
                && upperBound.compareTo(lowerBound) < 0) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
    
    @Override
    public Double getRangeLowerBound() {
        super.getRangeLowerBound();
        int field = getActiveField();
        
        Double lowerBound = (rangeLowerBound[field] == null ? rangeMinValue[field] : rangeLowerBound[field]);
        Double upperBound = (rangeUpperBound[field] == null ? rangeMaxValue[field] : rangeUpperBound[field]);
        
        if (lowerBound != null && upperBound != null
                && upperBound.compareTo(lowerBound) < 0) {
            return upperBound;
        } else {
            return lowerBound;
        }
    }
    
    @Override
    public Double getRangeUpperBound() {
        super.getRangeUpperBound();
        int field = getActiveField();
        
        Double lowerBound = (rangeLowerBound[field] == null ? rangeMinValue[field] : rangeLowerBound[field]);
        Double upperBound = (rangeUpperBound[field] == null ? rangeMaxValue[field] : rangeUpperBound[field]);
        
        if (lowerBound != null && upperBound != null
                && upperBound.compareTo(lowerBound) < 0) {
            return lowerBound;
        } else {
            return upperBound;
        }
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
            forceRedrawLater();
            notifyListeners();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isDragging = false;
        updateHighlightedDataPoint(e.getX());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        super.mouseEntered(e);
        isDragging = false;
        updateHighlightedDataPoint(e.getX());
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e);
        highlightedDataPoint = null;
        repaint();
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        
        updateHighlightedDataPoint(e.getX());
        if(highlightedDataPoint != null)
            this.setToolTipText(this.displayDataPoint(highlightedDataPoint));
    }
    
    // Author:  Mack Smith
    // Returns a string with all the data of a single weather data point
    public String displayDataPoint(WeatherDataPoint p) {
        
        String tooltip = new String();
        
        tooltip = "<html>";
        if(p.getTemperature() != null)
        {
            tooltip += "Temperature: " + p.getTemperature().toString() + "<br>";
        }
        if(p.getHumidity() != null)
            tooltip += "Humidity: " + p.getHumidity().toString() + "<br>";
        if(p.getPercipitation() != null)
            tooltip += "Rainfall: " + p.getPercipitation().toString() + "<br>";
        if(p.getPressure() != null )
            tooltip += "Pressure: " + p.getPressure().toString() + "<br>";
        if(p.getUVIndex() != null)
            tooltip += "UV Index: " + p.getUVIndex().toString() + "<br>";
        if(p.getHeatIndex() != null)
            tooltip += "Heat Index: " + p.getHeatIndex().toString() + "<br>";
        if(p.getWindSpeed() != null)
            tooltip += "Wind Speed: " + p.getWindSpeed().toString() + "<br>";
        if(p.getWindGust() != null)
            tooltip += "Wind Gust: " + p.getWindGust().toString() + "<br>";
        if(p.getWindChill() != null)
            tooltip += "Wind Chill: " + p.getWindChill().toString() + "<br>";
        if(p.getWindDirection() != null)
            tooltip += "Wind Direction: " + p.getWindDirection().toString() + "<br>";
        tooltip += "</html>";
        return tooltip;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        isDragging = true;
        updateHighlightedDataPoint(e.getX());
    }
    
    /**
     * Handles mouse click events. If mouse click is dragged, it passes
     *  it to a different handler. Otherwise, it searches for the nearest point
     *  on the graph in relation to the click, and passes that information to
     *  the other display handlers.
     * 
     * @see WeathermanWindow
     * 
     * @param mouseX 
     */
    private void updateHighlightedDataPoint(int mouseX) {
        
        // Completely dispell highlighted data point if dragging
        if (isDragging) {
            highlightedDataPoint = null;
            return;
        }
        
        // Short-circuit if no data points are registered
        List<WeatherDataPoint.DoubleDataPointAdapter> adapters = dataAdapters[getActiveField()];
        if (adapters.isEmpty()) return;
        
        int x = mouseX;
        
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
        if (highlightedAdapter != null) {
            double pDomain = highlightedAdapter.getDomainPercentage(domainLowerBound, domainUpperBound);
            double pRange  = highlightedAdapter.getRangePercentage(getRangeLowerBound(), getRangeUpperBound());
            if (pDomain < 0.0 || pDomain > 1.0 || pRange < 0.0 || pRange > 1.0) {
                highlightedAdapter = null;
            }
        }
        this.highlightedDataPoint = (highlightedAdapter != null ? highlightedAdapter.getLinkedObject() : null);
        
        repaint();
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        WeatherDataPoint points[] = {selectedDataPoint, highlightedDataPoint};
        
        for (int i  = 0; i < points.length; i++) {
            WeatherDataPoint dataPoint = points[i];
            
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

            // Draw vertical line at highlighted/selected point
            if (adapter != null && adapter.getRangeValue() != null) {
                int x = (int) (getWidth() * adapter.getDomainPercentage(getDomainLowerBound(), getDomainUpperBound()));
                int y = (int) (getHeight() * (1.0 - adapter.getRangePercentage(getRangeLowerBound(), getRangeUpperBound())));
                g.drawLine(x, 0, x, getHeight());
                
                // Draw bigger circle around selected point
                if (i == 0) {
                    g.drawOval(x - 4, y - 4, 8, 8);
                }
            }
        }
    }
}
