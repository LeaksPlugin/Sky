package com.talesdev.copsandcrims.armor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * CounterTerrorist Helmet
 *
 * @author MoKunz
 */
public class CounterTerroristHelmet extends Helmet {
    @Override
    public ItemStack asItem() {
        ItemStack itemStack = new ItemStack(Material.IRON_HELMET);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.BLUE + "CounterTerrorist Helmet");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
