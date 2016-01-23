package com.talesdev.core.world.raytrace;

import org.bukkit.util.Vector;

/**
 * @author MoKunz
 */
public class TraceResult {
    private boolean found;
    private TraceableObject object;
    private Vector hit;
    private double distance;

    public TraceResult(boolean found, TraceableObject object, Vector hit, double distance) {
        this.found = found;
        this.object = object;
        this.hit = hit;
        this.distance = distance;
    }

    public boolean found() {
        return found;
    }

    public TraceableObject getObject() {
        return object;
    }

    public Vector getHit() {
        return hit;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "TraceResult{" +
                "found=" + found +
                ", object=" + object +
                ", hit=" + hit +
                ", distance=" + distance +
                '}';
    }
}
