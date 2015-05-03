package com.talesdev.core.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.talesdev.core.world.sound.SoundEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Util for fast damaging entity
 *
 * @author MoKunz
 */
public class FastDamage {
    private LivingEntity entity;
    private double damage;

    public FastDamage(LivingEntity entity, double damage) {
        this.entity = entity;
        this.damage = damage;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void apply() {
        if (entity.getHealth() - damage > 0) {
            // damage packet
            PacketContainer entityStatus = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
            entityStatus.getIntegers().write(0, entity.getEntityId());
            entityStatus.getBytes().write(0, (byte) 2);
            try {
                for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(p, entityStatus);
                }
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            entity.setHealth(entity.getHealth() - damage);
            SoundEffect.getMobHurtSound(entity).playSound(entity.getLocation());
        } else {
            entity.damage(damage + 1);
        }
    }
}
