package com.talesdev.core.player.uuid;

import com.talesdev.core.TalesCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.*;

/**
 * Listener for creating uuid from core
 *
 * @author MoKunz
 */
public class UUIDSystem implements Listener, CommandExecutor {
    private TalesCore core;

    public UUIDSystem(TalesCore core) {
        this.core = core;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        fetchUUID(event.getPlayer());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("uuid")) {
            if (args.length > 0) {
                UUID uuid = core.getUuidMap().get(args[0]);
                if (uuid != null) {
                    sender.sendMessage("UUID of " + args[0] + " : " + ChatColor.GREEN + uuid.toString());
                } else {
                    if (args[0].equalsIgnoreCase("get")) {
                        if (args.length > 1) {
                            fetchUUID(args[1], null);
                            sender.sendMessage(ChatColor.GREEN + "Trying to fetch UUID of " + args[1]);
                            sender.sendMessage(ChatColor.GREEN + "You can use /uuid <player> to get uuid of this player");
                        }
                    } else if (args[0].equalsIgnoreCase("updateoffline")) {
                        Map<String, UUID> map = new HashMap<>();
                        for (OfflinePlayer offLinePlayer : Bukkit.getOfflinePlayers()) {
                            map.put(offLinePlayer.getName(), offLinePlayer.getUniqueId());
                        }
                        fetchUUID(map);
                        sender.sendMessage(ChatColor.GREEN + "Trying to fetch/update UUID of previous played players");
                        sender.sendMessage(ChatColor.GREEN + "You can use /uuid <player> to get uuid of these players");
                    } else {
                        sender.sendMessage("UUID Not available for this player!");
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void fetchUUID(Player p) {
        fetchUUID(p.getName(), p.getUniqueId());
    }

    private void fetchUUID(String playerName, UUID uuid) {
        Map<String, UUID> uuidMap = new HashMap<>();
        uuidMap.put(playerName, uuid);
        fetchUUID(uuidMap);
    }

    private void fetchUUID(Map<String, UUID> offlineUUIDMap) {
        core.getServer().getScheduler().runTaskAsynchronously(core, () -> {
            Map<String, UUID> mojangUUIDMap = new HashMap<>();
            UUIDFetcher fetcher = new UUIDFetcher(new ArrayList<>(offlineUUIDMap.keySet()));
            try {
                mojangUUIDMap = fetcher.call();
            } catch (Exception e) {
                core.getLogger().warning("Failed to fetch uuid for " + offlineUUIDMap.keySet() + " : " + e.getMessage());
                core.getLogger().warning("Plugin will use OfflinePlayer's UUID instead!");
            }
            final Map<String, UUID> finalMojangUUIDMap = mojangUUIDMap;
            core.getServer().getScheduler().runTask(core, () -> {
                finalMojangUUIDMap.keySet().stream().filter(player -> !core.getUuidMap().contains(player)).forEach(player -> {
                    if (finalMojangUUIDMap.get(player) != null) {
                        core.getUuidMap().set(player, finalMojangUUIDMap.get(player));
                    } else {
                        core.getUuidMap().set(player, offlineUUIDMap.get(player));
                    }
                });
            });
        });
    }
}
