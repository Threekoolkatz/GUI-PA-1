package com.hardcoders.csc468.weather;

import com.hardcoders.csc468.weather.graph.InteractiveLineGraph;
import java.util.Date;

/**
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 */
public class WeatherInteractiveLineGraph extends InteractiveLineGraph<Date, Double> {

    public WeatherInteractiveLineGraph() {
        super();
    }
    
    @Override
    public void translateBounds(double pDomain, double pRange) {
        Date domainLowerBound = getDomainLowerBound();
        Date domainUpperBound = getDomainUpperBound();
        Double rangeLowerBound = getRangeLowerBound();
        Double rangeUpperBound = getRangeUpperBound();
        
        // Make sure all parameters are not null
        if (domainLowerBound == null
                || domainUpperBound == null
                || rangeLowerBound == null
                || rangeUpperBound == null) {
            return;
        }
        
        double domainWidth = (double) domainUpperBound.getTime() - (double) domainLowerBound.getTime();
        double rangeHeight = rangeUpperBound - rangeLowerBound;
        
        // Adjust the bounds
        setDomainLowerBound(new Date((long) (domainLowerBound.getTime() + domainWidth * pDomain)));
        setDomainUpperBound(new Date((long) (domainUpperBound.getTime() + domainWidth * pDomain)));
        setRangeLowerBound(rangeLowerBound + rangeHeight * pRange);
        setRangeUpperBound(rangeUpperBound + rangeHeight * pRange);
    }

    @Override
    public void scaleBounds(double scale, double pDomain, double pRange) {
        Date domainLowerBound = getDomainLowerBound();
        Date domainUpperBound = getDomainUpperBound();
        Double rangeLowerBound = getRangeLowerBound();
        Double rangeUpperBound = getRangeUpperBound();
        
        // Make sure all parameters are not null
        if (domainLowerBound == null
                || domainUpperBound == null
                || rangeLowerBound == null
                || rangeUpperBound == null) {
            return;
        }
        
        double domainWidth = (double) domainUpperBound.getTime() - (double) domainLowerBound.getTime();
        double rangeHeight = rangeUpperBound - rangeLowerBound;
        double domainVal = (double) domainLowerBound.getTime() + domainWidth * pDomain;
        double rangeVal  = rangeLowerBound + rangeHeight * pRange;
        
        setDomainLowerBound(new Date((long) (domainVal - domainWidth * scale * pDomain)));
        setDomainUpperBound(new Date((long) (domainVal + domainWidth * scale * (1.0 - pDomain))));
        setRangeLowerBound(rangeVal - rangeHeight * scale * pRange);
        setRangeUpperBound(rangeVal + rangeHeight * scale * (1.0 - pRange));
    }
    
}
