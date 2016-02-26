package com.hardcoders.csc468.weather.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.JPanel;

/**
 * A basic graph class that has methods for adding data points, setting
 * display bounds, and drawing.
 * 
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 * 
 * @param <DomainType>
 * @param <RangeType>
 */
public abstract class Graph<DomainType extends Comparable, RangeType extends Comparable> extends JPanel {
    
    
    /**
     * List of registered listeners.
     */
    private final List<GraphListener> listeners;
    
    /**
     * Flag indicating that the data stored in this graph is dirty or that the
     * data bounds are dirty.
     */
    private boolean dataDirty;
    
    /**
     * Internal stored list of data points to display.
     */
    private final List<DataPoint<DomainType, RangeType>> dataPoints;
    
    /**
     * The upper bound on the domain (horizontal) axis to display.
     */
    private DomainType   domainUpperBound;
    
    /**
     * The lower bound on the domain (horizontal) axis to display.
     */
    private DomainType   domainLowerBound;
    
    /**
     * The upper bound on the range (vertical) axis to display.
     */
    private RangeType    rangeUpperBound;
    
    /**
     * The lower bound on the range (vertical) axis to display.
     */
    private RangeType    rangeLowerBound;
    
    /**
     * The maximum value among the domain values of the data points. Will be
     * {@code null} if there are no data points.
     */
    private DomainType   domainMaxValue;
    
    /**
     * The minimum value among the domain values of the data points. Will be
     * {@code null} if there are no data points.
     */
    private DomainType   domainMinValue;
    
    /**
     * The maximum value among the range values of the data points. Will be
     * {@code null} if there are no data points.
     */
    private RangeType    rangeMaxValue;
    
    /**
     * The minimum value among the range values of the data points. Will be
     * {@code null} if there are no data points.
     */
    private RangeType    rangeMinValue;
    
    
    /**
     * Default constructor. Initializes variables to default values.
     */
    public Graph() {
        super();
        
        listeners = new ArrayList<>();
        dataDirty = false;
        dataPoints = new ArrayList<>();
        domainUpperBound = null;
        domainLowerBound = null;
        rangeUpperBound = null;
        rangeLowerBound = null;
        domainMaxValue = null;
        domainMinValue = null;
        rangeMaxValue = null;
        rangeMinValue = null;
    }
    
    /**
     * Adds a compatible {@link DataPoint} object to the graph.
     * 
     * @param point The data point to add to the graph.
     */
    public void addDataPoint(DataPoint<DomainType, RangeType> point) {
        if (point == null) {
            return;
        }
        
        addDataPoints(Collections.singleton(point));
    }
    
    /**
     * Adds a {@link Collection} of {@link DataPoint} objects to the graph.
     * 
     * @param points The data points to add to the graph.
     */
    public void addDataPoints(Collection<? extends DataPoint<DomainType, RangeType>> points) {
        if (points == null || points.isEmpty()) {
            return;
        }
        dataPoints.addAll(points);
        
        for (DataPoint<DomainType, RangeType> point : points) {
            if (domainMinValue == null || domainMinValue.compareTo(point.getDomainPosition()) > 0) {
                domainMinValue = point.getDomainPosition();
            }
            if (domainMaxValue == null || domainMaxValue.compareTo(point.getDomainPosition()) < 0) {
                domainMaxValue = point.getDomainPosition();
            }
            if (rangeMinValue == null || rangeMinValue.compareTo(point.getRangeValue()) > 0) {
                rangeMinValue = point.getRangeValue();
            }
            if (rangeMaxValue == null || rangeMaxValue.compareTo(point.getRangeValue()) < 0) {
                rangeMaxValue = point.getRangeValue();
            }
        }
        
        dataDirty = true;
    }
    
    /**
     * Gets a complete list of all {@link DataPoint} objects displayed in this
     * graph.
     * 
     * @return An {@link ArrayList} of data points contained in this graph.
     */
    public List<DataPoint<DomainType, RangeType>> getDataPoints() {
        return new ArrayList<>(dataPoints);
    }
    
