package com.talesdev.copsandcrims.weapon.bullet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.PlayerLastDamage;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.module.FiringMode;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;
import com.talesdev.core.entity.BoundingBox;
import com.talesdev.core.math.Range;
import com.talesdev.core.player.LastPlayerDamage;
import com.talesdev.core.world.NMSRayTrace;
import com.talesdev.core.world.NearbyEntity;
import com.talesdev.core.world.RayTrace;
import com.talesdev.core.world.SoundEffect;
import com.talesdev.core.world.particle.ParticleEffect;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;

/**
 * Class represent a bullet
 *
 * @author MoKunz
 */
public class Bullet {
    protected RayTrace rayTrace;
    protected BulletListener action;
    protected BulletAccuracy bulletAccuracy;
    protected double recoil = 0.0;
    protected Vector normalizedDirection;
    protected Vector origin;
    protected Vector direction;
    protected double distance = 0.05;
    protected double bias = 3;
    protected double damage = 4;
    protected double lowerLegDamage = 3;
    protected double upperLegDamage = 7;
    protected double headShotDamage = 8;
    private World world;
    private Player player;
    private int maxIteration = 2000;
    private boolean cancel = false;
    private Weapon weapon;
    private boolean debug = false;

    public Bullet(Player player, BulletListener action, double damage, BulletAccuracy accuracy, double recoil, Weapon weapon) {
        this.player = player;
        if (action != null) this.action = action;
        this.world = this.player.getWorld();
        this.weapon = weapon;
        this.damage = damage;
        this.origin = this.player.getEyeLocation().toVector();
        this.direction = this.player.getEyeLocation().getDirection();
        setRecoil(recoil);
        if (accuracy != null) {
            this.bulletAccuracy = accuracy;
        }
        // recoil comes first
        this.direction.add(new Vector(0, getRecoil() / 100D, 0));
        // bullet spread
        this.direction = calculateSpreadDirection();
        this.normalizedDirection = this.direction.clone().normalize();
        // create raytrace engine
        this.rayTrace = new RayTrace(world, origin, direction);
        if (action != null) this.action.bulletCreated(this);
    }

    private Vector calculateSpreadDirection() {
        if (getBulletAccuracy() != null) {
            BulletAccuracy accuracy = new BulletAccuracy(getBulletAccuracy());
            if (player.isSneaking() && player.isOnGround()) {
                if (getWeapon().getModule(ShootingModule.class).getFiringMode().equals(FiringMode.BOLT)) {
                    if (CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(player).isScoping()) {
                        return this.direction.add(accuracy.getSneakingAccuracy().toVector());
                    }
                } else {
                    return this.direction.add(accuracy.getSneakingAccuracy().toVector());
                }
            }
            /**
             * Buggy code
             * trying to reimplement this
             */
            // base accuracy
            Accuracy baseAccuracy = accuracy.getDefaultAccuracy();
            // factor
            Accuracy jumpFactor = accuracy.getJumpingAccuracy();
            Accuracy walkFactor = accuracy.getWalkingAccuracy();
            Accuracy sprintFactor = accuracy.getSprintingAccuracy();
            // jumping?
            if (!player.isOnGround()) {
                modifyAccuracy(baseAccuracy, jumpFactor);
            }
            if (CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(player).isWalking()) {
                modifyAccuracy(baseAccuracy, walkFactor);
            }
            if (player.isSprinting()) {
                modifyAccuracy(baseAccuracy, sprintFactor);
            }
            // normal bullet spread
            return this.direction.add(baseAccuracy.toVector());
        }
        return this.direction;
    }

    private void modifyAccuracy(Accuracy base, Accuracy add) {
        Range xBase = base.getXSpread(), yBase = base.getYSpread(), zBase = base.getZSpread();
        Range xAdd = add.getXSpread(), yAdd = add.getYSpread(), zAdd = add.getZSpread();
        base.setXSpread(new Range(xBase.getStart() + xAdd.getStart(), xBase.getEnd() + xAdd.getEnd()));
        base.setYSpread(new Range(yBase.getStart() + yAdd.getStart(), yBase.getEnd() + yAdd.getEnd()));
        base.setZSpread(new Range(zBase.getStart() + zAdd.getStart(), zBase.getEnd() + zAdd.getEnd()));
    }

    private boolean isOnAir() {
        return player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isTransparent();
    }

    public double getRecoil() {
        return recoil;
    }

    public void setRecoil(double recoil) {
        this.recoil = recoil;
    }

