package com.talesdev.copsandcrims.weapon.module;

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

    /**
     * Check if given item is a weapon
     *
     * @param itemStack An item stack
     * @return true if given item is a weapon
     */
    public boolean isWeapon(ItemStack itemStack);
}
