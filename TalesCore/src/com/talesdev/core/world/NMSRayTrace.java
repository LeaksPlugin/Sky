package com.talesdev.core.world;

import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

/**
 * Ray trace using nms code
 *
 * @author MoKunz
 */
public class NMSRayTrace {
    public static final double RAY_LENGTH_LIMIT = 200;
    private boolean isHit = false;
    private World hitWorld;
    private BlockVector hitBlock;
    private BlockFace hitFace;
    private Vector hitPos;
    private Entity entity;

    protected NMSRayTrace(World inWorld, MovingObjectPosition traceRes) {
        this.isHit = traceRes != null;
        this.hitWorld = inWorld;
        if (isHit) {
            this.hitBlock = new BlockVector(traceRes.a().getX(), traceRes.a().getY(), traceRes.a().getZ());
            this.hitFace = CraftBlock.notchToBlockFace(traceRes.direction);
            this.hitPos = new Vector(traceRes.pos.a, traceRes.pos.b, traceRes.pos.c);
        }
    }

    private static Vec3D toVec3D(Vector vec) {
        return new Vec3D(vec.getX(), vec.getY(), vec.getZ());
    }

    private static WorldServer getHandle(World world) {
        if (world instanceof CraftWorld)
            return ((CraftWorld) world).getHandle();

        throw new IllegalArgumentException("Cannot raytrace in a non CraftBukkit world!");
    }

    public static NMSRayTrace rayTrace(World world, Vector start, Vector direction) {
        return rayTrace(world, start, direction, RAY_LENGTH_LIMIT);
    }

    public static NMSRayTrace rayTrace(World world, Vector start, Vector direction, double length) {
        Vector end = start.clone().add(direction.multiply(length));

        return new NMSRayTrace(world, getHandle(world).rayTrace(toVec3D(start), toVec3D(end), false));
    }

    public static NMSRayTrace eyeTrace(LivingEntity entity) {
        return eyeTrace(entity, RAY_LENGTH_LIMIT);
    }

    public static NMSRayTrace eyeTrace(LivingEntity entity, double length) {
        Location loc = entity.getEyeLocation();

        World world = loc.getWorld();
        Vector end = loc.toVector().add(loc.getDirection().multiply(length));

        return new NMSRayTrace(world, getHandle(world).rayTrace(toVec3D(loc.toVector()), toVec3D(end), false));
    }

    public boolean isHit() {
        return isHit;
    }

    public BlockVector getBlockVector() {
        return hitBlock;
    }

    public Block getBlock() {
        return isHit ? hitBlock.toLocation(hitWorld).getBlock() : null;
    }

    public BlockFace getFace() {
        return hitFace;
    }

    public Vector getHitPos() {
        return hitPos;
    }

    public String toString() {
        return "[NMSRayTrace:" + (!isHit ? "MISS" : hitBlock + ";" + hitFace + ";" + hitPos) + "]";
    }
}
