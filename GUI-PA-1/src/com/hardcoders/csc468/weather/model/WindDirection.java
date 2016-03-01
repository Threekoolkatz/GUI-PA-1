package com.hardcoders.csc468.weather.model;

/**
 * Enumeration representing the 8 cardinal directions and the midway
 * directions between each.
 * 
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 */
public enum WindDirection {
    EAST("East"),
    EAST_NORTH_EAST("East Northeast"),
    NORTH_EAST_EAST("Northeast East"),
    NORTH_EAST("Northeast"),
    NORTH_NORTH_EAST("North Northeast"),
    NORTH("North"),
    NORTH_NORTH_WEST("North Northwest"),
    NORTH_WEST("Northwest"),
    NORTH_WEST_WEST("Northwest West"),
    WEST_NORTH_WEST("West Northwest"),
    WEST("West"),
    WEST_SOUTH_WEST("West Southwest"),
    SOUTH_WEST_WEST("Southwest West"),
    SOUTH_WEST("Southwest"),
    SOUTH_SOUTH_WEST("South Southwest"),
    SOUTH("South"),
    SOUTH_SOUTH_EAST("South Southeast"),
    SOUTH_EAST("Southeast"),
    SOUTH_EAST_EAST("Southeast East"),
    EAST_SOUTH_EAST("East Southeast");
    
    //value to hold the string representation of each enum
    private String stringDirection;
    
    /**
     * Converts the string representation into the enumerated type
     * 
     * @param direction the passed in string
     * @return Enumerated version of the direction
     */
    public static WindDirection fromString(String direction)
    {
        if( direction == null ){
            return null;
        }
        switch (direction) {
            case "E":   return EAST;
            case "ENE": return EAST_NORTH_EAST;
            case "NEE": return NORTH_EAST_EAST;
            case "NE":  return NORTH_EAST;
            case "NNE": return NORTH_NORTH_EAST;
            case "N":   return NORTH;
            case "NNW": return NORTH_NORTH_EAST;
            case "NW":  return NORTH_WEST;
            case "NWW": return NORTH_WEST_WEST;
            case "WNW": return WEST_NORTH_WEST;
            case "W":   return WEST;
            case "WSW": return WEST_SOUTH_WEST;
            case "SWW": return SOUTH_WEST_WEST;
            case "SW":  return SOUTH_WEST;
            case "SSW": return SOUTH_SOUTH_WEST;
            case "S":   return SOUTH;
            case "SSE": return SOUTH_SOUTH_EAST;
            case "SE":  return SOUTH_EAST;
            case "SEE": return SOUTH_EAST_EAST;
            case "ESE": return EAST_SOUTH_EAST;
            default: {System.out.println("Invalid Wind Direction " + direction);
                      return null; }
            }
    }
    
    /**
     * Constructor that gives each enumerated value a string 
     * representation
     * 
     * @param stringDirection - string representation of enum value
     */
    WindDirection(String stringDirection){
        this.stringDirection = stringDirection;
    }
    
    /**
     * returns the string representation of the enumerated value
     * 
     * @return stringDirection - stored string representation
     */
    public String getValue() {
        return stringDirection;
    }
    
    @Override
    public String toString(){
        return this.getValue();
    }
}
