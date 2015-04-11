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
                oldBullet.getPlayer(), oldBullet.getListener(), oldBullet.getDamage(), getBurstFireAccuracy(), 0.0D
        );
        bullet.setHeadShotDamage(oldBullet.getHeadShotDamage());
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
}
