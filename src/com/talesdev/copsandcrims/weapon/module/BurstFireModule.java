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
public class BurstFireModule extends WeaponModule {

    private boolean enabled = false;
    private int burstFireDelay = 1;
    private int burstFireCooldown = 20;
    private int burstFireBullet = 3;
    private BulletAccuracy burstFireAccuracy;

    public BurstFireModule() {
        this(true);
    }

    public BurstFireModule(boolean enabled) {
        this(enabled, new BulletAccuracy(
                new Accuracy(new Range(-1, 1), new Range(-1, 1), new Range(-1, 1)),
                new Accuracy(new Range(0, 0), new Range(0, 0), new Range(0, 0))
        ));
    }

    public BurstFireModule(boolean enabled, BulletAccuracy burstFireAccuracy) {
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
        return new BulletTask(delayedBullet, getBurstFireBullet(), getWeapon()).runTaskTimer(getPlugin(), 0, getBurstFireDelay());
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

    public int getBurstFireDelay() {
        return burstFireDelay;
    }

    public void setBurstFireDelay(int burstFireDelay) {
        this.burstFireDelay = burstFireDelay;
    }

    public int getBurstFireBullet() {
        return burstFireBullet;
    }

    public void setBurstFireBullet(int burstFireBullet) {
        this.burstFireBullet = burstFireBullet;
    }

    public int getBurstFireCooldown() {
        return burstFireCooldown;
    }

    public void setBurstFireCooldown(int burstFireCooldown) {
        this.burstFireCooldown = burstFireCooldown;
    }
}
