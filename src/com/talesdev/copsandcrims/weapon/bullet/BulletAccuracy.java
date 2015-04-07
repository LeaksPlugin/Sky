package com.talesdev.copsandcrims.weapon.bullet;

import com.talesdev.core.math.Range;

/**
 * A class stored information about bullet accuracy
 *
 * @author MoKunz
 */
public class BulletAccuracy {
    private double recoil;
    private Accuracy defaultAccuracy;
    private Accuracy sneakingAccuracy;
    private Accuracy walkingAccuracy;
    private Accuracy sprintingAccuracy;
    private Accuracy jumpingAccuracy;

    public BulletAccuracy(Range xSpread, Range ySpread, Range zSpread) {
        this(new Accuracy(xSpread, ySpread, zSpread));
    }

    public BulletAccuracy(Accuracy defaultAccuracy) {
        this(defaultAccuracy, defaultAccuracy, defaultAccuracy, defaultAccuracy, defaultAccuracy);
    }

    public BulletAccuracy(Accuracy defaultAccuracy, Accuracy sneakingAccuracy, Accuracy walkingAccuracy, Accuracy sprintingAccuracy, Accuracy jumpingAccuracy) {
        this.defaultAccuracy = defaultAccuracy;
        this.sneakingAccuracy = sneakingAccuracy;
        this.walkingAccuracy = walkingAccuracy;
        this.sprintingAccuracy = sprintingAccuracy;
        this.jumpingAccuracy = jumpingAccuracy;
    }

    public Accuracy getDefaultAccuracy() {
        return defaultAccuracy;
    }

    public Accuracy getSneakingAccuracy() {
        return sneakingAccuracy;
    }

    public void setSneakingAccuracy(Accuracy sneakingAccuracy) {
        this.sneakingAccuracy = sneakingAccuracy;
    }

    public Accuracy getSprintingAccuracy() {
        return sprintingAccuracy;
    }

    public void setSprintingAccuracy(Accuracy sprintingAccuracy) {
        this.sprintingAccuracy = sprintingAccuracy;
    }

    public Accuracy getWalkingAccuracy() {
        return walkingAccuracy;
    }

    public void setWalkingAccuracy(Accuracy walkingAccuracy) {
        this.walkingAccuracy = walkingAccuracy;
    }

    public Accuracy getJumpingAccuracy() {
        return jumpingAccuracy;
    }

    public void setJumpingAccuracy(Accuracy jumpingAccuracy) {
        this.jumpingAccuracy = jumpingAccuracy;
    }


    public double getRecoil() {
        return recoil;
    }

    public void setRecoil(double recoil) {
        this.recoil = recoil;
    }

    public Range getXSpread() {
        return defaultAccuracy.getXSpread();
    }

    public void setXSpread(Range xSpread) {
        defaultAccuracy.setXSpread(xSpread);
    }

    public Range getYSpread() {
        return defaultAccuracy.getYSpread();
    }

    public void setYSpread(Range ySpread) {
        defaultAccuracy.setYSpread(ySpread);
    }

    public Range getZSpread() {
        return defaultAccuracy.getZSpread();
    }

    public void setZSpread(Range zSpread) {
        defaultAccuracy.setZSpread(zSpread);
    }
}
