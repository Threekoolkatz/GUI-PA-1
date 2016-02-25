package com.hardcoders.csc468.weather.model;

/**
 * Enumeration representing the 8 cardinal directions and the midway
 * directions between each.
 * 
 * @author Daniel Andrus <daniel.andrus@mines.sdsmt.edu>
 */
public enum WindDirection {
    EAST,
    NORTH_EAST_EAST,
    NORTH_EAST,
    NORTH_NORTH_EAST,
    NORTH,
    NORTH_NORTH_WEST,
    NORTH_WEST,
    NORTH_WEST_WEST,
    WEST,
    SOUTH_WEST_WEST,
    SOUTH_WEST,
    SOUTH_SOUTH_WEST,
    SOUTH,
    SOUTH_SOUTH_EAST,
    SOUTH_EAST,
    SOUTH_EAST_EAST;
    
    public static WindDirection fromString(String direction)
    {
        if( direction == null ){
            return null;
        }
        switch (direction) {
            case "E":   return EAST;
            case "NEE": return NORTH_EAST_EAST;
            case "NE":  return NORTH_EAST;
            case "NNE": return NORTH_NORTH_EAST;
            case "N":   return NORTH;
            case "NNW": return NORTH_NORTH_EAST;
            case "NW":  return NORTH_WEST;
            case "NWW": return NORTH_WEST_WEST;
            case "W":   return WEST;
            case "SWW": return SOUTH_WEST_WEST;
            case "SW":  return SOUTH_WEST;
            case "SSW": return SOUTH_SOUTH_WEST;
            case "S":   return SOUTH;
            case "SSE": return SOUTH_SOUTH_EAST;
            case "SE":  return SOUTH_EAST;
            case "SEE": return SOUTH_EAST_EAST;
            default: {System.out.println("Invalide Month");
                      return null; }
            }
    }
}
