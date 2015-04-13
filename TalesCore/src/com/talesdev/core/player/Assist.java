package com.talesdev.core.player;

import com.talesdev.core.TalesCore;
import com.talesdev.core.entity.MetaData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Assisting system
 *
 * @author MoKunz
 */
public class Assist {
    private Player player;
    private MetaData metaData;

    public Assist(Player player) {
        this(player, TalesCore.getPlugin());
    }

    public Assist(Player player, Plugin plugin) {
        this.player = player;
        this.metaData = new MetaData(player, plugin);
    }

    public void assist(Player player) {
        metaData.setMetadata("PlayerAssist", player.getName());
    }

    public Player last() {
        String lastPlayer = metaData.getMetadata("PlayerAssist", String.class);
        if (lastPlayer != null) {
            return Bukkit.getPlayer(lastPlayer);
        }
        return null;
    }

    public Player getPlayer() {
        return player;
    }
}
