package com.talesdev.copsandcrims.weapon;

import com.talesdev.copsandcrims.CopsAndCrims;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;

/**
 * Weapon cooldown
 *
 * @author MoKunz
 */
public class WeaponCooldownTag {
    private int coolDownTime;
    private static final String KEY = "WeaponCooldown";
    private Entity entity;

    public WeaponCooldownTag(int cooldownTime, Entity entity) {
        this.coolDownTime = cooldownTime;
        this.entity = entity;
    }

    public boolean attach() {
        entity.setMetadata(KEY, new FixedMetadataValue(CopsAndCrims.getPlugin(), true));
        Bukkit.getScheduler().runTaskLater(CopsAndCrims.getPlugin(), new Runnable() {
            @Override
            public void run() {
                entity.removeMetadata(KEY, CopsAndCrims.getPlugin());
            }
        }, coolDownTime);
        return true;
    }

    public boolean isCooldown() {
        return entity.hasMetadata(KEY);
    }

    public void setCoolDownTime(int coolDownTime) {
        this.coolDownTime = coolDownTime;
    }
}
