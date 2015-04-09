package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.WeaponBullet;
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
import org.bukkit.scheduler.BukkitTask;

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
    private int maxBullet = 10;
    private int reloadTime = 60;

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
        if (event.getItem() != null) {
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
                    CvCPlayer cvCPlayer = getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
                    WeaponBullet weaponBullet = cvCPlayer.getPlayerBullet().getBullet(getWeapon().getName());
                    if ((!weaponBullet.isReloading()) && weaponBullet.getBulletCount() < weaponBullet.getMaxBullet()) {
                        (new BulletReloadTask(cvCPlayer, getWeapon(), event.getItem(), getReloadTime())).runTaskTimer(getPlugin(), 0, 1);
                    }
                }
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);
            }
        }
    }

    private void shootBullet(PlayerInteractEvent event) {
        CvCPlayer player = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(event.getPlayer());
        // prevent invalid state
        updateBulletCount(player);
        WeaponCooldownTag tag = new WeaponCooldownTag(getCooldownTime(), event.getPlayer());
        if (!tag.isCooldown()) {
            // start shooting (no cooldown now!)
            // check if player is reloading bullet
            if (player.getPlayerBullet().getBullet(getWeapon().getName()).isReloading()) {
                return;
            }
            // force reload if player has no bullet
            if (player.getPlayerBullet().getBullet(getWeapon().getName()).getBulletCount() <= 0) {
                (new BulletReloadTask(player, getWeapon(), event.getItem(), getReloadTime())).runTaskTimer(getPlugin(), 0, 1);
                return;
            }
            // get last recoil
            double recoil = 0.0;
            recoil = player.getPlayerRecoil().getRecoil(getWeapon());
            // construct
            DelayedBullet bullet = new DelayedBullet(
                    event.getPlayer(), getListener(), getDamage(), getAccuracy(), recoil
            );
            bullet.setHeadShotDamage(getHeadShotDamage());
            bullet.setRayParameter(2000, 0.05, 4);
            bullet.setSpeed(getSpeed());
            // burst fire
            BurstFireModule burstFireModule = getWeapon().getModule(BurstFireModule.class);
            if (burstFireModule != null) {
                if (burstFireModule.isEnabled() && burstFireModule.goingToBurstFire(player.getPlayer())) {
                    bullet = getWeapon().getModule(BurstFireModule.class).createBurstFireBullet(bullet);
                    tag.setCoolDownTime(burstFireModule.getBurstFireCooldown());
                }
            }
            bullet.setWeapon(getWeapon());
            // some bullet ...
            int usedBullet = getBulletCount();
            if (burstFireModule != null) {
                if (burstFireModule.isEnabled() && burstFireModule.goingToBurstFire(player.getPlayer())) {
                    usedBullet = burstFireModule.getBurstFireBullet();
                }
            }
            // shoot bullet
            if (burstFireModule != null) {
                if (burstFireModule.isEnabled() && burstFireModule.goingToBurstFire(player.getPlayer())) {
                    // System.out.println("Shooting in burst fire mode!");
                    burstFireModule.runBurstFireTask(bullet);
                } else {
                    // System.out.println("Shooting in normal mode!");
                    runBulletTask(createBulletTask(bullet));
                }
            } else {
                // System.out.println("Module BurstFire not found!");
                runBulletTask(createBulletTask(bullet));
            }
            // play sound
            (new Sound(getSoundEffect(), 1.0F, 1.0F)).playSound(event.getPlayer().getLocation());
            // check if player is not null
            // add recoil
            player.getPlayerRecoil().addRecoil(getWeapon(), getRecoil());
            // update bullet
            updateBulletCount(player);/*
            // reload if run out of bullet
            if (player.getPlayerBullet().getBullet(getWeapon().getName()).getBulletCount() <= 0) {
                (new BulletReloadTask(player, getWeapon(), event.getItem(), getReloadTime())).runTaskTimer(getPlugin(), 0, 1);
            }*/
            // add cooldown
            tag.attach();
        }
    }

    private BulletTask createBulletTask(Bullet bullet) {
        return new BulletTask(bullet, getBulletCount(), getWeapon());
    }

    private BukkitTask runBulletTask(BulletTask task) {
        return task.runTaskTimer(CopsAndCrims.getPlugin(), 0, getBulletDelay());
    }

    private void updateBulletCount(CvCPlayer player) {
        player.getPlayer().getItemInHand().setAmount(player.getPlayerBullet().getBullet(getWeapon().getName()).getBulletCount());
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

    public int getMaxBullet() {
        return maxBullet;
    }

    public void setMaxBullet(int maxBullet) {
        if (maxBullet < 0) return;
        this.maxBullet = maxBullet;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        if (reloadTime < 0) return;
        this.reloadTime = reloadTime;
    }
}
