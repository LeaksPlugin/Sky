package com.talesdev.core.entity;

import com.talesdev.core.system.NMSClass;
import com.talesdev.core.system.ReflectionUtils.RefClass;
import com.talesdev.core.system.ReflectionUtils.RefField;
import com.talesdev.core.system.ReflectionUtils.RefMethod;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import static com.talesdev.core.system.ReflectionUtils.getRefClass;

/**
 * Bounding box for check collision
 */
public class BoundingBox {
    private static final RefClass craftEntityClass = getRefClass(NMSClass.getCBClass("entity.CraftEntity"));
    private static final RefClass nmsEntityClass = getRefClass(NMSClass.getNMSClass("Entity"));
    private static final RefClass nmsBBClass = getRefClass(NMSClass.getNMSClass("AxisAlignedBB"));
    private static final RefMethod getHandleMethod = craftEntityClass.getMethod("getHandle");
    private static final RefMethod getBBMethod = nmsEntityClass.getMethod("getBoundingBox");
    private static final RefField x1Field = nmsBBClass.getField("a");
    private static final RefField y1Field = nmsBBClass.getField("b");
    private static final RefField z1Field = nmsBBClass.getField("c");
    private static final RefField x2Field = nmsBBClass.getField("d");
    private static final RefField y2Field = nmsBBClass.getField("e");
    private static final RefField z2Field = nmsBBClass.getField("f");
    private Entity entity;
    private Vector v1;
    private Vector v2;

    public BoundingBox(Entity entity) {
        this.entity = entity;
        retrieve();
    }

    private void retrieve() {
        Object nmsEntity = getHandleMethod.of(entity).call();
        Object nmsBB = getBBMethod.of(nmsEntity).call();
        // field retrieving
        double x1 = (double) x1Field.of(nmsBB).get();
        double y1 = (double) y1Field.of(nmsBB).get();
        double z1 = (double) z1Field.of(nmsBB).get();
        double x2 = (double) x2Field.of(nmsBB).get();
        double y2 = (double) y2Field.of(nmsBB).get();
        double z2 = (double) z2Field.of(nmsBB).get();
        // set vector
        v1 = new Vector(x1, y1, z1);
        v2 = new Vector(x2, y2, z2);
    }

    public Vector[] getVector() {
        return new Vector[]{v1, v2};
    }

    public Vector getMin() {
        return v1;
    }

    public Vector getMax() {
        return v2;
    }

    public boolean isInside(Vector v) {
        return v1.getX() < v.getX() && v.getX() < v2.getX() && v1.getY() < v.getY() && v.getY() < v2.getY() && v1.getZ() < v.getZ() && v.getZ() < v2.getZ();
    }
}
