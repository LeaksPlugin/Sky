package com.talesdev.copsandcrims.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Tracking system
 *
 * @author MoKunz
 */
public class CompassTrackingSystem extends BukkitRunnable {
    private Set<Player> useTrackingPlayer;

    public CompassTrackingSystem() {
        useTrackingPlayer = Collections.synchronizedSet(new HashSet<>());
    }

    public void addTracking(Player player) {
        useTrackingPlayer.add(player);
    }

    public void removeTracking(Player player) {
        useTrackingPlayer.remove(player);
    }

    @Override
    public void run() {
        for (Player player : useTrackingPlayer) {

        }
    }

    public ItemStack getCompassItem(Player player) {
        ItemStack[] contents = player.getInventory().getContents();
        for (ItemStack itemStack : contents) {
            if (itemStack != null) {
                if (itemStack.getType().equals(Material.COMPASS) && itemStack.hasItemMeta()) {
                    ItemMeta meta = itemStack.getItemMeta();
                }
            }
        }
        return new ItemStack(Material.AIR);
    }
}
