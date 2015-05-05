package com.talesdev.core.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Damaging data
 *
 * @author MoKunz
 */
public class DamageData {
    private EntityDamageEvent.DamageCause damageCause;
    private Entity damager;
    private Map<String, Object> attachment;
    private double damageAmount;
    private long time;

    public DamageData() {
        time = System.currentTimeMillis();
        damageCause = EntityDamageEvent.DamageCause.CUSTOM;
        damager = null;
        damageAmount = 0.0;
        attachment = new HashMap<>();
    }

    public DamageData(double amount, EntityDamageEvent.DamageCause cause, Entity entity) {
        this();
        damageCause = cause;
        damageAmount = amount;
        damager = entity;
    }

    public DamageData(EntityDamageEvent event) {
        this();
        damageCause = event.getCause();
        damageAmount = event.getFinalDamage();
    }

    public DamageData(EntityDamageByEntityEvent event) {
        this();
        damageCause = event.getCause();
        damageAmount = event.getFinalDamage();
        damager = event.getDamager();
    }

    public void addAttachment(String key, Object obj) {
        attachment.put(key, obj);
    }

    public Object getAttachment(String key) {
        return attachment.get(key);
    }

    public boolean containsAttachment(String key) {
        return attachment.containsKey(key);
    }

    public boolean containsAttachmentValue(Object value) {
        return attachment.containsValue(value);
    }

    public void removeAttachment(String key) {
        attachment.remove(key);
    }

    public Map<String, Object> getAttachments() {
        return new HashMap<>(attachment);
    }

    public <T> T getAttachment(String key, Class<T> clazz) {
        return clazz.cast(attachment.get(key));
    }

    public long getTime() {
        return time;
    }

    public Entity getDamager() {
        return damager;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(damageCause).
                append(damager).
                append(time).
                append(damageAmount).
                toHashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof DamageData) {
            if (obj == this) {
                return true;
            }
            DamageData damageData = ((DamageData) obj);
            return new EqualsBuilder().
                    append(damager, damageData.getDamager()).
                    append(damageCause, damageData.getDamageCause()).
                    append(time, damageData.getTime()).
                    append(damageAmount, damageData.getDamageAmount()).
                    isEquals();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "DamageData{" +
                "damageCause=" + damageCause +
                ", damager=" + damager +
                ", attachment=" + attachment +
                ", damageAmount=" + damageAmount +
                ", time=" + time +
                '}';
    }

    public double getDamageAmount() {
        return damageAmount;
    }
}
