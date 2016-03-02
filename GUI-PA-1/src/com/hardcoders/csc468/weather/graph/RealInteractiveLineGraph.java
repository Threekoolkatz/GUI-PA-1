package com.hardcoders.csc468.weather.graph;

/**
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 */
public class RealInteractiveLineGraph extends InteractiveLineGraph<Double, Double> {

    public RealInteractiveLineGraph() {
        super();
    }
    
    @Override
    public void translateBounds(double pDomain, double pRange) {
        Double domainLowerBound = getDomainLowerBound();
        Double domainUpperBound = getDomainUpperBound();
        Double rangeLowerBound = getRangeLowerBound();
        Double rangeUpperBound = getRangeUpperBound();
        
        // Make sure all parameters are not null
        if (domainLowerBound == null
                || domainUpperBound == null
                || rangeLowerBound == null
                || rangeUpperBound == null
                || getDomainMinValue() == null
                || getDomainMaxValue() == null
                || getRangeMinValue() == null
                || getRangeMaxValue() == null) {
            return;
        }
        
        double domainWidth = domainUpperBound - domainLowerBound;
        double rangeHeight = rangeUpperBound - rangeLowerBound;
        
        double domainOffset = domainWidth * pDomain;
        domainOffset = Math.max(getDomainMinValue() - domainUpperBound, domainOffset);
        domainOffset = Math.min(getDomainMaxValue() - domainLowerBound, domainOffset);
        
        double rangeOffset  = rangeHeight * pRange;
        rangeOffset = Math.max(getRangeMinValue() - rangeUpperBound, rangeOffset);
        rangeOffset = Math.min(getRangeMaxValue() - rangeLowerBound, rangeOffset);
        
        // Adjust the bounds
        setDomainLowerBound(domainLowerBound + domainOffset);
        setDomainUpperBound(domainUpperBound + domainOffset);
        setRangeLowerBound(rangeLowerBound + rangeOffset);
        setRangeUpperBound(rangeUpperBound + rangeOffset);
    }

    @Override
    public void scaleBounds(double scale, double pDomain, double pRange) {
        Double domainLowerBound = getDomainLowerBound();
        Double domainUpperBound = getDomainUpperBound();
        Double rangeLowerBound = getRangeLowerBound();
        Double rangeUpperBound = getRangeUpperBound();
        
        // Make sure all parameters are not null
        if (domainLowerBound == null
                || domainUpperBound == null
                || rangeLowerBound == null
                || rangeUpperBound == null) {
            return;
        }
        
        double domainWidth = domainUpperBound - domainLowerBound;
        double rangeHeight = rangeUpperBound - rangeLowerBound;
        double domainVal = domainLowerBound + domainWidth * pDomain;
        double rangeVal  = rangeLowerBound + rangeHeight * pRange;
        
        if (domainWidth == 0 || rangeHeight == 0) return;
        
        scale = Math.min(1000.0 * 60 * 60 * 24 * 365 * 100 / domainWidth, scale);
        scale = Math.max(1000.0 / domainWidth, scale);
        
        setDomainLowerBound(domainVal - domainWidth * scale * pDomain);
        setDomainUpperBound(domainVal + domainWidth * scale * (1.0 - pDomain));
        setRangeLowerBound(rangeVal - rangeHeight * scale * pRange);
        setRangeUpperBound(rangeVal + rangeHeight * scale * (1.0 - pRange));
    }
    
}
