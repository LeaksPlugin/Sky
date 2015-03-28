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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;

/**
 * Class represent a bullet
 * @author MoKunz
 */
public class Bullet {
    private RayTrace rayTrace;
    private World world;
    private Player player;
    private BulletListener action;
    private int maxIteration = 2000;
    private double distance = 0.05;
    private double bias = 3;
    private double damage = 4;
    private boolean cancel = false;
    public Bullet(Player player,BulletListener action,double damage){
        this.player = player;
        if(action != null) this.action = action;
        this.rayTrace = new RayTrace(this.player.getEyeLocation());
        this.world = this.player.getWorld();
        this.damage = damage;
        if(action != null) this.action.bulletCreated(this);
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
            if(action != null){
                action.bulletFire(this,i,distance);
                if (action instanceof BulletParticle && (i % 50 == 0))
                    ((BulletParticle) action).createParticle(this, currentLocation, i, distance);
            }
            else{
                if (i > 40 && (i % 50 == 0)) createBulletParticle(currentLocation);
            }
            // check if hit box
            if (currentLocation.getBlock().getType().isSolid()) {
                BulletHitResult result = new BulletHitResult(this,currentLocation,currentLocation.getBlock());
                if(action != null){
                    action.bulletHitObject(result);
                    if(action instanceof BulletParticle) ((BulletParticle)action).createHitParticle(result);
                }
                else{
                    world.playEffect(currentLocation.getBlock().getLocation(), Effect.STEP_SOUND, currentLocation.getBlock().getType());
                }
                //p.sendMessage(ChatColor.GREEN + "Ray distance : " + i*0.1);
                cancel = true;
                break;
            }
            // set location of scanner
            nearbyEntity.setLocation(currentLocation);
            // find
            LivingEntity entity = nearbyEntity.findNearestInRadius(bias, true);
            if(entity != null){
                if (entity.getName().equals(player.getName())) continue;
                BoundingBox box = new BoundingBox(entity);
                if (box.isInside(currentVector)) {
                    // bullet particle
                    BulletHitResult result = new BulletHitResult(this,currentLocation,entity);
                    if(action != null){
                        action.bulletHitObject(result);
                        if(action instanceof BulletParticle) ((BulletParticle)action).createHitParticle(result);
                    }
                    // damage entity
                    if(entity.getHealth() - damage > 0){
                        // damage packet
                        PacketContainer entityStatus = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
                        entityStatus.getIntegers().write(0, entity.getEntityId());
                        entityStatus.getBytes().write(0, (byte) 2);
                        try {
                            for(Player p : Bukkit.getServer().getOnlinePlayers()){
                                ProtocolLibrary.getProtocolManager().sendServerPacket(p, entityStatus);
                            }
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                        entity.setHealth(entity.getHealth() - damage);
                        SoundEffect.getMobHurtSound(entity).playSound(currentLocation);
                    }
                    else{
                        entity.damage(damage + 1);
                        cancel = true;
                    }
                    break;
                }
            }
            if(action != null) action.afterBulletFire(this,i,distance);
        }
        // finish
        if(action != null) action.finishFiring(this);
        // clean up
        rayTrace.reset();
    }
    private void createBulletParticle(Location location){
        ParticleEffect.FLAME.display(0.01F, 0.01F, 0.01F, 0.001F, 1, location, 128);
    }
}