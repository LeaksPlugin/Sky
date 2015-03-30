package com.talesdev.copsandcrims.weapon.bullet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.talesdev.core.entity.BoundingBox;
import com.talesdev.core.world.NearbyEntity;
import com.talesdev.core.world.RayTrace;
import com.talesdev.core.world.SoundEffect;
import com.talesdev.core.world.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;

/**
 * Class represent a bullet
 * @author MoKunz
 */
public class Bullet {
    protected RayTrace rayTrace;
    private World world;
    private Player player;
    protected BulletListener action;
    private int maxIteration = 2000;
    protected double distance = 0.05;
    protected double bias = 3;
    protected double damage = 4;
    private boolean cancel = false;
    public Bullet(Player player,BulletListener action,double damage){
        this.player = player;
        if(action != null) this.action = action;
        this.rayTrace = new RayTrace(this.player.getEyeLocation());
        this.world = this.player.getWorld();
        this.damage = damage;
        if(action != null) this.action.bulletCreated(this);
    }

    public int getMaxIteration() {
        return maxIteration;
    }

    public Player getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }
    public void setDamage(double damage){
        this.damage = damage;
    }
    public double getDamage(){
        return damage;
    }
    public void setRayParameter(int maxIteration,double distancePerIteration,double rayBias){
        this.maxIteration = maxIteration;
        this.distance = distancePerIteration;
        this.bias = rayBias;
    }
    public boolean isCancel(){
        return cancel;
    }
    public void cancel(){
        this.cancel = true;
    }
    public BulletListener getListener(){
        return action;
    }
    public void fire(){
        // ray tracing engine
        Location currentLocation;
        Vector currentVector;
        // entity scanner
        NearbyEntity<LivingEntity> nearbyEntity = new NearbyEntity<>(player.getEyeLocation(), LivingEntity.class);
        // action
        if(action != null) action.prepareFiring(this);
        // start shooting ray
        for (int i = 0; i < maxIteration; i++) {
            // shoot
            currentVector = rayTrace.iterate(distance);
            currentLocation = currentVector.toLocation(world);
            // bullet fire
            if (!iterateBullet(i, currentLocation)) break;
            // check if hit box
            if (currentLocation.getBlock().getType().isSolid()) {
                if (!foundBlock(currentLocation)) break;
            }
            // set location of scanner
            nearbyEntity.setLocation(currentLocation);
            // find
            LivingEntity entity = nearbyEntity.findNearestInRadius(bias, true);
            if(entity != null){
                if (!foundEntity(currentLocation, currentVector, entity)) break;
            }
            if(action != null) action.afterBulletFire(this,i,distance);
        }
        // finish
        if(action != null) action.finishFiring(this);
        // clean up
        rayTrace.reset();
    }

    protected boolean iterateBullet(int i, Location location) {
        if (action != null) {
            action.bulletFire(this, i, distance);
            if (action instanceof BulletParticle && (i % 50 == 0))
                ((BulletParticle) action).createParticle(this, location, i, distance);
        } else {
            if (i > 40 && (i % 50 == 0)) createBulletParticle(location);
        }
        return true;
    }

    protected boolean foundBlock(Location location) {
        BulletHitResult result = new BulletHitResult(this, location, location.getBlock());
        if (action != null) {
            action.bulletHitObject(result);
            if (action instanceof BulletParticle) ((BulletParticle) action).createHitParticle(result);
        } else {
            world.playEffect(location.getBlock().getLocation(), Effect.STEP_SOUND, location.getBlock().getType());
        }
        cancel();
        return false;
    }

    protected boolean foundEntity(Location currentLocation, Vector currentVector, Entity entity) {
        if (entity.getName().equals(player.getName())) return true;
        BoundingBox box = new BoundingBox(entity);
        if (box.isInside(currentVector)) {
            // bullet particle
            BulletHitResult result = new BulletHitResult(this, currentLocation, entity);
            if (action != null) {
                action.bulletHitObject(result);
                if (action instanceof BulletParticle) ((BulletParticle) action).createHitParticle(result);
            }
            // damage entity
            if (entity instanceof LivingEntity) {
                if (((LivingEntity) entity).getHealth() - damage > 0) {
                    // damage packet
                    PacketContainer entityStatus = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
                    entityStatus.getIntegers().write(0, entity.getEntityId());
                    entityStatus.getBytes().write(0, (byte) 2);
                    try {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            ProtocolLibrary.getProtocolManager().sendServerPacket(p, entityStatus);
                        }
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    ((LivingEntity) entity).setHealth(((LivingEntity) entity).getHealth() - damage);
                    SoundEffect.getMobHurtSound(entity).playSound(currentLocation);
                } else {
                    ((LivingEntity) entity).damage(damage + 1);
                    cancel();
                }
                return false;
            }
        }
        return true;
    }
    private void createBulletParticle(Location location){
        ParticleEffect.FLAME.display(0.01F, 0.01F, 0.01F, 0.001F, 1, location, 128);
    }
}