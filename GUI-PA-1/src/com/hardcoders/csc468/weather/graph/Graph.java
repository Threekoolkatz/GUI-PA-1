package com.hardcoders.csc468.weather.graph;

import java.util.ArrayList;
import java.util.Collection;
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
public abstract class Graph<DomainType, RangeType> extends JPanel {
    private List<DataPoint<DomainType, RangeType>> dataPoints;
    private DomainType   domainUpperBound;
    private DomainType   domainLowerBound;
    private RangeType    rangeUpperBound;
    private RangeType    rangeLowerBound;
    
    public Graph() {
        super();
        
        dataPoints = new ArrayList<>();
        domainUpperBound = null;
        domainLowerBound = null;
        rangeUpperBound = null;
        rangeLowerBound = null;
    }
    
    public void addDataPoint(DataPoint<DomainType, RangeType> point) {
        if (point == null) {
            return;
        }
        dataPoints.add(point);
    }
    
    public void addDataPoints(Collection<? extends DataPoint<DomainType, RangeType>> points) {
        if (points == null || points.isEmpty()) {
            return;
        }
        dataPoints.addAll(points);
    }
    
    public List<DataPoint<DomainType, RangeType>> getDataPoints() {
        return new ArrayList<>(dataPoints);
    }
    
    public DomainType getDomainUpperBound() {
        return domainUpperBound;
    }
    
    public DomainType getDomainLowerBound() {
        return domainLowerBound;
    }
    
    public RangeType getRangeUpperBound() {
        return rangeUpperBound;
    }
    
    public RangeType getRangeLowerBound() {
        return rangeLowerBound;
    }
    
    public void setDomainUpperBound(DomainType upperBound) {
        domainUpperBound = upperBound;
    }
    
    public void setDomainLowerBound(DomainType lowerBound) {
        domainLowerBound = lowerBound;
    }
    
    public void setRangeUpperBound(RangeType upperBound) {
        rangeUpperBound = upperBound;
    }
    
    public void setRangeLowerBound(RangeType lowerBound) {
        rangeLowerBound = lowerBound;
    }
}
