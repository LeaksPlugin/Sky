package com.talesdev.copsandcrims.armor;

import org.bukkit.inventory.ItemStack;

/**
 * Kevlar
 *
 * @author MoKunz
 */
public abstract class Kevlar implements Armor {
    @Override
    public ArmorPart getPart() {
        return ArmorPart.CHESTPLATE;
    }

    @Override
    public abstract ItemStack asItem();
}
