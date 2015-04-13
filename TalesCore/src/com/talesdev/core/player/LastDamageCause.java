package com.talesdev.core.player;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.Map;

/**
 * Interface represent a last damage cause
 *
 * @author MoKunz
 */
public interface LastDamageCause {
    /**
     * Get the plugin tha involve in this cause
     *
     * @return the plugin
     */
    public Plugin getPlugin();

    public EntityDamageEvent.DamageCause getDamageCause();

    public void setDamageCause(EntityDamageEvent.DamageCause damageCause);

    public Entity getEntity();

    public <T> T getAttachment(String key, Class<T> attachmentClass);

    public Object getAttachment(String key);

    public <T> void addAttachment(String key, T value);

    public Map getAttachmentMap();

}
