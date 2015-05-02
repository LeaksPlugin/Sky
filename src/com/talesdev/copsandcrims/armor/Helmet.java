package com.talesdev.copsandcrims.armor;

import org.bukkit.inventory.ItemStack;

/**
 * Helmet
 *
 * @author MoKunz
 */
public abstract class Helmet implements Armor {

    @Override
    public ArmorPart getPart() {
        return ArmorPart.HELMET;
    }

    @Override
    public abstract ItemStack asItem();
}
