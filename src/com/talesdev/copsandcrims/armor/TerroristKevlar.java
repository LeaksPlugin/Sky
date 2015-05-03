package com.talesdev.copsandcrims.armor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Terrorist Kevlar
 *
 * @author MoKunz
 */
public class TerroristKevlar extends Kevlar {
    @Override
    public ItemStack asItem() {
        ItemStack itemStack = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Terrorist Kevlar");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
