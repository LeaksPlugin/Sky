package com.talesdev.core.math;

import org.bukkit.util.Vector;

/**
 * Angular vector
 *
 * @author MoKunz
 * @deprecated Buggy , deprecation might get removed if issue have been fixed
 */
@Deprecated()
public class AngularVector {
    private Vector origin, direction;
    private double pitch, yaw, roll;
    private double vectorPitch, vectorYaw, vectorRoll;

    public void setPitch(double vectorPitch) {
        this.vectorPitch = vectorPitch;
    }

    public void setYaw(double vectorYaw) {
        this.vectorYaw = vectorYaw;
    }

    public void setRoll(double vectorRoll) {
        this.vectorRoll = vectorRoll;
    }

    public AngularVector(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
        double vectorDistance = direction.distance(origin);
        double deltaX = direction.getX() - origin.getX();
        double deltaY = direction.getY() - origin.getY();
        vectorPitch = Math.asin(deltaY / vectorDistance) - this.pitch;
        vectorYaw = Math.asin(deltaX / vectorDistance) - this.yaw;
        vectorRoll = 0.0;
    }

    public void setAngle(double pitch, double yaw, double roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public double getPitch() {
        return vectorPitch;
    }

    public double getYaw() {
        return vectorYaw;
    }

    public double getRoll() {
        return vectorRoll;
    }

    public Vector getDirection() {
        double pitch, yaw;
        double vectorDistance = direction.distance(origin);
        double deltaX = direction.getX() - origin.getX();
        double deltaY = direction.getY() - origin.getY();
        pitch = Math.toDegrees(Math.asin(deltaY / vectorDistance)) - this.pitch;
        yaw = Math.toDegrees(Math.asin(deltaX / vectorDistance)) - this.yaw;
        Vector direction = new Vector();
        direction.setX(origin.getX() + (Math.cos(yaw) * Math.cos(pitch)));
        direction.setY(origin.getY() + (Math.sin(yaw) * Math.cos(pitch)));
        direction.setZ(origin.getZ() + Math.sin(pitch));
        return direction;
    }
}
