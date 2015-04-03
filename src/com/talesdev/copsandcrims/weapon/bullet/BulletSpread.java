package com.talesdev.copsandcrims.weapon.bullet;

/**
 * Bullet Spread
 *
 * @author MoKunz
 */
public class BulletSpread {
    private double pitchBias = 0.0;
    private double yawBias = 0.0;

    public BulletSpread(double pitchBias, double yawBias) {
        this.pitchBias = pitchBias;
        this.yawBias = yawBias;
    }

    public double getPitchBias() {
        return pitchBias;
    }

    public double getYawBias() {
        return yawBias;
    }
}
