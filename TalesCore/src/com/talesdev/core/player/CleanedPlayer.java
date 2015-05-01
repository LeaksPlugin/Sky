package com.talesdev.core.player;

import org.bukkit.entity.Player;

/**
 * Cleaned player
 *
 * @author MoKunz
 */
public class CleanedPlayer {
    private Player player;

    public CleanedPlayer(Player player) {
        this.player = player;
    }

    public Player clean() {
        player.setHealth(player.getMaxHealth());
        player.getInventory().clear();
        player.getEquipment().clear();
        player.setFoodLevel(20);
        player.setSaturation(20.0F);
        player.setFlying(false);
        return player;
    }
}
