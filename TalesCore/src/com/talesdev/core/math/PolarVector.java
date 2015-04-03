package com.talesdev.core.math;

import org.bukkit.util.Vector;

/**
 * Polar Vector
 *
 * @author MoKunz
 */
public class PolarVector {
    private float pitch, yaw;
    float deltaX;
    float deltaY;
    float deltaZ;
    private float length;

    public PolarVector(Vector origin, Vector direction) {
        this.length = (float) origin.distance(direction);
        float deltaX = (float) (direction.getX() - origin.getX());
        float deltaY = (float) (direction.getY() - origin.getY());
        float deltaZ = (float) (direction.getZ() - origin.getZ());
        float deltaXZ = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));
        this.pitch = (float) -Math.toDegrees(Math.atan(deltaY / deltaXZ));
        // yaw
        float yaw = 0;
        // Set yaw
        if (deltaX != 0) {
            // Set yaw start value based on dx
            if (deltaX < 0) {
                yaw = (float) (1.5 * Math.PI);
            } else {
                yaw = (float) (0.5 * Math.PI);
            }
            yaw -= Math.atan(deltaZ / deltaX);
        } else if (deltaZ < 0) {
            yaw = (float) Math.PI;
        }
        this.yaw = (float) Math.toDegrees(yaw);
        this.yaw = (float) Math.floor(-this.yaw);
    }

    public PolarVector(Vector direction) {
        this(new Vector(0, 0, 0), direction);
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public Vector toVector() {
        float pitchRadians = (float) Math.toRadians(pitch);
        float yawRadians = (float) Math.toRadians(yaw);

        float sinPitch = (float) Math.sin(pitchRadians);
        float cosPitch = (float) Math.cos(pitchRadians);
        float sinYaw = (float) Math.sin(yawRadians);
        float cosYaw = (float) Math.cos(yawRadians);

        return new Vector(cosPitch * cosYaw, cosPitch * sinYaw, sinPitch);
    }
}
