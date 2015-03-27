package com.talesdev.core.item;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Abstract class for Item
 * @author MoKunz
 */
public abstract class AbstractItem implements Listener {
    public String toString() {
        return "";
    }
    public abstract String getItemName();
    public abstract boolean isValidItem(ItemStack itemStack);
}
