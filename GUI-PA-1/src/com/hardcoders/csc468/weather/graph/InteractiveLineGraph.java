package com.hardcoders.csc468.weather.graph;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 * 
 * @param <DomainType> 
 * @param <RangeType>
 */
public abstract class InteractiveLineGraph<DomainType extends Comparable, RangeType extends Comparable> extends LineGraph<DomainType, RangeType> implements MouseListener, MouseMotionListener, MouseWheelListener {
    
    /**
     * The last known point that the mouse clicked on the component.
     */
    private Point              clickPoint;
    
    private DomainType         clickDomainLowerBound;
    private DomainType         clickDomainUpperBound;
    private RangeType          clickRangeLowerBound;
    private RangeType          clickRangeUpperBound;
    
    
    /**
     * Default constructor. Initializes properties to {@code null}.
     */
    public InteractiveLineGraph() {
        super();
        
        clickPoint = null;
        clickDomainLowerBound = null;
        clickDomainUpperBound = null;
        clickRangeLowerBound = null;
        clickRangeUpperBound = null;
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }
    
    
    /**
     * Translates the domain and range bounds by a certain percentage (relative
     * to the current size of the domain and range window (not the x and y pixel
     * height and width, but the data bounds themselves)). Must be overridden by
     * a subclass, since this requires knowledge of DomainType and RangeType.
     * 
     * @param pDomain The percent relative to the width of the domain window
     * to translate the graph along the domain. Negative shifts "left", positive
     * shifts "right".
     * @param pRange the percent relative to the height of the range window to
     * translate the graph along the range. Negative shifts "down", positive
     * shifts "up".
     */
    public abstract void translateBounds(double pDomain, double pRange);
    
    /**
     * Scales the bounds either in or out, allowing for zooming that is focused
     * on a point.
     * 
     * @param scale The percent scale value to adjust the bounds. Only positive
     * values are valid. Values less than 1.0 will zoom in, values greater than
     * 1.0 will zoom out.
     * @param pDomain The domain percent value to focus on when zooming.
     * @param pRange The range percent value to focus on when zooming.
     */
    public abstract void scaleBounds(double scale, double pDomain, double pRange);
    
    private void saveDisplayState(MouseEvent e) {
        clickPoint = e.getPoint();
        clickDomainLowerBound = getDomainLowerBound();
        clickDomainUpperBound = getDomainUpperBound();
        clickRangeLowerBound = getRangeLowerBound();
        clickRangeUpperBound = getRangeUpperBound();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // Nothing else to do
    }

    @Override
    public void mousePressed(MouseEvent e) {
        saveDisplayState(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Nothing else to do
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Nothing else to do
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Nothing else to do
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
        // Convenience variables
        Point point = e.getPoint();
        
        // Ensure clickPoint has been set
        if (clickPoint == null
                || clickDomainLowerBound == null
                || clickDomainUpperBound == null
                || clickRangeLowerBound == null
                || clickRangeUpperBound == null) {
            
            saveDisplayState(e);
            return;
        }
        
        // Calculate delta x and delta y
        int dx = (int) point.getX() - (int) clickPoint.getX();
        int dy = (int) point.getY() - (int) clickPoint.getY();
        
        double px = (double) dx / (double) getWidth();
        double py = (double) dy / (double) getHeight();
        
        // Reset bounds
        setDomainLowerBound(clickDomainLowerBound);
        setDomainUpperBound(clickDomainUpperBound);
        setRangeLowerBound(clickRangeLowerBound);
        setRangeUpperBound(clickRangeUpperBound);
        
        // Pass off implementation to subclass
        translateBounds(-px, py);
        
        redraw();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Nothing else to do
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        
        double scale = Math.pow(1.15, e.getPreciseWheelRotation());
        
        saveDisplayState(e);
        
        int dx = (int) clickPoint.getX();
        int dy = (int) clickPoint.getY();
        
        double px = (double) dx / (double) getWidth();
        double py = 1.0 - ((double) dy / (double) getHeight());
        
        scaleBounds(scale, px, py);
        
        saveDisplayState(e);
        
        redraw();
        repaint();
    }
}
