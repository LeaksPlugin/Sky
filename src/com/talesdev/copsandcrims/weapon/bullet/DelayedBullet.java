package com.talesdev.copsandcrims.weapon.bullet;

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

    @Override
    public void fire() {
        // action
        if (action != null) action.prepareFiring(this);
        // start shooting ray
        for (int i = 0; i < getMaxIteration(); i++) {
            // shoot
            delayedVector = rayTrace.iterate(distance);
            delayedLocation = delayedVector.toLocaton(getWorld());
            // bullet fire
            if (!iterateBullet(i, delayedLocation)) break;
            // check if hit box
            if (delayedLocation.getBlock().getType().isSolid()) {
                if (!foundBlock(delayedLocation)) break;
            }
            // set location of scanner
            nearbyEntity.setLocation(delayedLocation);
            // find
            LivingEntity entity = nearbyEntity.findNearestInRadius(bias, true);
            if (entity != null) {
                if (!foundEntity(delayedLocation, delayedVector, entity)) break;
            }
            if (action != null) action.afterBulletFire(this, i, distance);
        }
        // finish
        if (action != null) action.finishFiring(this);
        // clean up
        rayTrace.reset();
    }

    private void oneTickPassed() {
        tickCounter++;
    }

    private void iterationProcessed(int total) {
        processedIteration += total;
    }

    private boolean processingFinished() {
        return processedIteration >= getMaxIteration();
    }

    public void startProcessing() {

    }

    public void process() {

        // finish processing in each tick
        oneTickPassed();
    }

    public void finished() {

    }
}
