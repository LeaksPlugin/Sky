package com.talesdev.copsandcrims.weapon;

import org.bukkit.inventory.ItemStack;

/**
 * Represent a weapon that can be represented as ItemStack
 *
 * @author MoKunz
 */
public interface WeaponItem {
    /**
     * Create ItemStack from weapon
     *
     * @return A weapon item
     */
    public ItemStack createItemStack();
}