    /**
     * Gets the currently set domain upper bound value, or {@code null} if none
     * is set.
     * 
     * @return The current domain upper bound or {@code null} if none is set.
     */
    public DomainType getDomainUpperBound() {
        return (domainUpperBound == null ? domainMaxValue : domainUpperBound);
    }
    
    /**
     * Gets the currently set domain lower bound value, or {@code null} if none
     * is set.
     * 
     * @return The current domain lower bound or {@code null} if none is set.
     */
    public DomainType getDomainLowerBound() {
        return (domainLowerBound == null ? domainMinValue : domainLowerBound);
    }
    
    /**
     * Gets the currently set range upper bound value, or {@code null} if none
     * is set.
     * 
     * @return The current range upper bound or {@code null} if none is set.
     */
    public RangeType getRangeUpperBound() {
        return (rangeUpperBound == null ? rangeMaxValue : rangeUpperBound);
    }
    
    /**
     * Gets the currently set range lower bound value, or {@code null} if none
     * is set.
     * 
     * @return The current range lower bound or {@code null} if none is set.
     */
    public RangeType getRangeLowerBound() {
        return (rangeLowerBound == null ? rangeMinValue : rangeLowerBound);
    }
    
    /**
     * Sets the domain upper bound value. Can be used to unset the bound if
     * {@code null} is supplied.
     * 
     * @param upperBound The new value of the upper bound. Can be set to null.
     */
    public void setDomainUpperBound(DomainType upperBound) {
        domainUpperBound = upperBound;
        dataDirty = true;
    }
    
    /**
     * Sets the domain lower bound value. Can be used to unset the bound if
     * {@code null} is supplied.
     * 
     * @param lowerBound The new value of the lower bound. Can be set to null.
     */
    public void setDomainLowerBound(DomainType lowerBound) {
        domainLowerBound = lowerBound;
        dataDirty = true;
    }
    
    /**
     * Sets the range lower bound value. Can be used to unset the bound if
     * {@code null} is supplied.
     * 
     * @param upperBound The new value of the lower bound. Can be set to null.
     */
    public void setRangeUpperBound(RangeType upperBound) {
        rangeUpperBound = upperBound;
        dataDirty = true;
    }
    
    /**
     * Sets the range lower bound value. Can be used to unset the bound if
     * {@code null} is supplied.
     * 
     * @param lowerBound The new value of the lower bound. Can be set to null.
     */
    public void setRangeLowerBound(RangeType lowerBound) {
        rangeLowerBound = lowerBound;
        dataDirty = true;
    }
    
    /**
     * Sorts all data points by their domain values in ascending order.
     */
    public void sortDataPoints() {
        
        // Short-circuit if graph contains no data points
        if (dataPoints.isEmpty()) {
            return;
        }
        
        // Sort data points
        Collections.sort(dataPoints);
        dataDirty = true;
    }
    
    public void forceRedraw() {
        redraw();
    }

    /**
     * Redraws the graph, forcing a recalculation of drawing data. Should be
     * invoked manually after manipulating the graph contents and any graph
     * parameters.
     */
    public void redraw() {
        notifyListeners();
    };
    
    
    /**
     * Registers a listener to the Graph.
     * 
     * @param listener The listener to register.
     */
    public void addGraphListener(GraphListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    /**
     * Deregisters a listener from the Graph.
     * @param listener 
     */
    public void removeGraphListener(GraphListener listener) {
        if (listener != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }
    
    /**
     * Calls the event callback for all registered listeners.
     */
    private void notifyListeners() {
        for (GraphListener listener : listeners) {
            listener.onGraphChanged(this);
        }
        dataDirty = false;
    }
    
    
    /**
     * Listener interface for handling graph update events.
     */
    public interface GraphListener {
        
        /**
         * Main callback function; triggered when the bounds or data of this
         * graph is changed.
         * 
         * @param graph The {@link Graph} object that has been modified,
         * allowing this listener to be used with multiple {@link Graph}s.
         */
        public void onGraphChanged(Graph graph);
    }
}
