package com.talesdev.copsandcrims.armor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * CounterTerrorist Kevlar
 *
 * @author MoKunz
 */
public class CounterTerroristKevlar extends Kevlar {
    @Override
    public ItemStack asItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BLUE + "CounterTerrorist Kevlar");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
