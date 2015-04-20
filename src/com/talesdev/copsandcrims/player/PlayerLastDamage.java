package com.talesdev.copsandcrims.player;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.bullet.Bullet;
import com.talesdev.core.player.LastDamageCause;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Player last damage
 *
 * @author MoKunz
 */
public class PlayerLastDamage implements LastDamageCause {
    private Map<String, Object> objectMap = new HashMap<>();
    private Set<Entity> damagerList;
    private Entity lastDamager;
    private EntityDamageEvent.DamageCause damageCause;
    private Weapon weapon;
    private Bullet bullet;

    public PlayerLastDamage(Player player, Weapon weapon, Bullet bullet, boolean isHeadShot) {
        this(player);
        this.weapon = weapon;
        this.bullet = bullet;
        addAttachment("Bullet", getBullet());
        addAttachment("Weapon", getWeapon());
        addAttachment("HeadShot", isHeadShot);
    }

    public PlayerLastDamage(Entity entity) {
        this(EntityDamageEvent.DamageCause.ENTITY_ATTACK);
        damage(entity);
    }

    public PlayerLastDamage(EntityDamageEvent.DamageCause damageCause) {
        this();
        this.damageCause = damageCause;
    }

    public PlayerLastDamage() {
        damagerList = new HashSet<>();
    }

    @Override
    public Plugin getPlugin() {
        return CopsAndCrims.getPlugin();
    }

    @Override
    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public void setDamageCause(EntityDamageEvent.DamageCause damageCause) {
        this.damageCause = damageCause;
    }

    @Override
    public Entity getEntity() {
        return lastDamager;
    }

    @Override
    public void damage(Entity entity) {
        damagerList.add(entity);
        lastDamager = entity;
    }

    @Override
    public void assist(Entity entity) {
        damagerList.add(entity);
    }

    @Override
    public Set<Entity> getAssist() {
        return damagerList.stream().filter(entity -> !entity.equals(lastDamager)).collect(Collectors.toSet());
    }

    @Override
    public void clearDamager() {
        lastDamager = null;
        damagerList.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAttachment(String key, Class<T> attachmentClass) {
        return (T) getAttachment(key);
    }

    @Override
    public Object getAttachment(String key) {
        return objectMap.get(key);
    }

    @Override
    public <T> void addAttachment(String key, T value) {
        objectMap.put(key, value);
    }

    @Override
    public Map getAttachmentMap() {
        return objectMap;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Bullet getBullet() {
        return bullet;
    }
}
