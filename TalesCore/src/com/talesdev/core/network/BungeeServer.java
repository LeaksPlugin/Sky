package com.talesdev.core.network;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * BungeeCord server
 *
 * @author MoKunz
 */
public class BungeeServer {
    private String name;
    private Plugin plugin;

    public BungeeServer(Plugin plugin, String name) {
        this.name = name;
        this.plugin = plugin;
    }

    public void send(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player can't be null!");
        }
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(name);
        player.sendPluginMessage(plugin, "BungeeCord", output.toByteArray());
    }
}