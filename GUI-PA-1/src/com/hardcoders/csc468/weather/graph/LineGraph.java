package com.hardcoders.csc468.weather.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author 1989750
 */
public class LineGraph<DomainType, RangeType> extends Graph<DomainType, RangeType> implements ComponentListener {
    private List<Double> domainScales;
    private List<Double> rangeScales;
    private int          domainScalesLowerBound;
    private int          domainScalesUpperBound;
    private int          drawPoints[][];
    
    private boolean      dataPointsDirty;
    private boolean      scalesDirty;
    private boolean      drawPointsDirty;
    
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
        setRangeLowerBound(lowerBound);
        scalesDirty = true;
    }
    
    public void sortDataPoints() {
        if (!dataPointsDirty) {
            return;
        }
        
        Collections.sort(getDataPoints());
        dataPointsDirty = false;
        scalesDirty = true;
    }
    
    public void calculateDrawScales() {
        
        // Make sure data points are sorted along the domain axis
        sortDataPoints();
        
        // Cancel if scaling values are up-to-date
        if (scalesDirty) {
            return;
        }
        
        // Clear current data
        domainScales.clear();
        rangeScales.clear();
        domainScalesLowerBound = 0;
        domainScalesUpperBound = 0;
        
        List<DataPoint<DomainType, RangeType>> dataPoints = getDataPoints();
        
        for (DataPoint<DomainType, RangeType> dataPoint : dataPoints) {
            
            // Calculate point's position on the graph relative to the domain and range settings
            double domainScale = dataPoint.getDomainPercentage(getDomainLowerBound(), getDomainUpperBound());
            double rangeScale = dataPoint.getDomainPercentage(getDomainLowerBound(), getDomainUpperBound());
            
            // Store the result
            domainScales.add(domainScale);
            rangeScales.add(rangeScale);
            
            // Update bounds if necessary (useful for implicit trimming)
            if (domainScale < 0) domainScalesLowerBound = domainScales.size();
            if (domainScale > 1) domainScalesUpperBound = domainScales.size();
        }
        
        if (domainScalesUpperBound < domainScalesLowerBound) domainScalesUpperBound = domainScalesLowerBound;
        
        // Update dirty flags
        scalesDirty = false;
        drawPointsDirty = true;
    }
    
    public void calculateDrawPoints() {
        
        // Ensure drawing scales have been calculated with current data set
        calculateDrawScales();
        
        // Cancel if drawing points are up-to-date
        if (!drawPointsDirty) {
            return;
        }
        
        // Initialize constants
        final int width = getWidth();
        final int height = getHeight();
        final int numPoints = domainScalesUpperBound - domainScalesLowerBound + 1;
        
        // Allocate arrays
        for (int i = 0; i < 2; i++) {
            drawPoints[i] = new int[numPoints];
        }
        
        // Calculate pixel locations of drawing points
        for (int i = 0; i < numPoints; i++) {
            drawPoints[0][i] = (int) (domainScales.get(i + domainScalesLowerBound) * width);
            drawPoints[1][i] = (int) (rangeScales.get(i + domainScalesLowerBound) * height);
        }
        
        // Reset dirty flag
        drawPointsDirty = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Calculate drawing points if necessary; might be better to do elsewhere
        calculateDrawPoints();
        
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
