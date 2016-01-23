package com.talesdev.copsandcrims.weapon.bullet;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.module.FiringMode;
import com.talesdev.copsandcrims.weapon.module.ScopeModule;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;
import com.talesdev.core.world.NearbyEntity;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * DelayedBullet
 *
 * @author MoKunz
 */
public class DelayedBullet extends Bullet {
    long time = System.currentTimeMillis();
    private int tickCounter = 1;
    private Location delayedLocation;
    private Vector delayedVector;
    private NearbyEntity<LivingEntity> nearbyEntity = new NearbyEntity<>(getPlayer().getEyeLocation(), LivingEntity.class);
    private int processedIteration = 0;
    private int iterationPerTick = 100;

    public DelayedBullet(Player player, BulletListener action, double damage, BulletAccuracy accuracy, int speed, double recoil, Weapon weapon) {
        super(player, action, damage, accuracy, recoil, weapon);
        setSpeed(speed);
    }

    public DelayedBullet(Player player, BulletListener action, double damage, BulletAccuracy accuracy, double recoil, Weapon weapon) {
        super(player, action, damage, accuracy, recoil, weapon);
    }

    public DelayedBullet(DelayedBullet bullet) {
        this(bullet.getPlayer(), bullet.getListener(), bullet.getDamage(), new BulletAccuracy(bullet.getBulletAccuracy()), bullet.getSpeed(), bullet.getRecoil(), bullet.getWeapon());
        this.setWeapon(bullet.getWeapon());
        this.setSpeed(bullet.getSpeed());
        this.setDamage(bullet.getDamage());
        this.setHeadShotDamage(bullet.getHeadShotDamage());
        this.setUpperLegDamage(bullet.getUpperLegDamage());
        this.setLowerLegDamage(bullet.getLowerLegDamage());
        this.setRayParameter(3000, 0.05, 6);
    }

    public int getSpeed() {
        return this.iterationPerTick;
    }

    public void setSpeed(int speed) {
        this.iterationPerTick = speed;
    }

    @Override
    public void fire() {
        DelayedBulletTask task = new DelayedBulletTask(new DelayedBullet(this));
        task.runTaskTimer(CopsAndCrims.getPlugin(), 0, 1);
        // bolt scoping
        if (getWeapon().containsModule(ScopeModule.class)) {
            if (getWeapon().getModule(ShootingModule.class).getFiringMode().equals(FiringMode.BOLT)) {
                getWeapon().getModule(ScopeModule.class).cancelZoom(CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(getPlayer()));
            }
        }
    }

    private void oneTickPassed() {
        tickCounter++;
    }

    private void iterationProcessed(int total) {
        processedIteration += total;
    }

    private int getProcessed() {
        return processedIteration;
    }
    private boolean processingFinished() {
        return processedIteration >= getMaxIteration();
    }

    public void startProcessing() {
        // action hook
        time = System.currentTimeMillis();
        if (action != null) action.prepareFiring(this);
    }

    public void process() {
        // this do trick!
        if (isCancel()) return;
        // do some works
        if (processingFinished()) {
            cancel();
            return;
        }
        // acceleration
        boolean skipChecking = false;
        long time2 = System.currentTimeMillis();
        for (int i = getProcessed(); i < getProcessed() + iterationPerTick; i++) {
            // shoot
            delayedVector = rayTrace.iterate(distance);
            delayedLocation = delayedVector.toLocation(getWorld());
            // bullet fire
            if (!iterateBullet(getProcessed() + i, delayedLocation)) {
                iterationProcessed(getProcessed() + i);
                cancel();
                return;
            }
            // check if hit box
            if (delayedLocation.getBlock().getType().isSolid()) {
                if (!foundBlock(delayedLocation)) {
                    iterationProcessed(getProcessed() + i);
                    cancel();
                    return;
                }
            }
            System.out.println("Time used ( " + i + ") : " + (System.currentTimeMillis() - time2));
            time2 = System.currentTimeMillis();
            // set location of scanner
            nearbyEntity.setLocation(delayedLocation);
            if (skipChecking) continue;
            // acceleration
            LivingEntity livingEntity = nearbyEntity.findNearestInRadius(25, true);
            if (livingEntity != null) {
                if (livingEntity.getEntityId() == getPlayer().getEntityId()) {
                    skipChecking = true;
                }
            } else {
                skipChecking = true;
            }
            // find
            LivingEntity entity = nearbyEntity.findNearestInRadius(bias, true);
            if (entity != null) {
                if (!foundEntity(delayedLocation, delayedVector, entity)) {
                    iterationProcessed(getProcessed() + i);
                    cancel();
                    return;
                }
            }
            if (action != null) action.afterBulletFire(this, i, distance);
        }
        // processed
        iterationProcessed(iterationPerTick);
        // check if processing should stop and send signal to worker
        if (processingFinished()) {
            cancel();
            return;
        }
        // finish processing in each tick
        oneTickPassed();
    }

    public void finished() {
        // finish
        if (action != null) action.finishFiring(this);
        System.out.println("Time used on " + this.toString() + " : " + (System.currentTimeMillis() - time));
        // clean up
        rayTrace.reset();
    }
}
