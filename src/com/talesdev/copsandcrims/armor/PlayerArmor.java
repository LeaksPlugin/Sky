package com.talesdev.copsandcrims.armor;

import com.talesdev.copsandcrims.CopsAndCrims;
import com.talesdev.copsandcrims.weapon.Weapon;
import com.talesdev.copsandcrims.weapon.module.ShootingModule;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            return originalDamage + 1.36 * 5;
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
        ItemStack armorItem = part.getFrom(player);
        Armor armor = CopsAndCrims.getPlugin().getArmorFactory().getArmor(armorItem);
        if (armor != null) {
            return armor.getArmorPoint();
        }
        return 0.0;
    }
}
