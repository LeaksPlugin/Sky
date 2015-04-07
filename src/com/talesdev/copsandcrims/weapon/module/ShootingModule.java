package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.WeaponCooldownTag;
import com.talesdev.copsandcrims.weapon.bullet.*;
import com.talesdev.core.item.MaterialComparator;
import com.talesdev.core.item.RightClickable;
import com.talesdev.core.math.Range;
import com.talesdev.core.player.ClickingAction;
import com.talesdev.core.world.Sound;
import com.talesdev.core.world.SoundEffect;
import com.talesdev.core.world.SoundEffectInterface;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Weapon shooting module
 *
 * @author MoKunz
 */
public class ShootingModule extends WeaponModule {
    private FiringMode firingMode;
    private int cooldownTime = 5;
    private double damage = 4;
    private double headShotDamage = 8;
    private double recoil = 1.0D;
    private int bulletCount = 1;
    private int bulletDelay = 1;
    private int speed = 500;
    private BulletListener listener = null;
    private BulletAccuracy accuracy = null;
    private SoundEffectInterface soundEffect = null;

    public ShootingModule() {
        super("Shooting");
        setSoundEffect(SoundEffect.MOB_SKELETON_DEATH);
        setAccuracy(new BulletAccuracy(
                new Accuracy(new Range(-1, 1), new Range(-1, 1), new Range(-1, 1)), // default
                new Accuracy(new Range(-1, 1), new Range(-1, 1), new Range(-1, 1)), // sneaking
                new Accuracy(new Range(-1, 1), new Range(-1, 1), new Range(-1, 1)), // walking
                new Accuracy(new Range(-1, 1), new Range(-1, 1), new Range(-1, 1)), // sprinting
                new Accuracy(new Range(-1, 1), new Range(-1, 1), new Range(-1, 1)) // jumping
        ));
        setFiringMode(FiringMode.SEMI_AUTO);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        // is weapon
        if (getWeapon().isWeapon(event.getItem())) {
            if (event.getClickedBlock() != null) {
                MaterialComparator comparator = new MaterialComparator(new RightClickable());
                if (comparator.containThisMaterial(event.getClickedBlock().getType())) {
                    event.setUseItemInHand(Event.Result.DENY);
                    return;
                }
            }
            if (ClickingAction.isRightClick(event.getAction())) {
                shootBullet(event);
            } else if (ClickingAction.isLeftClick(event.getAction())) {
                // TODO : Attachment
            }
            event.setUseInteractedBlock(Event.Result.DENY);
            event.setUseItemInHand(Event.Result.DENY);
        }
    }

    private void shootBullet(PlayerInteractEvent event) {
        WeaponCooldownTag tag = new WeaponCooldownTag(getCooldownTime(), event.getPlayer());
        if (!tag.isCooldown()) {
            // start shooting (no cooldown now!)
            CvCPlayer player = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
            double recoil = 0.0;
            if (player != null) recoil = player.getPlayerRecoil().getRecoil(getWeapon());
            // construct
            DelayedBullet bullet = new DelayedBullet(
                    event.getPlayer(), getListener(), getDamage(), getAccuracy(), recoil
            );
            bullet.setHeadShotDamage(getHeadShotDamage());
            bullet.setRayParameter(2000, 0.05, 4);
            bullet.setSpeed(getSpeed());
            // shoot bullet
            (new BulletTask(bullet, getBulletCount())).runTaskTimer(CopsAndCrims.getPlugin(), 0, getBulletDelay());
            // play sound
            (new Sound(getSoundEffect(), 1.0F, 1.0F)).playSound(event.getPlayer().getLocation());
            // add recoil
            if (player != null) player.getPlayerRecoil().addRecoil(getWeapon(), getRecoil());
            // add cooldown
            tag.attach();
        }
    }

    public FiringMode getFiringMode() {
        return firingMode;
    }

    public void setFiringMode(FiringMode firingMode) {
        this.firingMode = firingMode;
    }

    public int getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(int cooldownTime) {
        this.cooldownTime = cooldownTime;
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

    public double getRecoil() {
        return recoil;
    }

    public void setRecoil(double recoil) {
        this.recoil = recoil;
    }

    public BulletListener getListener() {
        return listener;
    }

    public void setListener(BulletListener listener) {
        this.listener = listener;
    }

    public BulletAccuracy getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(BulletAccuracy accuracy) {
        this.accuracy = accuracy;
    }

    public SoundEffectInterface getSoundEffect() {
        return soundEffect;
    }

    public void setSoundEffect(SoundEffectInterface soundEffect) {
        this.soundEffect = soundEffect;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBulletCount() {
        return bulletCount;
    }

    public void setBulletCount(int bulletCount) {
        this.bulletCount = bulletCount;
    }

    public int getBulletDelay() {
        return bulletDelay;
    }

    public void setBulletDelay(int bulletDelay) {
        this.bulletDelay = bulletDelay;
    }
}
