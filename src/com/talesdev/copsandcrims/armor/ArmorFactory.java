package com.talesdev.copsandcrims.armor;

import com.talesdev.copsandcrims.CopsAndCrims;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

/**
 * Armor Factory
 *
 * @author MoKunz
 */
public class ArmorFactory implements Listener {
    private Set<Armor> armorSet;
    private CopsAndCrims plugin;

    public ArmorFactory(CopsAndCrims plugin) {
        this.armorSet = new HashSet<>();
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void addArmor(Armor armor) {
        armorSet.add(armor);
    }

    public Armor getArmor(ItemStack itemStack) {
        for (Armor armor : armorSet) {
            if (armor.isArmor(itemStack)) {
                return armor;
            }
        }
        return null;
    }

    @EventHandler
    public void onMove(InventoryClickEvent event) {
        if (event.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            event.setCancelled(true);
        }
    }
}
