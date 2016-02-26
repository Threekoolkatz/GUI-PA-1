package com.hardcoders.csc468.weather.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Line graph class that extends the {@link Graph} class. Displays registered
 * data points in a line graph. Performs internal optimizations for improving
 * drawing speed.
 * 
 * @param <DomainType> The data type to use for the domain (horizontal) axis.
 * @param <RangeType> The data type to use for the range (vertical) axis.
 * 
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 */
public class LineGraph<DomainType extends Comparable, RangeType extends Comparable> extends Graph<DomainType, RangeType> implements ComponentListener {
    
    /**
     * Cached data point domain scale values. Values are scaled relative to
     * width of component, allowing for fast resizing.
     */
    private final List<Double> domainScales;
    
    /**
     * Cached data point range scales. Values are scaled relative to height of
     * component, allowing for fast resizing.
     */
    private final List<Double> rangeScales;
    
    /**
     * The index of the data point with the lowest domain value to draw.
     */
    private int                domainScalesLowerBound;
    
    /**
     * The index of the data point with the highest domain value to draw.
     */
    private int                domainScalesUpperBound;
    
    /**
     * 2 dimensional array of x and y coordinates to use for drawing the graph.
     */
    private final int          drawPoints[][];
    
    /**
     * Flag indicating that data points are no longer valid and must be
     * resorted and calculated before rendering.
     */
    private boolean            dataPointsDirty;
    
    /**
     * Flag indicating that the cached scaling values for the data points are no
     * longer valid and need to be recalculated before rendering.
     */
    private boolean            scalesDirty;
    
    /**
     * Flag indicating that the cached data point coordinates are no longer
     * valid and must be recalculated before rendering.
     */
    private boolean            drawPointsDirty;

    
    /**
     * Default constructor. Initializes parameters to their default values.
     */
    public LineGraph() {
        super();
        
        domainScales = new ArrayList<>();
        rangeScales = new ArrayList<>();
        
        domainScalesLowerBound = 0;
        domainScalesUpperBound = 0;
        
        drawPoints = new int[2][];
        drawPoints[0] = new int[0];
        drawPoints[1] = new int[0];
        
        dataPointsDirty = false;
        scalesDirty = false;
        drawPointsDirty = false;
        
        // Handle own component events
        this.addComponentListener(this);
    }
    
    @Override
    public void addDataPoint(DataPoint<DomainType, RangeType> point) {
        if (point == null) {
            return;
        }
        super.addDataPoint(point);
        dataPointsDirty = true;
    }
    
    @Override
    public void addDataPoints(Collection<? extends DataPoint<DomainType, RangeType>> points) {
        if (points == null || points.isEmpty()) {
            return;
        }
        super.addDataPoints(points);
        dataPointsDirty = true;
    }
    
    @Override
    public void setDomainUpperBound(DomainType upperBound) {
        super.setDomainUpperBound(upperBound);
        scalesDirty = true;
    }
    
    @Override
    public void setDomainLowerBound(DomainType lowerBound) {
        super.setDomainLowerBound(lowerBound);
        scalesDirty = true;
    }
    
    @Override
    public void setRangeUpperBound(RangeType upperBound) {
        super.setRangeUpperBound(upperBound);
        scalesDirty = true;
    }
    
    @Override
    public void setRangeLowerBound(RangeType lowerBound) {
        super.setRangeLowerBound(lowerBound);
        scalesDirty = true;
    }
    
    
    /**
     * Sorts all data points by their domain values in ascending order, but only
     * if the {@link #dataPointsDirty} flag is set.
     */
    @Override
    public void sortDataPoints() {
        
        // Only sort if flag is set
        if (!dataPointsDirty) {
            return;
        }
        
        // Make call to super class
        super.sortDataPoints();
        
        // Update flags
        dataPointsDirty = false;
        scalesDirty = true;
    }

    @Override
    public void forceRedraw() {
        dataPointsDirty = true;
        super.forceRedraw();
    }
    
    @Override
    public void redraw() {
        calculateDrawPoints();
        super.redraw();
    }
        
