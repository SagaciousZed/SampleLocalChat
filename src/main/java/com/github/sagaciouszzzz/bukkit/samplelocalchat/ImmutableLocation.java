package com.github.sagaciouszzzz.bukkit.samplelocalchat;

import org.bukkit.Location;

/**
 * This Location has only final values for world and x,y,z
 * and can only compute distance squared
 * 
 * @author edmond
 *
 */
public class ImmutableLocation {
    
    /**
     * The world this is in.
     */
    final String worldName;
    
    /**
     * X, Y, Z coordinates.
     */
    final double x;
    final double y;
    final double z;
    
    /**
     * Constructor
     * @param location the location to snapshot
     */
    public ImmutableLocation(Location location) {
        worldName = location.getWorld().getName();
        
        x = location.getX();
        y = location.getY();
        z = location.getZ();
    }
    
    /**
     * computes distance squared. If two ImmutableLocations are in
     * different world returns the maximum possible distance.
     * 
     * @return distance squared.
     */
    public double distanceSquared(ImmutableLocation o) {
       return  worldName.equals(o.worldName) ? 
               (x - o.x) * (x - o.x)
               + (y - o.y) * (y - o.y)
               + (z - o.z) * (z - o.z)
               : Double.MAX_VALUE;
    }

}
