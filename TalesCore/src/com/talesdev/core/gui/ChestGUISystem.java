package com.talesdev.core.gui;

import com.talesdev.core.TalesCore;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Chest GUI Listener
 *
 * @author MoKunz
 */
public class ChestGUISystem implements Listener {
    private List<ChestGUI> guiList;
    private TalesCore core;

    public ChestGUISystem(TalesCore core) {
        this.guiList = new ArrayList<>();
        this.core = core;
    }

    public void addGUI(ChestGUI gui) {
        if (!containsGUI(gui)) {
            guiList.add(gui);
        }
    }

    public boolean containsGUI(ChestGUI gui) {
        return getGUI(gui.getName()) != null;
    }

    public ChestGUI getGUI(String name) {
        for (ChestGUI chestGUI : guiList) {
            if (ChatColor.stripColor(chestGUI.getName()).equals(name)) {
                return chestGUI;
            }
        }
        return null;
    }

    public void removeGUI(ChestGUI gui) {
        guiList.remove(guiList.indexOf(gui));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryAction(InventoryClickEvent event) {
        event.setCancelled(true);
        guiList.stream().filter(chestGUI -> chestGUI.getName().equals(event.getInventory().getName())).forEach(chestGUI -> {
            chestGUI.action(event);
        });
    }
}
