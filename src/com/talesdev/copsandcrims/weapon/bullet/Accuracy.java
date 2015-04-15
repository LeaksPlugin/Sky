package com.talesdev.copsandcrims.weapon.bullet;

import com.talesdev.core.math.MathRandom;
import com.talesdev.core.math.Range;
import org.bukkit.util.Vector;

/**
 * A class stored information about bullet accuracy in each scenario
 *
 * @author MoKunz
 */
public class Accuracy {
    private Range xSpread, ySpread, zSpread;

    public Accuracy(Range xSpread, Range ySpread, Range zSpread) {
        this.xSpread = xSpread;
        this.ySpread = ySpread;
        this.zSpread = zSpread;
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

    public Vector toVector() {
        return new Vector(
                MathRandom.randomRange(
                        getXSpread().getStart(),
                        getXSpread().getEnd()) / 1000D,
                MathRandom.randomRange(
                        getYSpread().getStart(),
                        getYSpread().getEnd()) / 1000D,
                MathRandom.randomRange(
                        getZSpread().getStart(),
                        getZSpread().getEnd()) / 1000D
        );
    }

    public Accuracy cloneAccuracy() {
        Range x = getXSpread(), y = getYSpread(), z = getZSpread();
        return new Accuracy(new Range(x.getStart(), x.getEnd()), new Range(y.getStart(), y.getEnd()), new Range(z.getStart(), z.getEnd()));
    }
}
