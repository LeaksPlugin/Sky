package com.talesdev.core.world;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * Ray tracing
 */
public class RayTrace {
    Vector direction, currentPos;
    Location location;
    public RayTrace(Location origin) {
        this.location = origin;
        this.currentPos = new Vector(location.getX(), location.getY(), location.getZ());
        this.direction = RayTrace.toVector(location.getYaw(), location.getPitch());
    }

    public Vector iterate(double distance) {
        return this.currentPos.add(direction.clone().multiply(distance));
    }

    public static Vector toVector(double yaw, double pitch) {
        double rotX = yaw, rotY = pitch, cosY = Math.cos(Math.toRadians(rotY));

        double x = (-cosY * Math.sin(Math.toRadians(rotX))),
                y = (-Math.sin(Math.toRadians(rotY))),
                z = (cosY * Math.cos(Math.toRadians(rotX)));

        return new Vector(x, y, z); // you may have to cast to double here
    }

    public void reset(){
        this.currentPos = new Vector(location.getX(), location.getY(), location.getZ());
        this.direction = RayTrace.toVector(location.getYaw(), location.getPitch());
    }
}
