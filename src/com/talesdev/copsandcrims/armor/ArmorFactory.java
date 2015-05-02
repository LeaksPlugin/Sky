package com.talesdev.copsandcrims.armor;

import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

/**
 * Armor Factory
 *
 * @author MoKunz
 */
public class ArmorFactory {
    private Set<Armor> armorSet;

    public ArmorFactory() {
        this.armorSet = new HashSet<>();
    }

    public void addArmor(Armor armor) {
        armorSet.add(armor);
    }

    public Armor getArmor(ItemStack itemStack) {
        for (Armor armor : armorSet) {
            if (armor.isArmor(itemStack)) {
                return armor;
            }
        }
        return null;
    }
}
