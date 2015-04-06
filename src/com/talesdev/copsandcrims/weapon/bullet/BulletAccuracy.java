package com.talesdev.copsandcrims.weapon.bullet;

import com.talesdev.core.math.Range;

/**
 * A class stored information about bullet accuracy
 *
 * @author MoKunz
 */
public class BulletAccuracy {
    private double recoil;
    private Range xSpread, ySpread, zSpread;

    public BulletAccuracy(Range xSpread, Range ySpread, Range zSpread, double recoil) {
        this.xSpread = xSpread;
        this.ySpread = ySpread;
        this.zSpread = zSpread;
        this.recoil = recoil;
    }

    public double getRecoil() {
        return recoil;
    }

    public void setRecoil(double recoil) {
        this.recoil = recoil;
    }

    public Range getXSpread() {
        return xSpread;
    }

    public void setXSpread(Range xSpread) {
        this.xSpread = xSpread;
    }

    public Range getYSpread() {
        return ySpread;
    }

    public void setYSpread(Range ySpread) {
        this.ySpread = ySpread;
    }

    public Range getZSpread() {
        return zSpread;
    }

    public void setZSpread(Range zSpread) {
        this.zSpread = zSpread;
    }
}
