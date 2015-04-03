package com.talesdev.copsandcrims.weapon.bullet;

import com.talesdev.copsandcrims.CopsAndCrims;
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
    private int tickCounter = 1;
    private Location delayedLocation;
    private Vector delayedVector;
    private NearbyEntity<LivingEntity> nearbyEntity = new NearbyEntity<>(getPlayer().getEyeLocation(), LivingEntity.class);
    private int processedIteration = 0;
    private int iterationPerTick = 100;

    public DelayedBullet(Player player, BulletListener action, double damage) {
        super(player, action, damage);
    }

    public DelayedBullet(DelayedBullet bullet) {
        super(bullet.getPlayer(), bullet.getListener(), bullet.getDamage());
    }

    @Override
    public void fire() {
        DelayedBulletTask task = new DelayedBulletTask(new DelayedBullet(this));
        task.runTaskTimer(CopsAndCrims.getPlugin(), 0, 1);
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
            // set location of scanner
            nearbyEntity.setLocation(delayedLocation);
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
        // clean up
        rayTrace.reset();
    }
}
