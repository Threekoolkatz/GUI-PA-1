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
                || rangeUpperBound == null) {
            return;
        }
        
        double domainWidth = domainUpperBound - domainLowerBound;
        double rangeHeight = rangeUpperBound - rangeLowerBound;
        
        // Adjust the bounds
        setDomainLowerBound(domainLowerBound + domainWidth * pDomain);
        setDomainUpperBound(domainUpperBound + domainWidth * pDomain);
        setRangeLowerBound(rangeLowerBound + rangeHeight * pRange);
        setRangeUpperBound(rangeUpperBound + rangeHeight * pRange);
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
        
        setDomainLowerBound(domainVal - domainWidth * scale * pDomain);
        setDomainUpperBound(domainVal + domainWidth * scale * (1.0 - pDomain));
        setRangeLowerBound(rangeVal - rangeHeight * scale * pRange);
        setRangeUpperBound(rangeVal + rangeHeight * scale * (1.0 - pRange));
    }
    
}
