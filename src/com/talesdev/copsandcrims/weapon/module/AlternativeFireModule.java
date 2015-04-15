package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.weapon.bullet.Accuracy;
import com.talesdev.copsandcrims.weapon.bullet.BulletAccuracy;
import com.talesdev.copsandcrims.weapon.bullet.BulletTask;
import com.talesdev.copsandcrims.weapon.bullet.DelayedBullet;
import com.talesdev.core.math.Range;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * BurstFire module of weapon
 *
 * @author MoKunz
 */
public class AlternativeFireModule extends WeaponModule {

    private boolean enabled = false;
    private int alternativeFireDelay = 1;
    private int alternativeFireCooldown = 20;
    private int alternativeFireBullet = 3;
    private double alternativeRecoil = 0.1D;
    private double damage = -1;
    private double headShotDamage = -1;
    private double lowerLegDamage = -1;
    private double upperLegDamage = -1;
    private BulletAccuracy burstFireAccuracy;

    public AlternativeFireModule() {
        this(true);
    }

    public AlternativeFireModule(boolean enabled) {
        this(enabled, new BulletAccuracy(
                new Accuracy(new Range(-1, 1), new Range(-1, 1), new Range(-1, 1)),
                new Accuracy(new Range(0, 0), new Range(0, 0), new Range(0, 0))
        ));
    }

    public AlternativeFireModule(boolean enabled, BulletAccuracy burstFireAccuracy) {
        super("BurstFire");
        this.enabled = enabled;
        this.burstFireAccuracy = burstFireAccuracy;
    }

    public boolean goingToBurstFire(Player player) {
        return player.isSneaking();
    }

    public DelayedBullet createBurstFireBullet(DelayedBullet oldBullet) {
        DelayedBullet bullet = new DelayedBullet(
                oldBullet.getPlayer(), oldBullet.getListener(), oldBullet.getDamage(), new BulletAccuracy(getBurstFireAccuracy()), 0.0D
        );
        if (getHeadShotDamage() > -1) {
            bullet.setHeadShotDamage(getHeadShotDamage());
        } else {
            bullet.setHeadShotDamage(oldBullet.getHeadShotDamage());
        }
        if (getDamage() > -1) {
            bullet.setDamage(getDamage());
        } else {
            bullet.setDamage(oldBullet.getDamage());
        }
        if (getLowerLegDamage() > -1) {
            bullet.setLowerLegDamage(getLowerLegDamage());
        } else {
            bullet.setLowerLegDamage(oldBullet.getLowerLegDamage());
        }
        if (getUpperLegDamage() > -1) {
            bullet.setUpperLegDamage(getUpperLegDamage());
        } else {
            bullet.setUpperLegDamage(oldBullet.getUpperLegDamage());
        }
        bullet.setRayParameter(2000, 0.05, 4);
        bullet.setSpeed(oldBullet.getSpeed());
        return bullet;
    }

    public BukkitTask runBurstFireTask(DelayedBullet delayedBullet) {
        return new BulletTask(delayedBullet, getAlternativeFireBullet(), getWeapon()).runTaskTimer(getPlugin(), 0, getAlternativeFireDelay());
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public BulletAccuracy getBurstFireAccuracy() {
        return burstFireAccuracy;
    }

    public void setBurstFireAccuracy(BulletAccuracy burstFireAccuracy) {
        this.burstFireAccuracy = burstFireAccuracy;
    }

    public int getAlternativeFireDelay() {
        return alternativeFireDelay;
    }

    public void setAlternativeFireDelay(int alternativeFireDelay) {
        this.alternativeFireDelay = alternativeFireDelay;
    }

    public int getAlternativeFireBullet() {
        return alternativeFireBullet;
    }

    public void setAlternativeFireBullet(int alternativeFireBullet) {
        this.alternativeFireBullet = alternativeFireBullet;
    }

    public int getAlternativeFireCooldown() {
        return alternativeFireCooldown;
    }

    public void setAlternativeFireCooldown(int alternativeFireCooldown) {
        this.alternativeFireCooldown = alternativeFireCooldown;
    }

    public double getAlternativeRecoil() {
        return alternativeRecoil;
    }

    public void setAlternativeRecoil(double alternativeRecoil) {
        this.alternativeRecoil = alternativeRecoil;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getHeadShotDamage() {
        return headShotDamage;
    }

    public void setHeadShotDamage(double headShotDamage) {
        this.headShotDamage = headShotDamage;
    }

    public double getLowerLegDamage() {
        return lowerLegDamage;
    }

    public void setLowerLegDamage(double lowerLegDamage) {
        this.lowerLegDamage = lowerLegDamage;
    }

    public double getUpperLegDamage() {
        return upperLegDamage;
    }

    public void setUpperLegDamage(double upperLegDamage) {
        this.upperLegDamage = upperLegDamage;
    }
}