    public BulletAccuracy getBulletAccuracy() {
        return bulletAccuracy;
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

    public double getHeadShotDamage() {
        return headShotDamage;
    }

    public void setHeadShotDamage(double headShotDamage) {
        this.headShotDamage = headShotDamage;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
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

    public void setRayParameter(int maxIteration, double distancePerIteration, double rayBias) {
        this.maxIteration = maxIteration;
        this.distance = distancePerIteration;
        this.bias = rayBias;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void cancel() {
        this.cancel = true;
    }

    public BulletListener getListener() {
        return action;
    }

    public void fire() {
        // check if i should debug
        if (CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(player) != null)
            debug = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(player).isDebug();
        // ray tracing engine
        Location currentLocation;
        Vector currentVector;
        // entity scanner
        NearbyEntity<LivingEntity> nearbyEntity = new NearbyEntity<>(player.getEyeLocation(), LivingEntity.class);
        // action
        if (action != null) action.prepareFiring(this);
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
            if (entity != null) {
                if (!foundEntity(currentLocation, currentVector, entity)) break;
            }
            if (action != null) action.afterBulletFire(this, i, distance);
        }
        // finish
        if (action != null) action.finishFiring(this);
        // clean up
        rayTrace.reset();
    }

    /**
     * @param i        iteration round
     * @param location Current bullet location
     * @return true if you want to continue continue or false if you want to break
     */
    protected boolean iterateBullet(int i, Location location) {
        if (action != null) {
            action.bulletFire(this, i, distance);
            if (action instanceof BulletParticle && (i % 50 == 0))
                ((BulletParticle) action).createParticle(this, location, i, distance);
        } else {
            if (i > 40 && (i % 10 == 0)) {
                createBulletParticle(location);
            }
        }
        return true;
    }

    protected boolean foundBlock(Location location) {
        // check if block is passable
        NMSRayTrace nmsRayTrace = NMSRayTrace.rayTrace(getWorld(), location.toVector(), normalizedDirection);
        if (nmsRayTrace.getBlockVector() != null) {
            if (!location.getBlock().getLocation().equals(nmsRayTrace.getBlock().getLocation())) {
                return true;
            }
        }
        BulletHitResult result = new BulletHitResult(this, location, location.getBlock());
        if (location.getBlock().getType().equals(Material.BARRIER) || location.getBlock().getType().equals(Material.IRON_FENCE))
            return true;
        if (action != null) {
            action.bulletHitObject(result);
            if (action instanceof BulletParticle) ((BulletParticle) action).createHitParticle(result);
        } else {
            world.playEffect(location.getBlock().getLocation(), Effect.STEP_SOUND, location.getBlock().getType());
        }
        // break grass
        Block block = location.getBlock();
        if (
                block.getType().equals(Material.THIN_GLASS) ||
                        block.getType().equals(Material.STAINED_GLASS_PANE)) {
            getWorld().getBlockAt(location).setType(Material.AIR);
            return true;
        }
        cancel();
        return false;
    }

    protected boolean foundEntity(Location currentLocation, Vector currentVector, Entity entity) {
        double damage = getDamage();
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
                LivingEntity livingEntity = (LivingEntity) entity;
                // skip creative gamemode
                if (entity instanceof Player) {
                    if (((Player) entity).getGameMode().equals(GameMode.CREATIVE)) {
                        return false;
                    }
                }
                boolean isHeadShot = false;
                Location baseLocation = livingEntity.getLocation(), eyeLocation = livingEntity.getEyeLocation();
                double baseY = baseLocation.getY(),
                        eyeY = eyeLocation.getY(),
                        hitY = currentLocation.getY(),
                        deltaEH = Math.abs(eyeY - hitY),
                        deltaBH = Math.abs(hitY - baseY);
                // begin body part detection
                // leg : [0,0.4] x >= 0 , 0 <= 0.4
                // upper leg : x > 0.4 , x <= 0.675
                if ((deltaEH <= 0.4) && (eyeLocation.distanceSquared(currentLocation) <= 0.45)) {
                    if (debug) player.sendMessage(ChatColor.RED + "HeadShot!");
                    damage = getHeadShotDamage();
                    isHeadShot = true;
                } else if ((deltaBH >= 0) && (deltaBH <= 0.4)) {
                    if (debug) player.sendMessage(ChatColor.GREEN + "Lower leg!");
                    damage = getLowerLegDamage();
                } else if ((deltaBH > 0.4) && (deltaBH <= 0.675)) {
                    if (debug) player.sendMessage(ChatColor.GREEN + "Upper leg!");
                    damage = getUpperLegDamage();
                }
                // end body part detection
                // damaging
                PlayerLastDamage lastDamage = new PlayerLastDamage(getPlayer(), getWeapon(), this, isHeadShot);
                LastPlayerDamage lastPlayerDamage = new LastPlayerDamage(entity, CopsAndCrims.getPlugin());
                lastPlayerDamage.setLastDamage(lastDamage);
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
                if (debug) player.sendMessage(ChatColor.GREEN + "Total damage : " + ChatColor.BLUE + damage);
                return false;
            }
        }
        return true;
    }

    private void createBulletParticle(Location location) {
        ParticleEffect.FLAME.display(0.01F, 0.01F, 0.01F, 0.001F, 1, location, 128);
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}