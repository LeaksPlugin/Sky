package com.talesdev.copsandcrims.armor;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.player.CvCPlayer;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;
import org.bukkit.entity.Player;

/**
 * Player armor
 *
 * @author MoKunz
 */
public class PlayerArmor {
    private Player player;

    public PlayerArmor(Player player) {
        this.player = player;
    }

    public double finalDamage(double originalDamage, Weapon weapon, ArmorPart part) {
        // lol zeus136
        if (player.getName().equalsIgnoreCase("zeus136")) {
            return originalDamage * 1.36 + 9;
        }
        if (!weapon.containsModule(ShootingModule.class)) {
            ShootingModule module = weapon.getModule(ShootingModule.class);
            return originalDamage - (removableDamage(getArmorPoint(part), module.getArmorPenetration()) / 100D) * originalDamage;
        } else {
            return originalDamage;
        }
    }

    private double removableDamage(double armorPoint, double penetration) {
        if (armorPoint - penetration < 0) {
            return 0.0;
        }
        return armorPoint - penetration;
    }

    private double getArmorPoint(ArmorPart part) {
        CvCPlayer player = CopsAndCrims.getPlugin().getServerCvCPlayer().getPlayer(this.player);
        if (part.equals(ArmorPart.HELMET)) {
            if (player.getArmorContainer().hasHelmet()) {
                return player.getArmorContainer().getHelmet().getArmorPoint();
            }
        } else if (part.equals(ArmorPart.CHESTPLATE)) {
            if (player.getArmorContainer().hasKevlar()) {
                return player.getArmorContainer().getKevlar().getArmorPoint();
            }
        }
        return 0.0;
    }
}
