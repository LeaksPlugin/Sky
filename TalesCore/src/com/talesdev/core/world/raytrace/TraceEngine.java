package com.talesdev.core.world.raytrace;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * @author MoKunz
 */
public class TraceEngine {
    public static final Set<Material> DEFAULT_TRANS = defaultTransparent();
    private Ray3D ray3D;
    private LivingEntity tracer;
    private Set<Material> trans;
    private SortedMap<TraceableObject, AABB> objects;

    public TraceEngine(LivingEntity livingEntity, Vector direction, Set<Material> trans) {
        objects = new TreeMap<>(TraceableObject.comparator(livingEntity.getEyeLocation()));
        ray3D = new Ray3D(Vec3D.fromLocation(livingEntity.getEyeLocation()), Vec3D.fromVector(direction));
        tracer = livingEntity;
        this.trans = trans;
    }

    public TraceEngine(LivingEntity livingEntity, Vector direction) {
        this(livingEntity, direction, DEFAULT_TRANS);
    }

    public TraceEngine(LivingEntity livingEntity) {
        this(livingEntity, livingEntity.getEyeLocation().getDirection(), DEFAULT_TRANS);
    }

    private static Set<Material> defaultTransparent() {
        Set<Material> trans = new HashSet<>();
        for (Material m : Material.values()) {
            if (!m.isSolid()) trans.add(m);
        }
        return trans;
    }

    public Set<Material> getTransparentBlock() {
        return trans;
    }

    private void searchObjects() {
        for (Entity entity : tracer.getNearbyEntities(100, 100, 100))
            objects.put(new TraceableObject(entity), new AABB(entity));
        BlockIterator bi = new BlockIterator(tracer, 140);
        Block block;
        while (bi.hasNext()) {
            block = bi.next();
            if (!trans.contains(block.getType())) {
                objects.put(new TraceableObject(block), new AABB(block.getLocation()));
            }
        }
    }

    public TraceResult trace(float distance) {
        Vec3D vec3D = null;
        TraceableObject object = null;
        searchObjects();
        for (Map.Entry<TraceableObject, AABB> entry : objects.entrySet()) {
            vec3D = entry.getValue().intersectsRay(ray3D, 0, distance);
            if (vec3D != null) {
                object = entry.getKey();
                break;
            }
        }
        return new TraceResult(
                vec3D != null,
                object,
                vec3D != null ? new Vector(vec3D.x, vec3D.y, vec3D.z) : new Vector(),
                object != null ? tracer.getEyeLocation().distance(object.location()) : 0.0
        );
    }

    public List<TraceResult> coneTrace(float distance, float coneSize) {
        return new ArrayList<>();
    }

    public void clear() {
        objects.clear();
    }
}
