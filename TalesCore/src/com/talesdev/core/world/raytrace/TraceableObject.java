package com.talesdev.core.world.raytrace;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.Comparator;

/**
 * @author MoKunz
 */
public class TraceableObject {
    private Entity entity;
    private Block block;

    public TraceableObject(Entity entity) {
        this.entity = entity;
    }

    public TraceableObject(Block block) {
        this.block = block;
    }

    public static Comparator<TraceableObject> comparator(Location origin) {
        return ((o1, o2) -> {
            double d1 = origin.distanceSquared(o1.location());
            double d2 = origin.distanceSquared(o2.location());
            return (int) (d1 - d2);
        });
    }

    public Object get() {
        if (entity != null) return entity;
        else return block;
    }

    public Entity entity() {
        return entity;
    }

    public Block block() {
        return block;
    }

    public Location location() {
        if (entity != null) return entity.getLocation();
        else return block.getLocation();
    }

    @Override
    public String toString() {
        return get().toString();
    }
}
