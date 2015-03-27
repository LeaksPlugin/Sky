package com.talesdev.core.world;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import java.util.*;

/**
 * Nearby entity
 * @author MoKunz
 */
public class NearbyEntity<T extends Entity>{
    private Class<T> type;
    private Location location;
    public NearbyEntity(Location location,Class<T> tClass){
        this.location = location;
        this.type = tClass;
    }
    public void setLocation(Location location){
        this.location = location;
    }
    public Location getLocation(){
        return location;
    }
    public List<T> find(){
        List<T> entity = new ArrayList<>();
        for(T e : location.getWorld().getEntitiesByClass(type)){
            entity.add(e);
        }
        return entity;
    }
    public List<T> findInRadius(double radius,boolean eyeLocation){
        List<T> entities = find();
        List<T> result = new ArrayList<>();
        // find
        for(T e : entities){
            if(eyeLocation){
                if(e instanceof LivingEntity){
                    if(((LivingEntity)e).getEyeLocation().distanceSquared(location) < radius*radius){
                        result.add(e);
                    }
                }
            }
            else{
                if(e.getLocation().distanceSquared(location) < radius*radius){
                    result.add(e);
                }
            }

        }
        // sorting
        return result;
    }
    public T findNearestInRadius(double radius,boolean eyeLocation){
        List<T> entities = findInRadius(radius,eyeLocation);
        Map<Double,T> distance = new HashMap<>();
        T nearestEntity = null;
        for(T entity : entities){
            if(eyeLocation){
                if(entity instanceof LivingEntity){
                    distance.put(((LivingEntity) entity).getEyeLocation().distanceSquared(location), entity);
                }
            }
            else{
                distance.put(entity.getLocation().distanceSquared(location), entity);
            }
        }
        if(distance.size() > 0){
            double nearestDistance = Collections.min(distance.keySet());
            nearestEntity = distance.get(nearestDistance);
        }
        return nearestEntity;
    }
    public List<T> findInRadius(double radius){
        return findInRadius(radius, false);
    }
}
