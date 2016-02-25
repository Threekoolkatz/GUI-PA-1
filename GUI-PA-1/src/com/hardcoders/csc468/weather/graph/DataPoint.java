package com.hardcoders.csc468.weather.graph;

/**
 * A data point to be represented on a graph.
 * 
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 * 
 * @param <DomainType> The data type that indicates where this data point is
 * located along the domain (horizontal) axis of a graph.
 * @param <RangeType> The data type that indicates where this data point is
 * located along the range (vertical) axis of a graph.
 */
public interface DataPoint<DomainType, RangeType> extends Comparable {
    
    /**
     * Gets the position of this data point along the domain axis.
     * 
     * @return The position of this data point along the domain axis.
     */
    DomainType getDomainPosition();
    
    /**
     * Gets the value of this data point along the range axis. This value may be
     * {@code null}.
     * 
     * @return The value of this data point along the range axis. May be
     * {@code null}.
     */
    RangeType getRangeValue();
    
    /**
     * Gets the percentage value of where this data point is located
     * on a graph relative to the provided lower bound.
     * 
     * @param lowerBound The lower bound to scale to.
     * @param upperBound The upper bound to scale to.
     * 
     * @return Percentage value representing how far along an axis between
     * the given lowerBound and upperBound this object should be placed. A
     * negative value means that the data point is located below the lower
     * bound, whereas a value greater than 1.0 means that the data point is
     * located above the upper bound.
     */
    Double getDomainPercentage(DomainType lowerBound, DomainType upperBound);
    
    /**
     * Gets the percentage value of where this data point is located
     * on a graph relative to the provided lower bound.
     * 
     * @param lowerBound The lower bound to compare to.
     * @param upperBound The upper bound to compare to.
     * 
     * @return Percentage value representing how far along an axis between the
     * given lowerBound and upperBound this object should be placed. A negative
     * value means that the data point is located below the lower bound, whereas
     * a value greater than 1.0 means that the data point is located above the
     * upper bound.
     */
    Double getRangePercentage(RangeType lowerBound, RangeType upperBound);
}
