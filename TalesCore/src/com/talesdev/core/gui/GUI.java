package com.talesdev.core.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * GUI
 *
 * @author MoKunz
 */
public interface GUI {
    void open(Player player);

    void action(InventoryClickEvent event);

    void close(Player player);
}
