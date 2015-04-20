package com.talesdev.copsandcrims.player;

import com.talesdev.core.world.NearbyEntity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
            ItemStack itemStack = getCompassItem(player);
            NearbyEntity<Player> nearbyEntity = new NearbyEntity<>(player.getLocation(), Player.class);
            Player nearestPlayer = nearbyEntity.findNearestInRadius(128, true);
            if (nearestPlayer != null) {
                player.setCompassTarget(nearestPlayer.getLocation());
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName(
                        ChatColor.GREEN + nearestPlayer.getName() + " : " + ChatColor.RED
                                + ((int) Math.round(nearestPlayer.getLocation().distance(player.getLocation()))) + " M "
                );
            }
        }
    }

    public ItemStack getCompassItem(Player player) {
        ItemStack[] contents = player.getInventory().getContents();
        for (ItemStack itemStack : contents) {
            if (itemStack != null) {
                if (itemStack.getType().equals(Material.COMPASS) && itemStack.hasItemMeta()) {
                    ItemMeta meta = itemStack.getItemMeta();
                    if (meta.hasLore()) {
                        List<String> lore = meta.getLore();
                        if (lore.size() > 0) {
                            if (lore.get(0).equalsIgnoreCase(ChatColor.GRAY + "TrackingCompass")) {
                                return itemStack;
                            }
                        }
                    }
                }
            }
        }
        return new ItemStack(Material.AIR);
    }
}
