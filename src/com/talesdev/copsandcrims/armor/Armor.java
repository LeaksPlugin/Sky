package com.talesdev.copsandcrims.armor;

import org.bukkit.inventory.ItemStack;

/**
 * Armor
 *
 * @author MoKunz
 */
public interface Armor {
    default double getArmorPoint() {
        return 100;
    }

    ArmorPart getPart();

    ItemStack asItem();

    default boolean isArmor(ItemStack itemStack) {
        return itemStack.isSimilar(asItem());
    }
}