    /**
     * Recalculates and caches data point drawing scales (relative to dimensions
     * of the this graph), but only if the {@link #scalesDirty} flag is set.
     */
    private void calculateDrawScales() {
        
        // Make sure data points are sorted along the domain axis
        sortDataPoints();
        
        // Cancel if scaling values are up-to-date
        if (!scalesDirty) {
            return;
        }
        
        // Clear current data
        domainScales.clear();
        rangeScales.clear();
        domainScalesLowerBound = 0;
        domainScalesUpperBound = 0;
        
        List<DataPoint<DomainType, RangeType>> dataPoints = getDataPoints();
        
        // Short-circuit if graph contains no data points
        if (dataPoints.isEmpty()) {
            return;
        }
        
        // Find domain upper bound using binary search
        int domainTop = dataPoints.size() - 1;
        int domainBottom = 0;
        int domainMid;
        do {
            domainMid = (domainTop + domainBottom) / 2;
            double domainScale = dataPoints.get(domainMid).getDomainPercentage(getDomainLowerBound(), getDomainUpperBound());
            
            if (domainScale == 1.0) {
                
                // Found what we were looking for
                break;
            } else if (domainScale > 1.0) {
                
                // Search lower half
                domainTop = domainMid - 1;
            } else if (domainScale < 1.0) {
                
                // Search upper half
                domainBottom = domainMid + 1;
            }
        } while (domainTop >= domainBottom);
        domainScalesUpperBound = domainMid + (domainMid < dataPoints.size() - 1 ? 1 : 0) + 1;
        
        // Find domain lower bound using binary search
        domainTop = domainMid;
        domainBottom = 0;
        do {
            domainMid = (domainTop + domainBottom) / 2;
            double domainScale = dataPoints.get(domainMid).getDomainPercentage(getDomainLowerBound(), getDomainUpperBound());
            
            if (domainScale == 0.0) {
                
                // Found what we're looking for
                break;
            } else if (domainScale > 0.0) {
                
                // Search lower half
                domainTop = domainMid - 1;
            } else if (domainScale < 0.0) {
                
                // Search upper half
                domainBottom = domainMid + 1;
            }
        } while (domainTop >= domainBottom);
        domainScalesLowerBound = domainMid - (domainMid > 0 ? 1 : 0);
        
        // Make sure bounds are in appropriate order
        if (domainScalesUpperBound < domainScalesLowerBound) domainScalesUpperBound = domainScalesLowerBound;
        
        for (DataPoint<DomainType, RangeType> dataPoint : dataPoints.subList(domainScalesLowerBound, domainScalesUpperBound)) {
            
            // Calculate point's position on the graph relative to the domain and range settings
            double domainScale = dataPoint.getDomainPercentage(getDomainLowerBound(), getDomainUpperBound());
            double rangeScale = dataPoint.getRangePercentage(getRangeLowerBound(), getRangeUpperBound());
            
            // Store the result
            domainScales.add(domainScale);
            rangeScales.add(rangeScale);
        }
        
        // Update dirty flags
        scalesDirty = false;
        drawPointsDirty = true;
    }
    
    /**
     * Recalculates and caches drawing coordinates for data points in the graph,
     * but only if the {@link #drawPointsDirty} flag is set.
     */
    private void calculateDrawPoints() {
        
        // Ensure drawing scales have been calculated with current data set
        calculateDrawScales();
        
        // Cancel if drawing points are up-to-date
        if (!drawPointsDirty) {
            return;
        }
        
        // Shortc-circuit if no data points exist
        if (domainScales.isEmpty() || rangeScales.isEmpty()) {
            return;
        }
        
        // Initialize constants
        final int width = getWidth();
        final int height = getHeight();
        final int numPoints = domainScalesUpperBound - domainScalesLowerBound;
        
        // Allocate arrays
        for (int i = 0; i < 2; i++) {
            drawPoints[i] = new int[numPoints];
        }
        
        // Calculate pixel locations of drawing points
        for (int i = 0; i < numPoints; i++) {
            drawPoints[0][i] = (int) (domainScales.get(i) * width);
            drawPoints[1][i] = (int) (rangeScales.get(i) * height);
        }
        
        // Reset dirty flag
        drawPointsDirty = false;
    }

    /**
     * Draws a line graph in addition to performing the default paint behaviors
     * of a {@link JPanel}.
     * 
     * @param g The graphics context to use when drawing.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        final int numPoints = drawPoints[0].length;
        
        // Set the color
        g.setColor(Color.RED);
        
        // Draw polyline
        if (numPoints > 1) {
            g.drawPolyline(drawPoints[0], drawPoints[1], numPoints);
        }
        
        // Draw circls at x/y array positions
        for (int i = 0; i < numPoints; i++) {
            g.fillOval(drawPoints[0][i] - 2, drawPoints[1][i] - 2, 4, 4);
        }
    }

    
    @Override
    public void componentResized(ComponentEvent e) {
        drawPointsDirty = true;
        redraw();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        // Nothing else to do
    }

    @Override
    public void componentShown(ComponentEvent e) {
        // Nothing else to do
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        // Nothing else to do
    }
}
