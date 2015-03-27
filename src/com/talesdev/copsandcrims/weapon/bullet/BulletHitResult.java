package com.talesdev.copsandcrims.weapon.bullet;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

/**
 * Bullet hit result
 */
public class BulletHitResult{
    public enum HitType{
        ENTITY,
        BLOCK,
        NONE
    }
    private Bullet bullet;
    private HitType type;
    private Location bulletLocation;
    private Entity entity = null;
    private Block block = null;
    public BulletHitResult(Bullet bullet,Location bulletLocation,Entity entity){
        this.type = HitType.ENTITY;
        this.bulletLocation = bulletLocation;
        this.entity = entity;
    }
    public BulletHitResult(Bullet bullet,Location bulletLocation,Block block){
        this.type = HitType.BLOCK;
        this.bulletLocation = bulletLocation;
        this.block = block;
    }
    public HitType getHitType(){
        return type;
    }
    public Entity getEntity(){
        return entity;
    }
    public Location getBulletLocation(){
        return bulletLocation;
    }
    public Block getBlock(){
        return block;
    }
}
