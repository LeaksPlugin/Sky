package com.talesdev.copsandcrims.weapon.module;

import com.talesdev.core.world.NMSRayTrace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

/**
 * Melee Module
 *
 * @author MoKunz
 */
public class MeleeModule extends WeaponModule {
    private double damage = 4;
    private double headDamage = 8;
    private double backStabDamage = 8;
    private double backStabHeadDamage = 12;

    public MeleeModule(double damage, double headDamage, double backStabDamage, double backStabHeadDamage) {
        super("Melee");
        this.damage = damage;
        this.headDamage = headDamage;
        this.backStabDamage = backStabDamage;
        this.backStabHeadDamage = backStabHeadDamage;
    }

    public MeleeModule(double damage, double headDamage) {
        this(damage, headDamage, headDamage, headDamage + damage);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof LivingEntity) {
            // cancel normal op
            event.setCancelled(true);
            // manual op
            LivingEntity entity = (LivingEntity) event.getEntity();
            Player player = (Player) event.getDamager();
            NMSRayTrace nmsRayTrace = NMSRayTrace.eyeTrace(player);
            Vector hitPos = nmsRayTrace.getHitPos();
            boolean isHeadShot = false;
            boolean isBackStab = false;
            // check for headshot
            if (Math.abs(entity.getEyeLocation().getY() - hitPos.getY()) <= 0.4) {
                isHeadShot = true;
            }
        }
    }

    public double getBackStabHeadDamage() {
        return backStabHeadDamage;
    }

    public void setBackStabHeadDamage(double backStabHeadDamage) {
        this.backStabHeadDamage = backStabHeadDamage;
    }

    public double getBackStabDamage() {
        return backStabDamage;
    }

    public void setBackStabDamage(double backStabDamage) {
        this.backStabDamage = backStabDamage;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getHeadDamage() {
        return headDamage;
    }

    public void setHeadDamage(double headDamage) {
        this.headDamage = headDamage;
    }
}
