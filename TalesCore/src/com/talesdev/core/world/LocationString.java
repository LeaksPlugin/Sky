package com.talesdev.core.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Location String
 *
 * @author MoKunz
 */
public class LocationString {
    private Location location;

    public LocationString(Location location) {
        setLocation(location);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Get LocationString object from encoded format
     * x,y,z,world
     *
     * @param locationString
     * @return
     */
    public static LocationString fromString(String locationString) {
        if (locationString == null) {
            return new LocationString(new Location(Bukkit.getServer().getWorlds().get(0), 0, 0, 0));
        }
        String[] split = locationString.split("\\,");
        World world = Bukkit.getServer().getWorlds().get(0);
        double x = 0;
        double y = 0;
        double z = 0;
        try {
            if (split.length > 0) {
                x = Double.parseDouble(split[0]);
            }
            if (split.length > 1) {
                y = Double.parseDouble(split[1]);
            }
            if (split.length > 2) {
                z = Double.parseDouble(split[2]);
            }
            if (split.length > 3) {
                if (Bukkit.getServer().getWorld(split[3]) != null) world = Bukkit.getServer().getWorld(split[3]);
            }
        } catch (Exception ignored) {
        }
        return new LocationString(new Location(world, x, y, z));
    }

    @Override
    public String toString() {
        final String SEP = ",";
        String x = Double.toString(getLocation().getX());
        String y = Double.toString(getLocation().getY());
        String z = Double.toString(getLocation().getZ());
        return x + SEP + y + SEP + z;
    }
}

