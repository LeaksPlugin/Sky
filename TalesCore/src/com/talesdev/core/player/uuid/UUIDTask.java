package com.talesdev.core.player.uuid;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

/**
 * UUID Task for finding uuid of given player
 *
 * @author MoKunz
 */
public class UUIDTask implements Runnable {
    private String playerName;
    private Plugin owner;
    private CommandSender sender;

    public UUIDTask(Plugin plugin,String playerName,CommandSender sender) {
        this.playerName = playerName;
        this.owner = plugin;
        this.sender = sender;
    }

    @Override
    public void run() {
        UUID uuid = null;
        try {
            uuid = UUIDFetcher.getUUIDOf(playerName);
        } catch (Exception ignored) {
        }
        final UUID finalUuid = uuid;
        Bukkit.getScheduler().runTask(owner, new Runnable() {
            @Override
            public void run() {
                if (finalUuid != null) {
                    sender.sendMessage("UUID of " + playerName + " : " + finalUuid.toString());
                }
            }
        });
    }
}
