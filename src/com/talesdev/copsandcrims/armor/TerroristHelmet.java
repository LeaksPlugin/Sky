package com.talesdev.copsandcrims.armor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Terrorist Helmet
 *
 * @author MoKunz
 */
public class TerroristHelmet extends Helmet {
    @Override
    public ItemStack asItem() {
        ItemStack itemStack = new ItemStack(Material.CHAINMAIL_HELMET);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.RED + "Terrorist Helmet");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
